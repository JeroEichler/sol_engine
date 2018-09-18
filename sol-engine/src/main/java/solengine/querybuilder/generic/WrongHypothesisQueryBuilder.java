package solengine.querybuilder.generic;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;

import solengine.querybuilder.QueryBuilder;
import solengine.utils.Vocabulary;

public class WrongHypothesisQueryBuilder extends QueryBuilder {
	
	//constructor
	public WrongHypothesisQueryBuilder(String endpoint, String subQueryString) {
		
		this.endpoint = endpoint;
//			this.subject = this.getRandomResource(param);
		
		Query subQuery = QueryFactory.create(subQueryString , Syntax.syntaxARQ);
		String var = subQuery.getResultVars().get(0);
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
		
//			this.querySolution = param;
	}

}
