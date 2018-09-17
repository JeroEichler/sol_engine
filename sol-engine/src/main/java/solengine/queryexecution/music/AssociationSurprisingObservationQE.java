package solengine.queryexecution.music;

import java.util.List;

import solengine.queryexecution.QueryExecutor;
import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:seeAlso> <resource>
 * when exists
 * 		<entity> <dbp:associatedBand> <resource> and
 * 		<subject> <dbp:associatedBand> <resource>
 * for any entity <entity>
 * 
 *****************************************************************************************************************/
public class AssociationSurprisingObservationQE extends QueryExecutor {

	//constructor
	public AssociationSurprisingObservationQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Rdfs_SeeAlsoProperty+"> ?entity}" +
				"	WHERE{" +
				"		?entity <"+Vocabulary.DB_AssociatedBandProperty+"> ?band . " +
				"		{" +
				"			SELECT ?band " +
				"			WHERE {" +
				"				<"+subject+"> <"+Vocabulary.DB_AssociatedBandProperty+"> ?band. " +
				"			}" +
				"		}" +
				"		FILTER (?entity != <"+subject+">)" +		
				"	}"
				;
		this.querySolution = param;
	}

}
