package solengine.resultanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;

/* ***************************************************************************************************************
 * Class that encapsulates the analysis of a QueryResponse.
 * 
 * The QueryResponse analysis consists of the average of the comparison between QueryResponse.results and each
 * element of the QueryResponse.additionalInfo.
 * 
 *****************************************************************************************************************/
public class QueryResponseAnalyzer implements Callable<AnalyzedQueryResponse> {

	private String datasetEndpoint = Vocabulary.DBpediaEndpoint;
	private QueryResponse result;

	public QueryResponseAnalyzer(QueryResponse resultItem, String endpoint) {
		this.result = resultItem;
		this.datasetEndpoint = endpoint;
	}

	@Override
	public AnalyzedQueryResponse call(){
		if(!result.isValid()){
			AnalyzedQueryResponse analyzed = new AnalyzedQueryResponse(this.result);
			analyzed.valid = false;
			return analyzed;
		}
		
		AnalyzedQueryResponse analysis = new AnalyzedQueryResponse(this.result);
		
		//extracting labels from QueryResponse.result
		List<String> queryResult = result.getResult();
		List<String> resultLabels = new ArrayList<String>();
		for(String queryResultItem : queryResult){
			List<String> labels = DatasetOperations.getDataAboutResource(this.datasetEndpoint, queryResultItem);
			if(labels != null) {
				resultLabels.addAll(labels);
			}
			else {
				analysis.valid = false;
				return analysis;
			}			
		}		
		analysis.resultLabels = resultLabels;

		//extracting labels from QueryResponse.additionalInfo
		List<String> additionalInfoResults = result.getObjects();
		HashMap<String, List<String>> additionalInfoLabels = new HashMap<String, List<String>>();
		for(String entity : additionalInfoResults){
			List<String> labels = DatasetOperations.getDataAboutResource(this.datasetEndpoint, entity);
			if(labels != null) {
				additionalInfoLabels.put(entity, labels);
			}
			else {
				analysis.valid = false;
				return analysis;
			}	
		}
		analysis.additionalInfoLabels = additionalInfoLabels;
		
		analysis.unexpectednessScore = this.computeSimilarity(analysis, resultLabels, additionalInfoLabels);
		
		return analysis;
	}

	

	/* ***************************************************************************************************************
	 * Computes the average of the similarities found in a QueryResponse.
	 * 
	 *****************************************************************************************************************/
	private double computeSimilarity(AnalyzedQueryResponse analysis, 
			List<String> resultLabels,
			HashMap<String, List<String>> additionalInfoLabels) {
		
		double sum = 0, counter = 0;
		for(List<String> additionalLabels : additionalInfoLabels.values()){
			double score = this.computeSimilarity(resultLabels, additionalLabels);
			if(score >= 0) {
				sum = sum + score;
				counter++;
			}
		}
		if(counter > 0) {
			double average = sum / counter;
			return average;
		}
		analysis.emptyResponse = false;
		return 0;
	}

	/* ***************************************************************************************************************
	 * Computes the similarity between two lists.
	 * 
	 *****************************************************************************************************************/
	private double computeSimilarity(List<String> listOne, List<String> listTwo) {
		
		if(listOne.size() == 0 || listTwo.size() == 0) {
			return -1;
		}
		double intersecctionCounter = 0;
		
		for(String x : listOne) {
			if(listTwo.contains(x)) {
				intersecctionCounter++;
			}
		}
		
		List<String> unionList = new ArrayList<String>();
		unionList.addAll(listOne);
		for(String x : listTwo) {
			if(!unionList.contains(x)) {
				unionList.add(x);
			}
		}
		double unexpectedness = 1 - ( intersecctionCounter / unionList.size());
		
		return unexpectedness;
	}

}
