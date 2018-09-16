package solengine.utils.dto;

import java.util.ArrayList;
import java.util.List;

public class QueryResultDto {
	
	public List<String> result;
	public List<TripleDto> triples;
	
	public QueryResultDto(){
		this.triples = new ArrayList<TripleDto>();
	}

}
