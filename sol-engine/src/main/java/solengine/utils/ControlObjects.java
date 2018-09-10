package solengine.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import solengine.model.QueryResult;

public class ControlObjects {
	
	public static QueryResult getEmptyQueryResult(){
      List<String> error = new ArrayList<String>();
      error.add("error");
      Model model = ModelFactory.createDefaultModel();
      QueryResult qx = new QueryResult(error, model);
      return qx;
	}

}
