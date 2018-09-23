package solengine.querybuilder.generic;

import java.util.Iterator;
import java.util.List;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;

import solengine.querybuilder.QueryBuilder;
import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that customize the QueryBuilder that formulates a second query string.
 * 
 * It composes a queryString that selects the three most popular categories of the subQueryString. The 
 * QueryBuilder.extractRelevantResources is used to extract relevant resources in the result of the query. In this
 * scenario.
 * 
 * These three categories are used in IQueryBuilder.buildNewQuery to define an alternative query.
 * 
 *****************************************************************************************************************/
public class WrongHypothesisQB extends QueryBuilder {
	
	String originalQuery;
	
	public WrongHypothesisQB(String endpoint, String subQueryString) {
		
		this.endpoint = endpoint;
		
		this.extractionFactor = "category";
		
		Query subQuery = QueryFactory.create(subQueryString , Syntax.syntaxARQ);
		String var = subQuery.getResultVars().get(0);
		this.originalQuery = subQueryString.replaceAll(var, "sub");
		this.queryString = "SELECT  (COUNT(?s) AS ?counter) ?category " +
						"WHERE { " +
						"	?s  <"+Vocabulary.DC_SubjectProperty+">  ?category " +
						"	FILTER ( ?s = ?"+var+" ) " +
						"	{ "+
						subQueryString +
						"	} " +
						"} " +
						"GROUP BY ?category " +
						"ORDER BY DESC(?counter) " +
						"OFFSET  1 " +
						"LIMIT   3"; 
	}
	
	public String buildNewQuery(List<String> resources) {
		String filter = "filter(?catAux IN (";
		Iterator<String> iterator = resources.iterator();
		while(iterator.hasNext()){
			filter = filter + "<" + iterator.next() +">";
			if(iterator.hasNext()){
				filter = filter + ", ";
			}
		}

		filter = filter + ")) ";
		String newQuery = "select distinct ?sub where { " +
							"	{ " +
							"		select ?sub where { " +
							"			?sub dct:subject ?catAux. " +
										filter+
							"		} " +
							"	} " +
							"	MINUS " +
							"	{ " +
									this.originalQuery +
							"	} " +
							"} " +
							"limit 100";
		return newQuery;
	}
}
