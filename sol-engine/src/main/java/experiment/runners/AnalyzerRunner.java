package experiment.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.resultanalysis.ResultAnalysisOrchestrator;
import solengine.utils.NewNewStorage;
import solengine.utils.RealStorage;

public class AnalyzerRunner {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		stepOne();
//		stepTwo();		

		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

	}
	
	public static void stepOne() {
		List<String> list = RealStorage.readList("genSeeAlsoSO", "__successX");
//		List<String> list = readList("__successX");
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(title);
//			System.out.println(qr.getObjects());
			responses.add(qr);
		}
		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator();
		List<AnalyzedQueryResponse> analysis = analyser.analyzeList(responses);
		
		for(AnalyzedQueryResponse title : analysis) {
			System.out.println(title.unexpectednessScore);
		}

	}
	
	public static void stepTwo() {
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator();
		
		List<String> list = RealStorage.readList("genSeeAlsoSO", "__successX");
//		List<String> list = readList("__successX");
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(title);
			if(qr.getObjects().size() > 0) {
//				System.out.println(qr.getObjects());
				AnalyzedQueryResponse analyzed = analyser.analyzeSingle(qr);
				System.out.println(analyzed.unexpectednessScore);
			}
		}

	}

	private static List<String> readList(String string) {
		List<String> list = new ArrayList<String>();
		list.add("Alternating_group");
		return list;
	}

}
