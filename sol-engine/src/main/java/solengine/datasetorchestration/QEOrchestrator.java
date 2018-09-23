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
import solengine.queryexecution.IQueryExecutor;
import solengine.queryexecution.QueryExecutorFactory;
import solengine.queryexecution.QueryExecutorTypeEnum;
import solengine.queryexecution.UserQueryExecutor;
import solengine.utils.Vocabulary;

public class QEOrchestrator {
	
	Map<List<String>,QueryResponse>  queryResults;
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;
	
	public QEOrchestrator(String address) {
		this.datasetEndpoint = address;
		this.usrQueryExecutor = new UserQueryExecutor();
		this.queryResults = new ConcurrentHashMap<List<String>,QueryResponse>();
	}
	
	public Map<List<String>, QueryResponse> processQuery(String queryString){
		List<List<String>> originalResults = usrQueryExecutor.queryEndpoint(datasetEndpoint, queryString);
		
		List<QueryExecutorTypeEnum> qExecutorNames = DatasetOrchestConfigurator.buildQueryExecutorList(datasetEndpoint);
		
		for(QueryExecutorTypeEnum qexecutor : qExecutorNames){
			this.processMapReduce(qexecutor, originalResults);
		}
		
		return this.queryResults;
		
	}

	private void processMapReduce(QueryExecutorTypeEnum executorType, List<List<String>> originalResults) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<QueryResponse>> temporaryResults = new ArrayList<>();
		for(List<String> resultItem : originalResults){
			IQueryExecutor processor = QueryExecutorFactory.createQueryExecutor(executorType, datasetEndpoint, resultItem);
			temporaryResults.add(executor.submit(processor));
//			System.out.println(k++);
		}
		for(Future<QueryResponse> future : temporaryResults)
        {
              try
              {
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
