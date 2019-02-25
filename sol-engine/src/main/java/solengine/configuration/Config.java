package solengine.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {
	
	public static final String version = "1.1.2";

	public static final String rdfFormat = "N-TRIPLES";
	
	public static QESystemConfiguration qeConfiguration = new QESystemConfiguration(6);
	
	// it specifies whether the QueryExecutors will produce limited results or not.	
	public static boolean qeLimited = false;
	
	public static int limit = 2;
	
	public static void setLimit(int value) {
		qeLimited = true;		
		limit = value;
	}
	
	private static final Properties prop = buildProperties();
	
	public static String listDelimiter() {
		return prop.getProperty("listDelimiter");
	}

	public static List<String> domains() {
		List<String> domains = Config.buildList(prop.getProperty("domains"));
		return domains;
	}
	
	public static List<String> loadQueryExecutorGroups() {
		List<String> types = Config.buildList(prop.getProperty("QETypes"));
			
		return types;
	}

	private static Properties buildProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = Config.class.getResourceAsStream("/config.properties");
			if(input==null){
	            System.out.println("Sorry, unable to find " );
	            return null;
			}
			// load a properties file
			prop.load(input);
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

	
	public static List<String> buildList(String concatedList){
		String[] array = concatedList.split(Config.listDelimiter());
		List<String> list = Arrays.asList(array);
		return list;
	}

}
