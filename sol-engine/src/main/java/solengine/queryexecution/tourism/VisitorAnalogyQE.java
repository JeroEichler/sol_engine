package solengine.queryexecution.tourism;

import java.util.List;

import solengine.queryexecution.QueryExecutor;
import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <sol:analogousTo> <museumSimilar>
 * when exists
 * 		<subject> <dbp:visitors> <entity>
 * 		<museumSimilar> <dbp:visitors> <entity>
 * 
 *****************************************************************************************************************/
public class VisitorAnalogyQE extends QueryExecutor {
	
	public VisitorAnalogyQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?similarEntity}" +
				"	WHERE{" +
				//selecting entities with the object of subquery
				"		?similarEntity <"+Vocabulary.DB_VisitorsProperty+"> ?object." +
				//selecting the object of the <subject> on <dbp:visitors>
				"		{" +
				"			SELECT ?object" +
				"			WHERE {" +
				"				<"+subject+"> <"+Vocabulary.DB_VisitorsProperty+"> ?object." +
				"			}" +
				"		}" +
				"		FILTER (?similarEntity != <"+subject+">)" +		
				"	}";
		
		this.querySolution = param;
		
	}

}
