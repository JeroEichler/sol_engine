package solengine.queryexecution;

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

import solengine.model.QueryAnalyser;

public class UserQueryExecutor {
	
	public List<List<String>> queryEndpoint(String endpoint, String queryString) {
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

            // Execute.
            ResultSet rs = qexec.execSelect();
            List<List<String>> resultsFormatted = formatResultsToString(rs, QueryAnalyser.extractVariables(queryString));
            //this.printModel(rs);
            return resultsFormatted;
        } 
        catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("problema de UserQueryProcessor em queryEndpoint" + e.toString());
        }
		return null;
	}

	private void printModel(ResultSet rs){
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
        	Resource StmtSubject = soln.getResource("subject");
        	System.out.println(StmtSubject.getURI());
        }
	}

	private List<List<String>> formatResultsToString(ResultSet rs, List<String> variables){
		List<List<String>> results = new ArrayList<List<String>>();
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
        	List<String> resultItem = new ArrayList<String>();
        	for(String var : variables){
//        		Resource StmtSubject = soln.getResource(var);
//        		results.add(StmtSubject.getURI());
//        		System.out.println(StmtSubject.getURI());
        		RDFNode varNode = soln.get(var);

        		if(varNode.isResource()){
					Resource res = (Resource) varNode;
					if(res.getURI() != null){
						resultItem.add(res.getURI());
//						System.out.println(var+"---"+res.getURI());
					}
        		}
        		else{
        			Literal res = (Literal) varNode;
					String literalExpression = res.getString();
						resultItem.add(literalExpression);
//						System.out.println(var+"data: "+literalExpression);
        		}
        	}
        	results.add(resultItem);
        }
//		System.out.println("\n   "+ results.size()+"     \n");
		return results;
	}

}
