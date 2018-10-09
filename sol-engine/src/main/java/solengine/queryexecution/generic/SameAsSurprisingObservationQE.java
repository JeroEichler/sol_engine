package solengine.queryexecution.generic;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <rdf:seeAlso> <resource>
 * when exists
 * 		<subject> <owl:sameAs> <resource>
 * 
 * obs.: Since owl:sameAs property is widely used, we are limiting the quantity of query results in order to provide
 * a small sample of the dataset. For this reason the query is limited to provide only two query results.
 * 
 *****************************************************************************************************************/
public class SameAsSurprisingObservationQE  extends QueryExecutor {
	
	public SameAsSurprisingObservationQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		this.limitedQueryExecutor = true;
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Rdfs_SeeAlsoProperty+"> ?entity}" +
				"	WHERE{" +
				"		<"+subject+"> <"+Vocabulary.Owl_SameAsProperty+"> ?entity ." +
				"	}"
				;
		this.querySolution = param;
	}
	
}
