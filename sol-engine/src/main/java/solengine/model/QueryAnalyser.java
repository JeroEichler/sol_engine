package solengine.model;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.Syntax;

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
		
//		for(String var :vars){
//			System.out.println(var);
//		}
		
//		System.out.println("-----------------");
		
		return vars;
	}
	
	public static boolean isQueryValid(String queryString) {
		try {
			if(queryString == null || queryString.length() == 0) {
				return false;
			}
			QueryFactory.create(queryString, Syntax.syntaxARQ);
		}
		catch(QueryParseException e) {
			return false;
		}		
		return true;
	}
	
	//TODO what happens if query has already the limit clause
	public static String limitQuery(String queryString, int limitValue) {
		queryString = queryString + " LIMIT " + limitValue
                ;
		return queryString;
	}
	
	//TODO what happens if query has already the limit clause
	public static String limitQueryWithOffset(String queryString, int limitValue, int stepValue) {
		queryString = limitQuery(queryString, limitValue)
				+ " OFFSET " +limitValue * stepValue + " "
				;
		return queryString;
	}

}
