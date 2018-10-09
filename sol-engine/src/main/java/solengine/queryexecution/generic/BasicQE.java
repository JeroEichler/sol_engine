package solengine.queryexecution.generic;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <predicate> <object>
 * when exists
 * 		<subject> <predicate> <object>
 * 
 * obs.: It is just a small sample of the dataset. For this reason the query is limited to provide only three query
 * results.
 * 
 *****************************************************************************************************************/
public class BasicQE extends QueryExecutor{
	
	public BasicQE(String endpoint, List<String> param) {
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		this.querySolution = param;
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Rdf_TypeProperty+"> ?object}" +
				"	WHERE{" +
				"		<"+subject+"> <"+Vocabulary.Rdf_TypeProperty+"> ?object." +
				"		filter (!EXISTS{" +
				"			select distinct ?object where { ?a <"+Vocabulary.Rdfs_SubclassOfProperty+"> ?object} " +
				"		})" +
				"	}"
	            ;
		
	}
	
	protected BasicQE() {}

}
