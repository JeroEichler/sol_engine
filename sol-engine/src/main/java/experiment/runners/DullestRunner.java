package experiment.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import solengine.zz.storage.NewStorage;

public class DullestRunner {

	public static void main(String[] args) {
		doIt2();

	}

	private static void doIt() {
		List<String> list = new ArrayList<String>();
		
		for(int i=0; i<=100000; i++) {
			list.add("affgdtyttdhjkbcgjjbthjkvftxghgrchkb---"+i);
		}
		NewStorage.saveEntity("nadas", list);
	}
	
	private static void doIt2() {
		List<String> list = new ArrayList<String>();
		list.add("httjps://affgdtyttdhjkbcgjjbthjkvftxghgrchkb---");

		//list.add("http://jero---");
		
		for(int i=0; i<=100000; i++) {
			//System.out.println(i+"------------------------");
			String s = getRandomResource(list);
			if(!s.equals("http://affgdtyttdhjkbcgjjbthjkvftxghgrchkb---")) {
				System.out.println(s);
			}
		}
		System.out.println("gone");
	}
	
	protected static String getRandomResource(List<String> terms){
		List<String> objectTyped = new ArrayList<String>();
		for(String term : terms){
			if(term.contains("http://") || term.contains("https://")){
				objectTyped.add(term);
			}
		}
		if(objectTyped.size() > 0) {
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(objectTyped.size());
			return objectTyped.get(randomInt);
		}
		else {
			return "No URIs were provided.";
		}
	}

}
