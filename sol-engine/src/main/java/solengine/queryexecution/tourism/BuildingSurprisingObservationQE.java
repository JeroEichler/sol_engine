package solengine.queryexecution.tourism;

import java.util.List;

import solengine.queryexecution.QueryExecutor;
import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:seeAlso> <resource>
 * when exists
 * 		<entity> <dbp:significantBuilding> <resource> and
 * 		<entity> <dbp:significantBuilding> <subject>
 * for any entity <entity>
 * 
 *****************************************************************************************************************/
public class BuildingSurprisingObservationQE extends QueryExecutor{
	
	public BuildingSurprisingObservationQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Rdfs_SeeAlsoProperty+"> ?building}" +
				"	WHERE{" +
				"		?entity <"+Vocabulary.DB_SignificantBuildingProperty+">  <"+subject+">." +
				"		?entity <"+Vocabulary.DB_SignificantBuildingProperty+"> ?building." +
				"		FILTER(?building != <"+subject+">)" +
				"	}";
		
		this.querySolution = param;
	}

}
