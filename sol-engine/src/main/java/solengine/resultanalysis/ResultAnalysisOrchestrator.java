package solengine.resultanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.model.QueryResponse;


public class ResultAnalysisOrchestrator {

	public List<String> processAnalysis(List<QueryResponse>  results){
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<String>> temporaryResults = new ArrayList<>();
        List<String> analysisResults = new ArrayList<>();
//		int k =0;
		for(QueryResponse resultItem : results){
			ResultItemAnalyzer analyzer = new ResultItemAnalyzer(resultItem);
			temporaryResults.add(executor.submit(analyzer));
//			System.out.println(k++);
		}
		for(Future<String> future : temporaryResults)
        {
              try
              {
            	  String analysis = future.get();
            	  analysisResults.add(analysis);
              } 
              catch (InterruptedException | ExecutionException e) 
              {
                  e.printStackTrace();
              }
          }

		executor.shutdown();
		
		return analysisResults;
	}
}
