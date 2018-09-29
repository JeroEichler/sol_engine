package solengine.model.dto;

import java.util.ArrayList;
import java.util.List;

public class QueryResponseDto {
	
	public List<String> result;
	public List<TripleDto> triples;
	
	public QueryResponseDto(){
		this.triples = new ArrayList<TripleDto>();
	}

}
