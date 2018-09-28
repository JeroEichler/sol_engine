package solengine.utils.dto;

public class TripleDto {
	
	public TripleDto() {
	}
	
	public TripleDto(String s, String p, String o) {
		this.subject = s;
		this.predicate = p;
		this.object = o;
	}
	
	public String subject;
	public String predicate;
	public String object;

}
