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
 * 
 * For now, considering only a single column result
 * TODO: reconsider the name of the class.
 *****************************************************************************************************************/

public class QueryResult implements Serializable {
	
	private static final long serialVersionUID = 5743156082940385418L;
	
	List<String> result;
	String model;
	
	public QueryResult(List<String> result){
		this.result = result;
//		this.additionalInfo = ModelFactory.createDefaultModel();
	}
	
	public QueryResult(List<String> result, Model model){
		this.result = result;
//		this.additionalInfo = model;
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
