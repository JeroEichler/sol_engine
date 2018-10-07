package solengine.model;

import java.util.List;

public class AnalyzedQueryResponse {
	
	public QueryResponse queryResponse;
	public List<String> resultLabels;
	public List<String> additionalInfoLabels;
	public double unexpectednessScore;
	
	public AnalyzedQueryResponse(QueryResponse qr) {
		this.queryResponse = qr;
	}

}
