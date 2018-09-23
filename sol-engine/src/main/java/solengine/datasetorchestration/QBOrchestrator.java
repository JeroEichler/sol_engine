package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.querybuilder.IQueryBuilder;
import solengine.querybuilder.QueryBuilderFactory;
import solengine.querybuilder.QueryBuilderTypeEnum;
import solengine.utils.Vocabulary;

public class QBOrchestrator {
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	public QBOrchestrator(String address) {
		this.datasetEndpoint = address;
	}
	
	public List<String> generateQuery(String queryString){
		
		List<String> alternativeQueries = new ArrayList<String>();
		
		List<QueryBuilderTypeEnum> qbTypes = DatasetOrchestConfigurator.buildQueryBuilderList(datasetEndpoint);
		
		for(QueryBuilderTypeEnum type : qbTypes){
			String altQuery = this.getAlternativeQuery(type, queryString);
			if(altQuery.length() > 0) { // no problem occurred.
				alternativeQueries.add(altQuery);
			}
		}
		
		return alternativeQueries;
		
	}
	
	private String getAlternativeQuery(QueryBuilderTypeEnum qBuilderType, String queryString) {
		String result = "";
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<String>> temporaryResults = new ArrayList<>();
        
        IQueryBuilder processor = QueryBuilderFactory.createQueryTransformer(qBuilderType, datasetEndpoint, queryString);
        temporaryResults.add(executor.submit(processor));
        
		for(Future<String> future : temporaryResults)
        {
              try
              {
            	  String query = future.get();
            	  result = query;
              } 
              catch (InterruptedException | ExecutionException e) 
              {
                  e.printStackTrace();
              }
          }

		executor.shutdown();
		
		return result;
	}

}
