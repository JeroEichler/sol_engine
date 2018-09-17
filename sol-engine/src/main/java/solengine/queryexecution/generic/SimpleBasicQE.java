package solengine.queryexecution.generic;

import java.util.List;

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
public class SimpleBasicQE extends BasicQE{
	
	public SimpleBasicQE(String endpoint, List<String> param) {
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		this.querySolution = param;
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> ?property ?object}" +
				"	WHERE{" +
				"		<"+subject+"> ?property ?object." +
				"		filter (!regex(?property, 'http://dbpedia.org/ontology/wikiPage'))" +
				"	}"
	            ;
		
	}

}
