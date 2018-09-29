package solengine.queryexecution;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import solengine.model.QueryResponse;
import solengine.queryexecution.generic.SimpleBasicQE;
import solengine.utils.Config;

/* ***************************************************************************************************************
 * Abstract class that implements the fundamental behaviour of a QueryExecutor
 * 
 * 
 * A QueryExecutor has the following properties.
 *****************************************************************************************************************/
public abstract class QueryExecutor extends QueryElement implements IQueryExecutor {
	
	//Common Properties
	protected String endpoint, queryString, subject;
	protected List<String> querySolution;
	protected boolean limitedQueryExecutor = false;


	/* ***************************************************************************************************************
	 * Function that invoke a sparql query call and returns the query results.
	 * 
	 * Parameters: void
	 * Returns: QueryResponse.
	 *****************************************************************************************************************/
	@Override
	public QueryResponse call() throws Exception {
		if(Config.queryExecutorLimited() || this.limitedQueryExecutor || this.isBasicType()) {
			queryString = queryString +" LIMIT 2 ";
		}
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

    		// create the query result
            Model model = qexec.execConstruct();
            QueryResponse qr = new QueryResponse(querySolution, model);
//	            Convert.printModel2(model);
            return qr;
        } 
        catch (Exception e) {
//	            e.printStackTrace();
            System.out.println("..."+querySolution.toString());
            return new QueryResponse(querySolution, false);
        }
	}


	private boolean isBasicType() {
		boolean outcome = this.getClass().isAssignableFrom(SimpleBasicQE.class);
		return outcome;
	}

}
