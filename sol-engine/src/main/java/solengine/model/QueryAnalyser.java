package solengine.model;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.Syntax;

import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that analyze and extract information from a given query string.
 * 
 * 
 *****************************************************************************************************************/
public class QueryAnalyser {

	/* ***************************************************************************************************************
	 * Function that extracts the variables of a given query string
	 * 
	 *****************************************************************************************************************/
	public static List<String> extractVariables(String queryString){
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
		List<String> vars = query.getResultVars();
		
		//for(String var :vars){
//			System.out.println(var);
		//}
		
//		System.out.println("-----------------");
		
		return vars;
	}
	
	@SuppressWarnings("unused")
	public static boolean isQueryValid(String queryString) {
		try {
			Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
		}
		catch(QueryParseException e) {
			return false;
		}		
		return true;
	}
	
	//TODO if query has already the limit clause
	public static String limitQuery(String queryString, int step) {
		queryString = queryString + "LIMIT " + Vocabulary.searchResultsSize 
                + " OFFSET " +Vocabulary.searchResultsSize * step + " "
                ;
		return queryString;
	}

}
