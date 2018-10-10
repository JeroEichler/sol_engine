package experiment.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.resultanalysis.ResultAnalysisOrchestrator;
import solengine.utils.NewNewStorage;
import solengine.utils.RealStorage;

public class AnalyzerRunner {
	
	public static String baseFolder = "analysis//full//genSeeAlsoSO";

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
//		stepOne();
//		stepTwo();	
		finalStep();

		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

	}
	
	public static void stepOne() {
		List<String> list = RealStorage.readList(RealRunner.baseFolder, "__successX");
		List<String> saved = new ArrayList<String>();
		List<String> errors = new ArrayList<String>();
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(RealRunner.baseFolder, title);
//			System.out.println(qr.getObjects());
			responses.add(qr);
		}
		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator();
		List<AnalyzedQueryResponse> analysis = analyser.analyzeList(responses);
		
		for(AnalyzedQueryResponse item : analysis) {
//			System.out.println(title.unexpectednessScore);
			if(item.valid) {
				String title = NewNewStorage.saveSingleAnalysis(baseFolder, item);
				saved.add(title);
			}
			else {
				String title = NewNewStorage.saveSingleAnalysis(baseFolder, item);
				errors.add(title);
				System.out.println("danger, danger.");
			}
		}
		
		if(saved.size() > 0) {
			NewNewStorage.saveEntity(baseFolder, "__successX", saved);
		}
		if(errors.size() > 0) {
			NewNewStorage.saveEntity(baseFolder, "__errorX", errors);
		}
		
		

	}
	
	public static void stepTwo() {
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator();
		
		List<String> list = RealStorage.readList(RealRunner.baseFolder, "__successX");
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(RealRunner.baseFolder, title);
			if(qr.getObjects().size() > 0) {
//				System.out.println(qr.getObjects());
				AnalyzedQueryResponse analyzed = analyser.analyzeSingle(qr);
				System.out.println(analyzed.unexpectednessScore);
			}
		}

	}
	
	public static void finalStep() {
		double sum = 0, counter = 0;
		List<String> list = RealStorage.readList(baseFolder, "__successX");
		
		for(String title : list) {
			AnalyzedQueryResponse aqr = NewNewStorage.readAnalysis(baseFolder, title);
			if(!aqr.emptyResponse) {
				sum = sum + aqr.unexpectednessScore;
				counter++;
			}
		}
		
		double average = 0;
		if(counter != 0) {
			average = sum / counter;
		}

		System.out.println("unexpectedness equal to : " + average);
		System.out.println("in a total of : " + counter);

	}

}
