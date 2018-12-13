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
	* Function that extracts the variables of a given query string.
	* 
	*****************************************************************************************************************/
	public static List<String> extractVariables(String queryString){
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ) ;
		List<String> vars = query.getResultVars();
		
		return vars;
	}
	
	/* ***************************************************************************************************************
	* Function that evaluates if a query string is valid.
	* 
	*****************************************************************************************************************/
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
	
	/* ***************************************************************************************************************
	* Function that evaluates if a query string is limited in results.
	* 
	*****************************************************************************************************************/
	public static boolean isQueryLimited(String queryString) {
		if(queryString.contains("LIMIT") || queryString.contains("limit")) {
			return true;
		}		
		return false;
	}
	
	/* ***************************************************************************************************************
	* Function that evaluates if a query string is offseted in results.
	* 
	*****************************************************************************************************************/
	public static boolean isQueryOffseted(String queryString) {
		if(queryString.contains("OFFSET") || queryString.contains("offset")) {
			return true;
		}		
		return false;
	}
	
	//TODO what happens if query has already the limit clause
	public static String limitQuery(String queryString, int limitValue) {
		if(!isQueryLimited(queryString)) {
			queryString = queryString + " LIMIT " + limitValue
	                ;
		}		
		return queryString;
	}
	
	//TODO what happens if query has already the limit clause
	public static String limitQueryWithOffset(String queryString, int limitValue, int stepValue) {
		queryString = limitQuery(queryString, limitValue);
		if(!isQueryOffseted(queryString)) {
			queryString = queryString + " OFFSET " +limitValue * stepValue + " "
					;
		}	
				
		return queryString;
	}

}
