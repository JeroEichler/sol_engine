package solengine.model.dto;

import java.util.List;

public class AnalyzedQueryResponseDto {

	public QueryResponseDto queryResponse;
	public List<String> resultLabels;
	public List<String> additionalInfoLabels;
	public double unexpectednessScore;
	public boolean valid = true;
	public boolean emptyResponse = false;
	
	public AnalyzedQueryResponseDto() {
		
	}
	
	public AnalyzedQueryResponseDto(QueryResponseDto qr) {
		this.queryResponse = qr;
	}
}
