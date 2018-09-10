package experiment.runners;

import solengine.utils.Config;
import solengine.utils.Vocabulary;

public class BasicRunner {

	public static void main(String[] args) {

		System.out.println("Guten Morgen!");
		
		for(String item : Config.loadQueryExecutors(Vocabulary.DBpediaEndpoint)){
			System.out.println(item);
		}

	}

}
