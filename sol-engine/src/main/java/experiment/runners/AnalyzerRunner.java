package experiment.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.datasetorchestration.ResultAnalysisOrchestrator;
import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.utils.StringFormatter;
import solengine.zz.storage.NewNewStorage;
import solengine.zz.storage.RealStorage;

public class AnalyzerRunner {
	
	public static String baseFolder = "analysis//" + RealRunner.baseFolder;
		
	static int progress=0;

	public static void main(String[] args) {
		
		System.out.println("Here goes " + RealRunner.baseProject);
		
		long start = System.currentTimeMillis();
		
//		stepZero();
//		stepOne();
//		stepOne_B();	
		realFinalStep();
//		finalStep();

		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

	}
	
	public static void stepZero() {
		List<String> list = RealStorage.readList(RealRunner.baseFolder, "__successX");
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(RealRunner.baseFolder, title);
			if(qr.getObjects().size() > 0) {
				responses.add(qr);
			}
		}
		System.out.println(responses.size() + "!");
	}
	
	public static void stepOne() {		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
		List<String> list = RealStorage.readList(baseFolder, "__base");
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(RealRunner.baseFolder, title);
//			System.out.println(qr.getObjects());
			responses.add(qr);
		}
		List<AnalyzedQueryResponse> analysis = analyser.analyzeList(responses);
		
		for(AnalyzedQueryResponse item : analysis) {
//			System.out.println(title.unexpectednessScore);
			printProgress();
			persistAnalysis(item);	
		}
	}
	
	public static void stepOne_B() {		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
		List<String> list = RealStorage.readList(baseFolder, "__base");
		
		for(String title : list) {
			System.out.println(title);
			QueryResponse qr = NewNewStorage.readQResponse(RealRunner.baseFolder, title);
			AnalyzedQueryResponse analyzed = analyser.analyzeSingle(qr);
			persistAnalysis(analyzed);				
		}
	}
	
	private static void persistAnalysis(AnalyzedQueryResponse analyzed) {
		String title = StringFormatter.clean(analyzed.queryResponse.getResult());
		if(analyzed.valid) {
			NewNewStorage.saveSingleAnalysis(baseFolder, analyzed);
			NewNewStorage.updateList(baseFolder, "__successX", title);
			NewNewStorage.reduceList(baseFolder, "__base", title);
		}
		else {
			NewNewStorage.updateList(baseFolder, "__errorX", title);
			System.out.println("danger, danger.");
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
	

	public static void realFinalStep() {

		for(int i=0; i<7; i++) {
			System.out.println("Here goes " + RealRunner.names[i]);
			baseFolder = "analysis//" + RealRunner.queryOn + "//" + RealRunner.modeOn +"//" +  RealRunner.names[i];
			finalStep();
			System.out.println("-------------------------------------");
		}

	}


	
	private static void printProgress() {
		if(progress % 1000 == 0) {
			System.out.println("passed by "+progress);
		}
		progress++;
	}
}
