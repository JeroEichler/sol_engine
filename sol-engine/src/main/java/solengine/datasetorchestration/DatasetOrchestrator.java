package solengine.datasetorchestration;

import java.util.List;
import java.util.Map;

import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.queryexecution.UserQueryExecutor;

public class DatasetOrchestrator {
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;
	
	QEOrchestrator2ndStrategy qExecutorOrchestrator;
	
	QBOrchestrator qbuilderOrchestrator;

	public DatasetOrchestrator(String address) {
		this.datasetEndpoint = address;
		this.usrQueryExecutor = new UserQueryExecutor();
		
		this.qExecutorOrchestrator = new QEOrchestrator2ndStrategy(address);
		this.qbuilderOrchestrator = new QBOrchestrator(address);
	}
	
	public Map<List<String>, QueryResponse> processQuery(String queryString){		
		return this.qExecutorOrchestrator.processQuery(queryString);		
	}
	
	public List<List<String>> basicProcessOfQuery(String queryString){
		List<List<String>> results = this.usrQueryExecutor.queryEndpoint(datasetEndpoint, queryString);
		return results;		
	}

	public List<String> generateQuery(String query) {
		return this.qbuilderOrchestrator.generateQueries(query);
	}
	

	
	public QueryResponse getResponse(List<String> baseResult){		
		return this.qExecutorOrchestrator.getResponse(baseResult);		
	}

}
