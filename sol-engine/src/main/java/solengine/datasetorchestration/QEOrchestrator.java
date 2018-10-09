package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.queryexecution.IQueryExecutor;
import solengine.queryexecution.QueryExecutorFactory;
import solengine.queryexecution.QueryExecutorTypeEnum;
import solengine.queryexecution.UserQueryExecutor;

/* ***************************************************************************************************************
 * Class that encompasses the orchestration process of a set of QueryExecutors.
 *  
 * Properties:	(1) String datasetEndpoint; 	// represents the remote dataset endpoint that the QBOrchestrator
 * 												// works with.
 * 				(2) Map<List<String>,QueryResponse>		
 * 						queryResults;			// represents the final result of the execution of the query 
 * 												// process.
 * 				(3) UserQueryExecutor 
 * 						usrQueryExecutor;		// it is a QueryExecutor that is used to process the user's query.
 * 
 *****************************************************************************************************************/
public class QEOrchestrator {
	
	Map<List<String>,QueryResponse>  queryResults;
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;
	
	public QEOrchestrator(String address) {
		this.datasetEndpoint = address;
		this.usrQueryExecutor = new UserQueryExecutor();
		this.queryResults = new ConcurrentHashMap<List<String>,QueryResponse>();
	}
	
	/* ***************************************************************************************************************
	 * Function that provides the complete execution of a query.
	 * 
	 * Parameters:	(1) String queryString;  				// represents the submitted query.
	 * Returns: 	(2) Map<List<String>, QueryResponse>.	// represents a HashMap of QueryResponses indexed by 
	 * 														// the result string.
	 *****************************************************************************************************************/
	public Map<List<String>, QueryResponse> processQuery(String queryString){
		List<List<String>> originalResults = usrQueryExecutor.queryEndpoint(datasetEndpoint, queryString);
		
		List<QueryExecutorTypeEnum> qExecutorNames = DatasetOrchestConfigurator.buildQueryExecutorList(datasetEndpoint);
		
		for(QueryExecutorTypeEnum qexecutor : qExecutorNames){
			this.dispatchQueryExecutors(qexecutor, originalResults);
		}
		
		return this.queryResults;
		
	}

	private void dispatchQueryExecutors(QueryExecutorTypeEnum executorType, List<List<String>> originalResults) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<QueryResponse>> temporaryResults = new ArrayList<>();
		for(List<String> resultItem : originalResults){
			IQueryExecutor processor = QueryExecutorFactory.createQueryExecutor(executorType, datasetEndpoint, resultItem);
			// MAP STEP: for each result item, a query executor will be dispatch to retrieve additional information for
			//// this result.
			temporaryResults.add(executor.submit(processor));
		}
		for(Future<QueryResponse> future : temporaryResults)
        {
              try
              {
            	  // REDUCE STEP: all responses (RDF Model) are aggregated by each query result item (List<String>), which
            	  //// the response is about.
            	  QueryResponse queryAnswer = future.get();
            	  this.queryResults.merge(
            			  queryAnswer.getResult(), 
            			  queryAnswer, 
            			  (oldVal, newVal) -> new QueryResponse(queryAnswer.getResult(), newVal.getAdditionalInfo().add(oldVal.getAdditionalInfo()) ));
            	  
              } 
              catch (InterruptedException | ExecutionException e) 
              {
                  e.printStackTrace();
              }
          }

		executor.shutdown();
	}

}
