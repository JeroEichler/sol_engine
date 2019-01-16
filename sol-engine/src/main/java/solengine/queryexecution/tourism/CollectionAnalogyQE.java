package solengine.queryexecution.tourism;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <sol:analogousTo> <museumSimilar>
 * when exists
 * 		<subject> <dbp:collection> <entity>
 * 		<museumSimilar> <dbp:collection> <entity>
 * 
 *****************************************************************************************************************/
public class CollectionAnalogyQE extends QueryExecutor {
	
	public CollectionAnalogyQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?similarEntity}" +
				"	WHERE{" +
				//selecting entities with the object of subquery
				"		?similarEntity <"+Vocabulary.DB_CollectionProperty+"> ?object." +
				//selecting the object of the <subject> on <dbp:collection>
				"		{" +
				"			SELECT ?object" +
				"			WHERE {" +
				"				<"+subject+"> <"+Vocabulary.DB_CollectionProperty+"> ?object." +
				"			}" +
				"		}" +
				"		FILTER (?similarEntity != <"+subject+">)" +		
				"	}";
		
		this.querySolution = param;
		
	}

}
