package solengine.resultanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;

public class ResultItemAnalyzer implements Callable<AnalyzedQueryResponse> {

	private String datasetEndpoint = Vocabulary.DBpediaEndpoint;
	private QueryResponse result;

	public ResultItemAnalyzer(QueryResponse resultItem, String endpoint) {
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
			List<String> labels = this.getDataAboutResource(queryResultItem);
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
			List<String> labels = this.getDataAboutResource(entity);
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

	private double computeSimilarity(AnalyzedQueryResponse analysis, List<String> resultLabels,
			HashMap<String, List<String>> additionalInfoLabels) {
		double sum = 0, counter = 0;
		for(List<String> additionalLabels : additionalInfoLabels.values()){
			double score = this.computeSimilarity(resultLabels, additionalLabels);
			if(score < 0) {
				analysis.valid = false;
			}
			else {
				sum = sum + score;
				counter++;
			}
		}
		if(counter > 0) {
			double average = sum / counter;
			return average;
		}
		analysis.valid = false;
		return 0;
	}

	private List<String> getDataAboutResource(String resource){
		String queryString = this.buildQueryStringFromResource(resource);
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint , query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

            // Execute.
            ResultSet rs = qexec.execSelect();
            List<String> resultsLabels = extractResources(rs);
            return resultsLabels;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	private String buildQueryStringFromResource(String resourceURI){
		String queryString  = 
				"SELECT ?object" +
				"	WHERE{" +
				"		<"+resourceURI+"> <"+Vocabulary.Rdf_TypeProperty+"> ?object." +
				"	}"
	            ;
		return queryString;
	}
	
	private List<String> extractResources(ResultSet rs) {
		List<String> labels = new ArrayList<String>();
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
    		RDFNode labelObject = soln.get("object");

    		if(labelObject.isResource()){
				Resource res = (Resource) labelObject;
				if(res.getURI() != null){
					labels.add(res.getURI());
				}
    		}
    		else{
    			Literal res = (Literal) labelObject;
				String literalExpression = res.getString();
				labels.add(literalExpression);
    		}
        }
		return labels;
	}
	
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
