package solengine.queryexecution.tourism;

import java.util.List;

import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutor;

/* ***************************************************************************************************************
 * Class that customize the QueryExecutor to construct statements of the form
 * 		<subject> <sol:analogousTo> <museumSimilar>
 * when exists
 * 		<artWorkAux> <dbp:museum> <museumSimilar>
 * 		<artWorkAux> <dbp:author> <artist>
 * 		<anything> <dbp:museum> <subject>
 * 		<anything> <dbp:author> <artist>
 * 
 * to summarize, we link <subject> to <museumSimilar> when there is a <?artWorkAux> from a commom <artist> to both
 * <subject> to <museumSimilar>
 * 
 * obs.: Since the query below is very restrictive, we are not limiting the quantity of query results.
 * 
 *****************************************************************************************************************/
public class ArtworkAnalogyQE extends QueryExecutor{

	public ArtworkAnalogyQE(String endpoint, List<String> param) {
		
		this.endpoint = endpoint;
		this.subject = this.getRandomResource(param);
		
		this.queryString  = 
				"CONSTRUCT {<"+subject+"> <"+Vocabulary.Stool_AnalogyProperty+"> ?museumSimilar}" +
				//selecting museums that have artworks from the artists of the subquery
				"	WHERE{" +
				"		?artWorkAux <http://dbpedia.org/ontology/museum> ?museumSimilar." +
				"		?artWorkAux <http://dbpedia.org/ontology/author> ?artist." +
				"		{" +
				//selecting artists that have art work on the <subject> museum
				"			SELECT ?artist (count(?artWork) as ?countArtWork)" +
				"			WHERE {" +
				"				?artWork <http://dbpedia.org/ontology/museum> <"+subject+">." +
				"				?artWork <http://dbpedia.org/ontology/author> ?artist." +
				"			}" +
				"			GROUP BY ?artist" +
				"			HAVING (count(?artWork) > 1)" +				
				"		}" +
				//removing the obvious <subject> reference.
				"		MINUS {?artWorkAux <http://dbpedia.org/ontology/museum>	<"+subject+">}" +
				"	}"+
				"	GROUP BY ?museumSimilar" +
				"	HAVING (count(?artWorkAux) > 1)" +
				"	ORDER BY DESC (count(?artWorkAux))";
		
		this.querySolution = param;
	}
}
