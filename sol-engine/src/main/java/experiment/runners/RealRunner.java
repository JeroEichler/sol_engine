package experiment.runners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;
import solengine.zz.storage.RealStorage;

public class RealRunner {
	
	static int magicNumer = 5;
	
	static String[] names = {
			"all",				// 0
			"genAnalogy",		// 1
			"genDiffInversion",	// 2
			"genSameAsSO",		// 3
			"genSeeAlsoSO",		// 4
			"musicAssocSO",		// 5
			"musicInflAnalogy"	// 6
		};
		
	static String[] query = {
			"band",
			"m-artist"
		};
	
	static String[] mode = {
			"limited",
			"full"
		};
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static String queryOn = query[0];
	public static String modeOn = mode[1];
	public static String baseProject = names[magicNumer];
	
	public static String baseFolder = queryOn + "//" + modeOn +"//" + baseProject;
	
	static String baseListFile = "__userResults"; 

	public static void main(String[] args) {
		theOneThatStoresBasicResults();
		theOneThatReadsBasicResults();

	}

	private static void theOneThatStoresBasicResults() {
		
		List<List<String>> finalResult = new ArrayList<List<String>>();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10000; i=i+10000) {
			List<List<String>> result = system.ordinaryProcess(makeQuery(i), datasetAddresses);
			finalResult.addAll(result);
			System.out.println(finalResult.size()+"=====================================>");
		}
			
		System.out.println("\n\n"+finalResult.size()+"--------------------------------->");
		RealStorage.saveEntity(baseFolder, baseListFile, finalResult);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
	}
	

	private static String makeQuery(int offset) {
		String type = "http://dbpedia.org/ontology/Band";
		if(queryOn.equals("m-artist")) {
			type = "http://dbpedia.org/ontology/MusicalArtist";
		}
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <" + type + "> ." + 
	                    "} " + 
	                    "LIMIT 10000 " + 
	                    "OFFSET " + offset + " "	                    
	                    ;
		
		return userQuery;
	}

	
	private static void theOneThatReadsBasicResults() {
		List<List<String>> listR = RealStorage.readListList(baseFolder, baseListFile);
		for(List<String> r: listR) {
			if(r.contains("http://dbpedia.org/resource/The_Beatles"))
				System.out.println(r +"  "+ r.size());
		}
		System.out.println(listR.size());
		
	}

}
