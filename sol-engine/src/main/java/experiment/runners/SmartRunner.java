package experiment.runners;

import java.util.Arrays;
import java.util.List;

import solengine.configuration.Config;
import solengine.configuration.QESystemConfiguration;
import solengine.frontier.EngineInterface;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.utils.StringFormatter;
import solengine.zz.storage.NewNewStorage;
import solengine.zz.storage.RealStorage;

public class SmartRunner {
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);
		
	static int i=0;

	public static void main(String[] args) {
		
		Config.qeConfiguration = new QESystemConfiguration(RealRunner.magicNumer);
		if(RealRunner.modeOn.equals("limited")) {
			Config.setLimit(1);
		}
		
		System.out.println("Here goes " + RealRunner.baseFolder);
		
		long start = System.currentTimeMillis();
//		doIt();
		optimDoIt();
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		System.out.println("Here lies " + RealRunner.baseFolder);
	}
	


	private static void doIt() {
		List<List<String>> baseResults = RealStorage.readListList(RealRunner.baseFolder, RealRunner.baseListFile);
		System.out.println("starting with "+baseResults.size());
		for(List<String> result : baseResults) {
			List<QueryResponse> responses = system.findResponse(result, datasetAddresses);
			for(QueryResponse response : responses) {
				persistResponse(response);
			}
		}
		
	}
	
	private static void optimDoIt() {
		List<List<String>> baseResults = RealStorage.readListList(RealRunner.baseFolder, RealRunner.baseListFile);
		System.out.println("starting with "+baseResults.size());
		
		List<QueryResponse> responses = system.findResponses(baseResults, datasetAddresses);
		
		for(QueryResponse response : responses) {
			persistResponse(response);
		}
	}
	
	private static void persistResponse(QueryResponse response) {
		
		String title = StringFormatter.clean(response.getResult());
		if(response.isValid()) {
			NewNewStorage.saveSingleResult(RealRunner.baseFolder, title, response);
			NewNewStorage.updateList(RealRunner.baseFolder, "__successX", title);
			NewNewStorage.updateList(AnalyzerRunner.baseFolder, "__base", title);
			RealStorage.reduceListList(RealRunner.baseFolder, RealRunner.baseListFile, response.getResult());

			// pro form
			printProgress();
		}
		else {
			System.out.println("danger");
			NewNewStorage.updateList(RealRunner.baseFolder, "__errorX", title);
		}
	}
	
	private static void printProgress() {
		if(i % 500 == 0) {
			System.out.println("passed by "+i);
		}
		i++;
	}
}
