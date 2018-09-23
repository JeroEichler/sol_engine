package solengine.querybuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import solengine.queryexecution.QueryElement;

/* ***************************************************************************************************************
 * Abstract class that implements the fundamental behaviour of a QueryBuilder.
 * 
 * A QueryBuilder has the following properties, that ought to be defined by the subclass.
 * 		(1) String endpoint;  //dataset endpoint address.
 * 		(2) String queryString; //original query string submitted.
 * 		(3) String extractionFactor;  //the information retrieved about the query string.
 *****************************************************************************************************************/
public abstract class QueryBuilder extends QueryElement implements IQueryBuilder {

	//Common Properties
	protected String endpoint, queryString, extractionFactor;

	/* ***************************************************************************************************************
	 * Function that invoke a sparql query call and returns the query string.
	 * 
	 *****************************************************************************************************************/
	@Override
	public String call() throws Exception {
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;
            
            // Execute.
            ResultSet rs = qexec.execSelect();
            List<String> relevantResources = extractRelevantResources(rs);
            
            return buildNewQuery(relevantResources);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	/* ***************************************************************************************************************
	 * Function that extracts results that were ccxaptured by the extractionFactor.
	 * 
	 *****************************************************************************************************************/
	private List<String> extractRelevantResources(ResultSet rs) {
		List<String> results = new ArrayList<String>();
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
        		RDFNode varNode = soln.get(this.extractionFactor);
        		if(varNode.isResource()){
					Resource res = (Resource) varNode;
					if(res.getURI() != null){
						results.add(res.getURI());
					}
        		}
        }
		return results;
	}

}
