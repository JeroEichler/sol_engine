package experiment.runners;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import solengine.configuration.Config;
import solengine.frontier.EngineInterface;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.zz.storage.NewStorage;

public class BasicRunner {
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);
	static int limit = Config.searchSize();

	public static void main(String[] args) {

		System.out.println("Guten Morgen!");
		
		for(String item : Config.loadQueryExecutorGroups()){
			System.out.println(item);
		}
		
		for(String item : datasetAddresses){
			System.out.println(item.concat("#"));
		}
		
		helloWorldExperiment();

	}
	
	private static void helloWorldExperiment() {
		
		long start = System.currentTimeMillis();
		for(int i =0; i<5; i=i+1){
			
			long startMicro = System.currentTimeMillis();
			
			Map<List<String>, QueryResponse> result = system.processOnDatasets(basicQuery(), datasetAddresses, limit, i);
			NewStorage.saveResults(result);
			
			long elapsedTimeMicro = System.currentTimeMillis() - startMicro;
			long elapsedTimeTotal = System.currentTimeMillis() - start;
			
			System.out.println("--> Passed Through "+i+" records at "+
			elapsedTimeMicro/1000F+" seconds, "+
			elapsedTimeTotal/(60*1000F)+" total. "
			);
		}
		
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

}
