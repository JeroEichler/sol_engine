package solengine.queryexecution.generic;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

public class SimilarityQE extends QueryExecutor {

	public SimilarityQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_SimilarityProperty+"> ?similarEntity}" +
				//selecting entities of the category of subquery
				"	WHERE{" +
				"		?similarEntity <"+Vocabulary.DC_SubjectProperty+"> ?category." +
				"		{" +
				//selecting categories close to <subject> category
				"			SELECT ?category " +
				"			WHERE {" +
				"				{" +
								//selecting categories more specific than <subject> category
				"					?category <"+Vocabulary.Skos_BroaderProperty+"> ?auxCategory." +
				"					<"+subject+"> <"+Vocabulary.DC_SubjectProperty+"> ?auxCategory." +
				"				}" +
				"				UNION" +
				"				{" +
								//selecting categories more general than <subject> category
				"					?auxCategory <"+Vocabulary.Skos_BroaderProperty+"> ?category." +
				"					<"+subject+"> <"+Vocabulary.DC_SubjectProperty+"> ?auxCategory." +
				"				}" +
				"			}" +		
				"		}" +
				"		FILTER (?similarEntity != <"+subject+">)" +		
				"	}"+
				"	GROUP BY ?similarEntity" +
				"	HAVING (count(?category) > 5)" +
				"	ORDER BY DESC (count(?category))";
		
		this.querySolution = param;
	}

}
