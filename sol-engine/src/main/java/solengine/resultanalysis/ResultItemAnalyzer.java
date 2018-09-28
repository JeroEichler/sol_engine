package solengine.resultanalysis;

import java.util.ArrayList;
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

import solengine.model.QueryResponse;
import solengine.utils.ControlObjects;
import solengine.utils.Vocabulary;

public class ResultItemAnalyzer implements Callable<String> {

	private String endpoint = Vocabulary.DBpediaEndpoint;
	private QueryResponse result;

	public ResultItemAnalyzer(QueryResponse resultItem) {
		this.result = resultItem;
	}

	@Override
	public String call(){
		if(result.equals(ControlObjects.emitInvalidQueryResponse())){
			return "problem reading resource";
		}
		//extracting labels from QueryResponse.result
		List<String> queryResult = result.getResult();
		List<String> resultLabels = new ArrayList<String>();
		for(String queryResultItem : queryResult){
			resultLabels.addAll(this.getDataAboutResource(queryResultItem));
		}

		//extracting labels from QueryResponse.additionalInfo
		List<String> additionalInfoResults = result.getObjects();
		List<String> additionalInfoLabels = new ArrayList<String>();
		for(String queryResultItem : additionalInfoResults){
			additionalInfoLabels.addAll(this.getDataAboutResource(queryResultItem));
		}
		return 
				"similarity of "+
				this.result.getResult()+""
				+" is ;"+
				""+this.computeSimilarity(resultLabels, additionalInfoLabels)
				;
	}

	private List<String> getDataAboutResource(String resource){
		String queryString = this.buildQueryStringFromResource(resource);
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint , query) ) {
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
	
	private String computeSimilarity(List<String> listOne, List<String> listTwo) {
		int sizeOfListOne = listOne.size();
		if(sizeOfListOne == 0){
			return -1+"";
		}
		listOne.removeAll(listTwo);
		int numberOfSimilarItems = sizeOfListOne - listOne.size();
		
		return (double) numberOfSimilarItems/sizeOfListOne +"; " +numberOfSimilarItems/(sizeOfListOne+listTwo.size()) +"; " +
		sizeOfListOne +"; "+listTwo.size()+ "; "+numberOfSimilarItems;
	}

}
