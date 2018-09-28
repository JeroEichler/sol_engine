package solengine.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import solengine.model.QueryResponse;

public class ControlObjects {
	
	/* ***************************************************************************************************************
	* Function that provides QueryResponse object that is assigned as invalid.
	* 
	* Comment: It is used for disabling inconsistent QueryResponse caused by network exceptions.
	* 
	*****************************************************************************************************************/
	public static QueryResponse emitInvalidQueryResponse(){
      List<String> error = new ArrayList<String>();
      error.add(Vocabulary.ErrorMessage);
      Model model = ModelFactory.createDefaultModel();
      QueryResponse qx = new QueryResponse(error, model);
      return qx;
	}

}
