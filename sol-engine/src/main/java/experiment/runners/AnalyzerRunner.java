package experiment.runners;

import java.util.List;

import solengine.model.QueryResponse;
import solengine.utils.NewNewStorage;
import solengine.utils.RealStorage;

public class AnalyzerRunner {

	public static void main(String[] args) {
		stepOne();

	}
	
	public static void stepOne() {
		List<String> list = RealStorage.readList("__successX");
		
		for(String title : list) {
			QueryResponse qr = NewNewStorage.readQResponse(title);
			System.out.println(qr.getObjects());
		}

	}

}
