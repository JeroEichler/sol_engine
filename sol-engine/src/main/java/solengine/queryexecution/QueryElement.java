package solengine.queryexecution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QueryElement {
	

	/* ***************************************************************************************************************
	 * Function that provides a random number between 0 and 10.
	 * 
	 * Parameters: void
	 * Returns: int randomnumber.
	 *****************************************************************************************************************/
	protected int getRandomNumber(){
		Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(10);
		return randomInt;
	}
	
	/* ***************************************************************************************************************
	 * Function that randomly extracts a term from a list. This term must be a URI from a resource.
	 * 
	 * Parameters: List<String> terms
	 * Returns: String random term.
	 *****************************************************************************************************************/
	protected String getRandomResource(List<String> terms){
		List<String> objectTyped = new ArrayList<String>();
		for(String term : terms){
			if(term.contains("http://")){
				objectTyped.add(term);
			}
		}
		if(objectTyped.size() > 0) {
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(objectTyped.size());
			return objectTyped.get(randomInt);
		}
		else {
			return "No URIs were provided.";
		}
	}

}
