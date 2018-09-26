package experiment.runners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import solengine.frontier.EngineInterface;
import solengine.utils.MapperFactory;
import solengine.utils.NewStorage;
import solengine.utils.Vocabulary;

public class DullRunner {
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static void main(String[] args) {
		//helloWorldExperiment();
//		helloWorldExperiment2();
		readHelloWorldResult();

	}

	private static void helloWorldExperiment() {
		
		long start = System.currentTimeMillis();
			
		List<List<String>> result = system.ordinaryProcess(basicQuery(), datasetAddresses);
		System.out.print(result.size()+"aaaaaaaaaaaaaaaaaaaaaaaaaa");
		NewStorage.saveEntity("userResults", result);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
	}

	private static void helloWorldExperiment2() {
		
		List<List<String>> finalResult = new ArrayList<List<String>>();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<70000; i=i+10000) {
			List<List<String>> result = system.ordinaryProcess(basicLimitedQuery(i), datasetAddresses);
			finalResult.addAll(result);
			System.out.println(finalResult.size()+"=====================================>");
		}
			
		System.out.println("\n\n"+finalResult.size()+"--------------------------------->");
		NewStorage.saveEntity("userResults", finalResult);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
	}
	
	private static String basicQuery() {
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <http://dbpedia.org/ontology/Band> ." + 
	                    "} "
	                    ;
		
		return userQuery;
	}
	

	private static String basicLimitedQuery(int offset) {
		//int actualOffset = 10000 * offset;
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <http://dbpedia.org/ontology/Band> ." + 
	                    "} " + 
	                    "LIMIT 10000 " + 
	                    "OFFSET " + offset + " "	                    
	                    ;
		
		return userQuery;
	}
	
	private static void readHelloWorldResult() {
		List<List<String>> listR = NewStorage.readPersistedResults("userResults");
		for(List<String> r: listR) {
			System.out.println(r +"  "+ r.size());
		}
		System.out.println(listR.size());
		
	}

}
