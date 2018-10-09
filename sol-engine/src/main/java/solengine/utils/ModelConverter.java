package solengine.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import solengine.configuration.Config;
import solengine.model.QueryResponse;
import solengine.model.dto.QueryResponseDto;
import solengine.model.dto.TripleDto;

public class ModelConverter {
	
	public static QueryResponseDto convert(QueryResponse origin) {
		QueryResponseDto target = new QueryResponseDto();
		target.result = origin.getResult();		

		StmtIterator iter = origin.getAdditionalInfo().listStatements();
		while (iter.hasNext()) {
			Statement stmt      = iter.nextStatement();
		    String  subject   = stmt.getSubject().getURI();
		    String  predicate = stmt.getPredicate().getURI();
		    String object = null;
		    RDFNode   objectNode    = stmt.getObject();

		    if (objectNode instanceof Resource) {
		       object = ((Resource) objectNode).getURI();
		    } else {
		    	object =  objectNode.toString();
		    }
		    TripleDto triple = new TripleDto(subject, predicate, object);
		    target.triples.add(triple);
		}
		
		
		return target;
	}
	
	public static QueryResponse convert(QueryResponseDto origin) {
		
		List<String> result = origin.result;	
		
		Model model = ModelFactory.createDefaultModel();

		for(TripleDto triple : origin.triples) {
			Resource subject = model.createResource(triple.subject);
			Property predicate = model.createProperty(triple.predicate);
			Resource object = model.createResource(triple.object);
			subject.addProperty(predicate, object);
		}
		QueryResponse target = new QueryResponse(result, model);
		
		return target;
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
