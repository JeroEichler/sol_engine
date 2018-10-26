package solengine.model;

import java.util.HashMap;
import java.util.List;

public class AnalyzedQueryResponse {
	
	public QueryResponse queryResponse;
	public List<String> resultLabels;
	public HashMap<String,List<String>> additionalInfoLabels;
	public double unexpectednessScore;
	public boolean valid = true;
	public boolean emptyResponse = false;
	
	public AnalyzedQueryResponse(QueryResponse qr) {
		this.queryResponse = qr;
	}

}
