package solengine.queryexecution.generic;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:seeAlso> <resource>
 * when exists
 * 		<subject> <rdf:seeAlso> <resource>
 * 
 * 
 *****************************************************************************************************************/
public class SeeAlsoSurprisingObservationQE extends QueryExecutor {
	
	public SeeAlsoSurprisingObservationQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Rdfs_SeeAlsoProperty+"> ?entity}" +
				"	WHERE{" +
				"		<"+subject+"> <"+Vocabulary.Rdfs_SeeAlsoProperty+"> ?entity ." +
				"	}"
				;
		this.querySolution = param;
	}
	
}
