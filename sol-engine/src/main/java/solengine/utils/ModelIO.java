package solengine.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import solengine.configuration.Config;

public class ModelIO {

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

	/* ***************************************************************************************************************
	 * Function that iterate over a model in order to print its RDF statements.
	 * 
	 * Parameters: (1) org.apache.jena.rdf.model.Model model
	 * Returns: void.
	 * Obs.: For log and debug only.
	 *****************************************************************************************************************/
	public static void printModel2(Model rs){
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
	
	public static String printModel(Model rs){
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

	
	public static String printList(List<String> list){
		String temp = "";

		for(String resultItem : list){
			temp = temp + resultItem + " ";
		}
		return temp;
	}
	
	public static void printModel(ResultSet rs){
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
        	Resource StmtSubject = soln.getResource("subject");
        	System.out.println(StmtSubject.getURI());
        }
	}

	public static List<List<String>> formatResultsToString(ResultSet rs, List<String> variables){
		List<List<String>> results = new ArrayList<List<String>>();
		while(rs.hasNext()){
        	QuerySolution soln = rs.nextSolution() ;
        	List<String> resultItem = new ArrayList<String>();
        	for(String var : variables){
//        		Resource StmtSubject = soln.getResource(var);
//        		results.add(StmtSubject.getURI());
//        		System.out.println(StmtSubject.getURI());
        		RDFNode varNode = soln.get(var);

        		if(varNode.isResource()){
					Resource res = (Resource) varNode;
					if(res.getURI() != null){
						resultItem.add(res.getURI());
//						System.out.println(var+"---"+res.getURI());
					}
        		}
        		else{
        			Literal res = (Literal) varNode;
					String literalExpression = res.getString();
						resultItem.add(literalExpression);
//						System.out.println(var+"data: "+literalExpression);
        		}
        	}
        	results.add(resultItem);
        }
//		System.out.println("\n   "+ results.size()+"     \n");
		return results;
	}
}
