package solengine.utils;

public class StringFormatter {

	public static String clean(String text) {
		String x = text.replaceAll("\\W", "");;
		return x;		
	}
}
