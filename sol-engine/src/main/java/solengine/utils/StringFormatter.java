package solengine.utils;

import java.util.List;

public class StringFormatter {

	public static String clean(String text) {
		String term = text.replaceAll("\\W", "");
		return term;		
	}
	
	public static String removeNamespace(String text) {
		String[] terms = text.split("\\/");
		String term = terms[terms.length - 1];
		
		return term;		
	}
	
	public static String clean(List<String> list) {
		String name = "";
		for(String term : list) {
			String label = StringFormatter.removeNamespace(term);
			label = StringFormatter.clean(label);
			name = name.concat(label);
		}
		return name;		
	}
}
