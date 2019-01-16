package solengine.resultanalysis;

import java.util.ArrayList;
import java.util.List;

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

import solengine.model.Vocabulary;

public class DatasetOperations {

	public static List<String> getDataAboutResource(String datasetEndpoint, String resource){
		String queryString = buildQueryStringFromResource(resource);
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint, query) ) {
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

	private static String buildQueryStringFromResource(String resourceURI){
		String queryString  = 
				"SELECT ?object" +
				"	WHERE{" +
				"		<"+resourceURI+"> <"+Vocabulary.Rdf_TypeProperty+"> ?object." +
				"	}"
	            ;
		return queryString;
	}
	
	private static List<String> extractResources(ResultSet rs) {
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
}
