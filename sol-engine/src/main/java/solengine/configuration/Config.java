package solengine.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

	public static final String baseFolder = "C:/solengine/experiment/";
	public static final String root = "C://solengine//data//";
	public static final String baseFolder2 = root+"full//genSeeAlsoSO//";
	
	public static final String rdfFormat = "N-TRIPLES";
	
	private static final Properties prop = buildProperties();
	
	public static String listDelimiter() {
		return prop.getProperty("listDelimiter");
	}
	
	public static int searchSize() {
		return Integer.parseInt(prop.getProperty("searchSize"));
	}
	
	public static List<String> datsets() {
		List<String> datasets = Config.buildList(prop.getProperty("datasets"));
		return datasets;
	}

	public static List<String> domains() {
		List<String> domains = Config.buildList(prop.getProperty("domains"));
		return domains;
	}
	
	public static String rdfFormat() {
		return prop.getProperty("rdfFormat");
	}
	
	public static List<String> loadQueryExecutorGroups() {
		List<String> types = Config.buildList(prop.getProperty("QETypes"));
			
		return types;
	}
	

	
	public static Boolean queryExecutorLimited() {
		return Boolean.parseBoolean(prop.getProperty("QELimit"));
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
