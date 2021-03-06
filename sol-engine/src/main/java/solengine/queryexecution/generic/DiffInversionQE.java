package solengine.queryexecution.generic;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:differentFrom> <resource>
 * when exists
 * 		<subject> <owl:differentFrom> <resource>
 * 
 *****************************************************************************************************************/
public class DiffInversionQE extends QueryExecutor {
	
	public DiffInversionQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Owl_differentFromProperty+"> ?entity}" +
				"	WHERE{" +
				"		<"+subject+"> <"+Vocabulary.Owl_differentFromProperty+"> ?entity ." +
				"	}"
				;
		this.querySolution = param;
	}

}
