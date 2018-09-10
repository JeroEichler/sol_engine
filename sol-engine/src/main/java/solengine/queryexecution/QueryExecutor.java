package solengine.queryexecution;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import solengine.model.QueryResult;
import solengine.queryexecution.generic.SimpleBasicQueryExecutor;
import solengine.utils.Config;
import solengine.utils.ControlObjects;

/* ***************************************************************************************************************
 * Abstract class that implements the fundamental behaviour of a QueryExecutor
 * 
 * A QueryExecutor has the following properties.
 *****************************************************************************************************************/
public abstract class QueryExecutor extends QueryElement implements IQueryExecutor {
	
	//Common Properties
		protected String endpoint, queryString, subject;
		protected List<String> querySolution;


		/* ***************************************************************************************************************
		 * Function that invoke a sparql query call and returns the query results.
		 * 
		 * Parameters: void
		 * Returns: sol_tool_1_4.QueryResult.
		 * Obs.: For log and debug only.
		 *****************************************************************************************************************/
		@Override
		public QueryResult call() throws Exception {
			if(Config.queryExecutorLimited() || this.isBasicType()) {
				queryString = queryString +" LIMIT 2 ";
			}
			Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
	        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query) ) {
	            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

	    		// create the query result
	            Model model = qexec.execConstruct();
	            QueryResult qr = new QueryResult(querySolution, model);
//	            Convert.printModel2(model);
	            return qr;
	        } 
	        catch (Exception e) {
//	            e.printStackTrace();
	            System.out.println("..."+querySolution.toString());
	            return ControlObjects.getEmptyQueryResult();
	        }
		}


		private boolean isBasicType() {
			boolean outcome = this.getClass().isAssignableFrom(SimpleBasicQueryExecutor.class);
			return outcome;
		}

}
