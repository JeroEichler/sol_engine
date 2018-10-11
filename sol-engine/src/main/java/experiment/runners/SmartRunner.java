package experiment.runners;

import java.util.Arrays;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.utils.NewNewStorage;
import solengine.utils.RealStorage;
import solengine.utils.StringFormatter;

public class SmartRunner {
	
	static String baseListFile = "__userResults"; 
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);
		
	static int i=0;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		doIt();

		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
//		doIt2();
	}
	


	private static void doIt() {
		List<List<String>> baseResults = RealStorage.readListList(RealRunner.baseFolder, baseListFile);
		System.out.println("starting with "+baseResults.size());
		for(List<String> result : baseResults) {
			List<QueryResponse> responses = system.findResponse(result, datasetAddresses);
			for(QueryResponse response : responses) {
				String title = StringFormatter.clean(response.getResult());
				if(response.isValid()) {
					NewNewStorage.saveSingleResult(RealRunner.baseFolder, title, response);
					NewNewStorage.updateList(RealRunner.baseFolder, "__successX", title);
					NewNewStorage.updateList(AnalyzerRunner.baseFolder, "__base", title);
					RealStorage.reduceListList(RealRunner.baseFolder, baseListFile, result);

					// pro form
					printProgress();
				}
				else {
					System.out.println("danger");
					NewNewStorage.updateList(RealRunner.baseFolder, "__errorX", title);
				}
			}
		}
		
	}
	
	private static void printProgress() {
		if(i % 500 == 0) {
			System.out.println("passed by "+i);
		}
		i++;
	}
	


}
