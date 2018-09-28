package experiment.runners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.QueryResponse;
import solengine.utils.NewNewStorage;
import solengine.utils.Vocabulary;
import solengine.utils.dto.QueryResponseDto;
import solengine.utils.dto.TripleDto;

public class SmartRunner {
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static void main(String[] args) {
//		doIt();
		doIt2();
	}
	


	private static void doIt() {
		for(List<String> result : getInput()) {
			List<QueryResponse> responses = system.findResponse(result, datasetAddresses);
			for(QueryResponse response : responses) {
				if(response.isValid()) {
					NewNewStorage.saveSingleResult(response);
					NewNewStorage.updateControlList("successX", response);
				}
				else {
					NewNewStorage.updateControlList("errorX", response);
				}
			}
		}
		
	}
	
	private static void doIt2() {
		List<String> savedList = NewNewStorage.readControlList("successX");
		for(String item : savedList) {
			QueryResponseDto response = NewNewStorage.readQResponse(item);
			System.out.println(response.result+": ");
			for(TripleDto triple : response.triples) {
				System.out.println("    s: "+triple.subject+"   p: "+triple.predicate+"   o: "+triple.object);
			}
		}
		
	}



	public static List<List<String>> getInput() {
		List<List<String>> baseResults = new ArrayList<List<String>>();
		
		List<String> aResult = new ArrayList<String>();
		aResult.add("http://dbpedia.org/resource/Miguasha_Group");
		baseResults.add(aResult);
		
		List<String> bResult = new ArrayList<String>();
		bResult.add("http://dbpedia.org/resource/The_Beatles");
		baseResults.add(bResult);
		
		List<String> cResult = new ArrayList<String>();
		cResult.add("http://dbpedia.org/resource/The_Police");
		baseResults.add(cResult);
		
		List<String> dResult = new ArrayList<String>();
		dResult.add("http://dbpedia.org/resource/The_Rolling_Stones");
		baseResults.add(dResult);
		
		return baseResults;

	}

}
