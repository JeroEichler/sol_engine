package solengine.resultanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;


public class ResultAnalysisOrchestrator {

	public List<AnalyzedQueryResponse> analyzeList(List<QueryResponse>  results){
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<AnalyzedQueryResponse>> temporaryResults = new ArrayList<>();
        List<AnalyzedQueryResponse> analysisResults = new ArrayList<>();
//		int k =0;
		for(QueryResponse resultItem : results){
			if(resultItem.getObjects().size() > 0) {
				ResultItemAnalyzer analyzer = new ResultItemAnalyzer(resultItem);
				temporaryResults.add(executor.submit(analyzer));
//				System.out.println(k++ + " dispatched.");
			}
		}
		for(Future<AnalyzedQueryResponse> future : temporaryResults)
        {
              try
              {
            	  AnalyzedQueryResponse analysis = future.get();
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
	
	public AnalyzedQueryResponse analyzeSingle(QueryResponse  resultItem){
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<AnalyzedQueryResponse>> temporaryResults = new ArrayList<>();
        AnalyzedQueryResponse analysisResult = new AnalyzedQueryResponse(resultItem);

        ResultItemAnalyzer analyzer = new ResultItemAnalyzer(resultItem);
        temporaryResults.add(executor.submit(analyzer));

		for(Future<AnalyzedQueryResponse> future : temporaryResults)
        {
              try
              {
            	  AnalyzedQueryResponse analysis = future.get();
            	  analysisResult = analysis;
              } 
              catch (InterruptedException | ExecutionException e) 
              {
                  e.printStackTrace();
              }
          }

		executor.shutdown();
		
		return analysisResult;
	}
}
