package experiment.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.configuration.Config;
import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;

public class QueryBuilderRunner {

	static int limit = Config.searchSize();

	public static void main(String[] args) {

		queryBuilderExperiment1();

	}

	private static void queryBuilderExperiment1() {
		EngineInterface system = new EngineInterface();
		List<String> datasetAddresses = new ArrayList<>();
		datasetAddresses.add(Vocabulary.DBpediaEndpoint);
		
		long start = System.currentTimeMillis();
			
			List<String> queries = system.createQueries(queryWithoutOffset2(), datasetAddresses);
			
			for(String query : queries) {
				System.out.println("-----------------------------------------------\n");
				System.out.println(query);

				System.out.println("-----------------------------------------------");
			}
			
			//Storage.saveProgress("C:/nada/2017ago/progresso.txt", i);
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

		
	}

	public static String queryWithoutOffset2(){
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <http://dbpedia.org/ontology/Band> ." + 
//	                    "	?subject <"+Vocabulary.DB_AssociatedBandProperty+"> ?labooel  ." + 
	                    "} "
	                    ;
		
		return userQuery;
	}

}
