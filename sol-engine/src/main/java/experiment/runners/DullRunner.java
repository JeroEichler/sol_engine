package experiment.runners;

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
		helloWorldExperiment();
		
		readHelloWorldResult();

	}

	private static void helloWorldExperiment() {
		
		long start = System.currentTimeMillis();
			
		List<List<String>> result = system.ordinaryProcess(basicQuery(), datasetAddresses);
		NewStorage.saveEntity("userResults", result);
			
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
	
	private static void readHelloWorldResult() {
		List<List<String>> listR = NewStorage.readPersistedResults("userResults");
		for(List<String> r: listR) {
			System.out.println(r);
		}
		System.out.println(listR.size());
		
	}

}
