package solengine.queryexecution.music;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:seeAlso> <resource>
 * when exists
 * 		<entity> <dbp:influenced> <resource> 
 * 		<entity> <dbp:influenced> <subject>
 * 		<resource> <dc:subject> <auxCategory> 
 * 		<subject> <dc:subject> <auxCategory>
 * for any entity <entity>
 * 
 *****************************************************************************************************************/
public class InfluenceCategoryAnalogyQE extends QueryExecutor {

	public InfluenceCategoryAnalogyQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?analogousEntity}" +
				"	WHERE{" +
				"		?auxInfluence <"+Vocabulary.DB_InfluencedProperty+"> ?analogousEntity; " +
				"			<"+Vocabulary.DB_InfluencedProperty+">  <"+subject+">. " +
				"		<"+subject+"> <"+Vocabulary.DC_SubjectProperty+"> ?auxCategory. " +
				"		?analogousEntity <"+Vocabulary.DC_SubjectProperty+"> ?auxCategory. " +
				"		FILTER (?analogousEntity != <"+subject+">)" +	
				"	}"
				;
		
		this.querySolution = param;
	}

}