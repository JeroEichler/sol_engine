package solengine.queryexecution.generic;

import java.util.List;

import solengine.queryexecution.QueryExecutor;
import solengine.utils.Vocabulary;

public class HierarchieAnalogyQE_Alt2 extends QueryExecutor {

	public HierarchieAnalogyQE_Alt2(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?similarEntity}" +
				"	WHERE{" +
				//selecting entities of the category of subquery
				"		?similarEntity <"+Vocabulary.DC_SubjectProperty+"> ?category." +
				//selecting a more general category than <subject> category
				"		?auxCategory <"+Vocabulary.Skos_BroaderProperty+"> ?fatherCategory." +
				//making <category> also a more specific category than father category
				"		?category <"+Vocabulary.Skos_BroaderProperty+"> ?fatherCategory." +
				//selecting a category of the <subject> randomly
				"		{" +
				"			SELECT ?auxCategory" +
				"			WHERE {" +
				"				<"+subject+"> <"+Vocabulary.DC_SubjectProperty+"> ?auxCategory." +
				"			}" +
				"			OFFSET " + this.getRandomNumber() + " " +
				"			LIMIT 1" +
				"		}" +
				"		FILTER (?similarEntity != <"+subject+">)" +		
				"	}"
				;
		
		this.querySolution = param;
	}

}
