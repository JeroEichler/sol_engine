package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.resultanalysis.QueryResponseAnalyzer;


/* ***************************************************************************************************************
 * Class that encompasses the orchestration process of the QueryResponse analysis.
 *  
 * Properties:	(1) String datasetEndpoint; 	// represents the remote dataset endpoint that the QRAOrchestrator
 * 												// works with.
 * 
 *****************************************************************************************************************/
public class QueryResponseAnalysisOrchestrator {
	
	private String datasetEndpoint = Vocabulary.EmptyString;
	
	public QueryResponseAnalysisOrchestrator(String endpoint) {
		this.datasetEndpoint = endpoint;
	}

	public List<AnalyzedQueryResponse> analyzeList(List<QueryResponse>  results){
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(50);
        List<Future<AnalyzedQueryResponse>> temporaryResults = new ArrayList<>();
        List<AnalyzedQueryResponse> analysisResults = new ArrayList<>();
        
		for(QueryResponse resultItem : results){
			if(resultItem.getObjects().size() > 0) {
				// MAP STEP: for each QueryResponse item, a QueryResponseAnalyzer executor will be dispatch to compute
				//// the score of the QueryResponse.
				QueryResponseAnalyzer analyzer = new QueryResponseAnalyzer(resultItem, this.datasetEndpoint);
				temporaryResults.add(executor.submit(analyzer));
			}
			else {
				AnalyzedQueryResponse not_analyzed = new AnalyzedQueryResponse(resultItem);
				not_analyzed.emptyResponse = true;
				analysisResults.add(not_analyzed);
			}
		}
		for(Future<AnalyzedQueryResponse> future : temporaryResults)
        {
              try
              {
            	  // REDUCE STEP: all AnalyzedQueryResponse are added to the analysisResults.
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
		
		List<QueryResponse> results = new ArrayList<QueryResponse>();
		results.add(resultItem);
		
		List<AnalyzedQueryResponse> analysis = this.analyzeList(results);
		
		AnalyzedQueryResponse analyzedResult = analysis.get(0);
		
		return analyzedResult;
	}
}
