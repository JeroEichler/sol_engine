package solengine.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import solengine.model.QueryResponse;

public class ControlObjects {
	
	public static QueryResponse getEmptyQueryResult(){
      List<String> error = new ArrayList<String>();
      error.add("error");
      Model model = ModelFactory.createDefaultModel();
      QueryResponse qx = new QueryResponse(error, model);
      return qx;
	}

}
