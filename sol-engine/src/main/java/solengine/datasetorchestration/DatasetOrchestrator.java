package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.model.QueryResult;
import solengine.queryexecution.IQueryExecutor;
import solengine.queryexecution.QueryExecutorFactory;
import solengine.queryexecution.QueryExecutorTypeEnum;
import solengine.queryexecution.UserQueryExecutor;
import solengine.utils.Vocabulary;

public class DatasetOrchestrator {
	
	Map<List<String>,QueryResult>  queryResults;
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;

	public DatasetOrchestrator(String address) {
		this.datasetEndpoint = address;
		this.usrQueryExecutor = new UserQueryExecutor();
		this.queryResults = new ConcurrentHashMap<List<String>,QueryResult>();
	}
	
	public Map<List<String>, QueryResult> processQuery(String queryString){
		List<List<String>> originalResults = usrQueryExecutor.queryEndpoint(datasetEndpoint, queryString);
		
		List<QueryExecutorTypeEnum> qExecutorNames = DatasetOrchestConfigurator.buildQueryExecutorList(datasetEndpoint);
		
		for(QueryExecutorTypeEnum qexecutor : qExecutorNames){
			this.processMapReduce(qexecutor, originalResults);
		}
		
		return this.queryResults;
		
	}

	private void processMapReduce(QueryExecutorTypeEnum executorType, List<List<String>> originalResults) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<QueryResult>> temporaryResults = new ArrayList<>();
		for(List<String> resultItem : originalResults){
			IQueryExecutor processor = QueryExecutorFactory.createQueryExecutor(executorType, datasetEndpoint, resultItem);
			temporaryResults.add(executor.submit(processor));
//			System.out.println(k++);
		}
		for(Future<QueryResult> future : temporaryResults)
        {
              try
              {
            	  QueryResult queryAnswer = future.get();
            	  this.queryResults.merge(
            			  queryAnswer.getResult(), 
            			  queryAnswer, 
            			  (oldVal, newVal) -> new QueryResult(queryAnswer.getResult(), newVal.getAdditionalInfo().add(oldVal.getAdditionalInfo()) ));
            	  
              } 
              catch (InterruptedException | ExecutionException e) 
              {
                  e.printStackTrace();
              }
          }

		executor.shutdown();
	}

	public String generateQuery(String query) {
		return "";
	}

}
