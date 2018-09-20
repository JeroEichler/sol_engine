package solengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.rdf.model.*;

import solengine.utils.ModelConverter;


/* ***************************************************************************************************************
 * Class that augments a query result with additional information 
 * 
 * Properties:	(1) List<String> result; 		// represents the result of a SPARQL query,
 * 												// it is a list because the result of a SELECT SPARQL query may be
 * 												// composed of a set of columns.
 * 				(2) Model getAdditionalInfo;  	// represents a RDF data graph that provides additional information
 * 												// about the result property.
 *****************************************************************************************************************/

public class QueryResponse implements Serializable {
	
	private static final long serialVersionUID = 5743156082940385418L;
	
	List<String> result;
	String model;
	
	public QueryResponse(List<String> result, Model model){
		this.result = result;
		this.model = ModelConverter.modelToString(model);
	}
	
	public List<String> getResult(){
		return this.result;
	}
	
	public Model getAdditionalInfo(){
		return ModelConverter.stringTomodel(model);
	}
	
	public List<String> getObjects(){
		NodeIterator iteratorObject =  this.getAdditionalInfo().listObjects();
		List<String> resourceObjectsURI = iteratorObject.toList().stream()
				.filter(item -> item.isResource()).map(object -> object.asResource().getURI())
				.collect(Collectors.toCollection(ArrayList<String>::new));
		return resourceObjectsURI;
	}
}
