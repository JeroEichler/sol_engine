package solengine.queryexecution.generic;

import java.util.List;

import solengine.queryexecution.QueryExecutor;
import solengine.utils.Vocabulary;

public class HierarchieAnalogyQE_Alt3 extends QueryExecutor {

	public HierarchieAnalogyQE_Alt3(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?similarEntity}" +
				"	WHERE{" +
				//selecting entities of the category of subquery
				"		?similarEntity <"+Vocabulary.DC_SubjectProperty+"> ?category." +
				//selecting a more specific category than <subject> category
				"		?sonCategory <"+Vocabulary.Skos_BroaderProperty+"> ?auxCategory." +
				//making <category> also a broader category than son category
				"		?sonCategory <"+Vocabulary.Skos_BroaderProperty+"> ?category." +
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
	
//	public void beatIt(){
//		this.queryString  = 
//				"CONSTRUCT {<"+subject+"> <"+Vocabulary.AnalogyProperty+"> ?similarEntity}" +
//				//selecting entities of the category of subquery
//				"	WHERE{" +
//				"		?similarEntity <"+Vocabulary.SubjectProperty+"> ?category." +
//				//selecting categories close to <subject> category
//				"		<"+subject+"> <"+Vocabulary.SubjectProperty+"> ?auxCategory." +
//				//selecting a more general category than <subject> category
//				"		?auxCategory <"+Vocabulary.BroaderProperty+"> ?fatherCategory." +
//				"		?fatherCategory <"+Vocabulary.BroaderProperty+"> ?grandfatherCategory." +
//				"		?grandfatherCategory <"+Vocabulary.BroaderProperty+"> ?grangrandfatherCategory." +
//				"		?grangrandfatherCategory <"+Vocabulary.BroaderProperty+"> ?me." +
//				//making <category> also a more specific category than father category
//				"		?category <"+Vocabulary.BroaderProperty+"> ?fatherCategory." +
//				"		FILTER (?similarEntity != <"+subject+">)" +		
//				"	}"+
//				"	LIMIT 3";
//	}

}
