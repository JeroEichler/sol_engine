package solengine.utils;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import solengine.model.QueryResult;
import solengine.utils.dto.QueryResultDto;
import solengine.utils.dto.TripleDto;

public class ModelConverter {
	
	public static QueryResultDto convert(QueryResult origin) {
		QueryResultDto queryResult = new QueryResultDto();
		queryResult.result = origin.getResult();		

		StmtIterator iter = origin.getAdditionalInfo().listStatements();
		while (iter.hasNext()) {
			Statement stmt      = iter.nextStatement();  // get next statement
		    String  subject   = stmt.getSubject().getURI();     // get the subject
		    String  predicate = stmt.getPredicate().getURI();   // get the predicate
		    String object = null;
		    RDFNode   objectNode    = stmt.getObject();      // get the object

		    System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (objectNode instanceof Resource) {
		       object = ((Resource) objectNode).getURI();
		    } else {
		    	object =  object.toString();
		    }
		    TripleDto triple = new TripleDto(subject, predicate, object);
		    queryResult.triples.add(triple);
		}
		
		
		return queryResult;
	}
	

	public static String modelToString(Model model){
		StringWriter sw = new StringWriter();
		model.write(sw, Config.rdfFormat);
		String temp = sw.toString();
		
		return temp;
	}
	
	public static Model stringTomodel(String model){
		StringReader sr = new StringReader(model);
		Model loadedModel = ModelFactory.createDefaultModel();
		loadedModel.read(sr, null, Config.rdfFormat);
		
		return loadedModel;
	}

}
