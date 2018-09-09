package solengine.model;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.rdf.model.*;


/* ***************************************************************************************************************
 * Class that augments a query result with additional information 
 * 
 * 
 * For now, considering only a single column result
 * TODO: reconsider the name of the class.
 *****************************************************************************************************************/

public class QueryResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5743156082940385418L;
	
	List<String> result;
//	private transient Model additionalInfo;
	String model;
	
	public QueryResult(List<String> result){
		this.result = result;
//		this.additionalInfo = ModelFactory.createDefaultModel();
	}
	
	public QueryResult(List<String> result, Model model){
		this.result = result;
//		this.additionalInfo = model;
		this.model = this.modelToString(model);
	}
	
	public List<String> getResult(){
		return this.result;
	}
	

	
	public String modelToString(Model model){
		StringWriter sw = new StringWriter();
		model.write(sw, "N-TRIPLES");
		String temp = sw.toString();
		return temp;
	}
	
	public Model stringTomodel(String model){
		StringReader sr = new StringReader(model);
		Model loadedModel = ModelFactory.createDefaultModel();
		loadedModel.read(sr, null, "N-TRIPLES");
		return loadedModel;
	}
	
	public Model getAdditionalInfo(){
		return this.stringTomodel(model);
	}
	
	public List<String> getObjects(){
		NodeIterator iteratorObject =  this.getAdditionalInfo().listObjects();
		List<String> resourceObjectsURI = iteratorObject.toList().stream()
				.filter(item -> item.isResource()).map(object -> object.asResource().getURI())
				.collect(Collectors.toCollection(ArrayList<String>::new));
		return resourceObjectsURI;
	}
	
	public String toString(){
		String temp = printResult(result);
		temp = temp+"\n"+printModel(this.getAdditionalInfo());
		return temp;
	}
	
	public void printString(){
		System.out.println(result);
		this.printModel2(this.getAdditionalInfo());
	}
	


	public void printModel2(Model rs){
		StmtIterator iter = rs.listStatements();

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // object is a literal
		        System.out.print(" \"" + object.toString() + "\"");
		    }

		    System.out.println(" .");
		}
	}
	
	public String printModel(Model rs){
		StmtIterator iter = rs.listStatements();
		String temp = "";

		// print out the predicate, subject and object of each statement
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // get next statement
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    temp = temp+subject.toString()+" " + predicate.toString() + " ";
		    if (object instanceof Resource) {
		    	temp = temp+object.toString();
		    } else {
		        // object is a literal
		    	temp = temp+" \"" + object.toString() + "\"";
		    }

		    temp = temp+" .\n";
		}
		return temp;
	}
	

	
	public String printResult(List<String> result){
		String temp = "";

		for(String resultItem : result){
			temp = temp + resultItem + " ";
		}
		return temp;
	}

}
