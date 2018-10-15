package experiment.runners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;
import solengine.utils.RealStorage;

public class RealRunner {
	
	static int magicNumer = 4;
	
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
			"full",
			"limited"
		};
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static String baseProject = names[magicNumer];
	public static String baseFolder = query[1] + "//" + mode[0] +"//" + baseProject;
	
	static String baseListFile = "__userResults"; 

	public static void main(String[] args) {
		theOneThatStoresBasicResults();
		theOneThatReadsBasicResults();

	}

	private static void theOneThatStoresBasicResults() {
		
		List<List<String>> finalResult = new ArrayList<List<String>>();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10000; i=i+10000) {
			List<List<String>> result = system.ordinaryProcess(bandQuery(i), datasetAddresses);
			finalResult.addAll(result);
			System.out.println(finalResult.size()+"=====================================>");
		}
			
		System.out.println("\n\n"+finalResult.size()+"--------------------------------->");
		RealStorage.saveEntity(baseFolder, baseListFile, finalResult);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
	}
	

	private static String bandQuery(int offset) {
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
	
	private static String musicArtistQuery(int offset) {
		//int actualOffset = 10000 * offset;
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <http://dbpedia.org/ontology/MusicalArtist> ." + 
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
