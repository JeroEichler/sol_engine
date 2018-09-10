package solengine.queryexecution;

import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import solengine.model.QueryAnalyser;
import solengine.utils.ModelIO;

public class UserQueryExecutor {
	
	public List<List<String>> queryEndpoint(String endpoint, String queryString) {
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

            // Execute.
            ResultSet rs = qexec.execSelect();
            List<List<String>> resultsFormatted = ModelIO.formatResultsToString(rs, QueryAnalyser.extractVariables(queryString));
            //ModelIO.printModel(rs);
            return resultsFormatted;
        } 
        catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("problema de UserQueryProcessor em queryEndpoint" + e.toString());
        }
		return null;
	}


}
