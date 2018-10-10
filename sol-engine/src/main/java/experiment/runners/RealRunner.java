package experiment.runners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;
import solengine.utils.RealStorage;

public class RealRunner {
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);
	
	public static String baseFolder = "full//genSeeAlsoSO";

	public static void main(String[] args) {
		theOneThatStoresBasicResults();
		theOneThatReadsBasicResults();

	}

	private static void theOneThatStoresBasicResults() {
		
		List<List<String>> finalResult = new ArrayList<List<String>>();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10000; i=i+10000) {
			List<List<String>> result = system.ordinaryProcess(basicLimitedQuery(i), datasetAddresses);
			finalResult.addAll(result);
			System.out.println(finalResult.size()+"=====================================>");
		}
			
		System.out.println("\n\n"+finalResult.size()+"--------------------------------->");
		RealStorage.saveEntity(baseFolder, SmartRunner.baseListFile, finalResult);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
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

	
	private static void theOneThatReadsBasicResults() {
		List<List<String>> listR = RealStorage.readListList(baseFolder, SmartRunner.baseListFile);
		for(List<String> r: listR) {
			if(r.contains("http://dbpedia.org/resource/The_Beatles"))
				System.out.println(r +"  "+ r.size());
		}
		System.out.println(listR.size());
		
	}

}
