package solengine.datasetorchestration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import solengine.model.QueryResult;
import solengine.queryexecution.UserQueryExecutor;
import solengine.utils.Vocabulary;

public class DatasetOrchestrator {
	
	Map<List<String>,QueryResult>  queryResults;
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;

	public DatasetOrchestrator(String address) {
		this.datasetEndpoint = address;
		//this.usrprocessor = new UserQueryProcessor();
		this.queryResults = new ConcurrentHashMap<List<String>,QueryResult>();
	}
	
	public Map<List<String>, QueryResult> processQuery(String queryString){
		List<List<String>> originalResults = usrQueryExecutor.queryEndpoint(datasetEndpoint, queryString);
		
		List<String> processorNames = this.listOfQueryProcessors();
		
		for(String sprocessor : processorNames){
			this.doStuff(sprocessor, originalResults);
		}
		
		return this.queryResults;
		
	}

	private void doStuff(String sprocessor, List<List<String>> originalResults) {
		// TODO Auto-generated method stub
		
	}

	private List<String> listOfQueryProcessors() {
		// TODO Auto-generated method stub
		return null;
	}

}
