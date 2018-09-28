package solengine.model;

import java.util.List;

import solengine.utils.Vocabulary;

public class QueryResponseAnalyser {
	
	/* ***************************************************************************************************************
	* Function that evaluates if a QueryResponse object is valid.
	* 
	*****************************************************************************************************************/
	public static boolean isValid(QueryResponse response){
		List<String> results = response.getResult();
		boolean valid = !results.contains(Vocabulary.ErrorMessage);
		
		return valid;
	}

}
