package solengine.datasetorchestration;

import java.util.List;
import java.util.Map;

import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.queryexecution.QueryExecutorTypeEnum;
import solengine.queryexecution.UserQueryExecutor;

public class DatasetOrchestrator {
	
	String datasetEndpoint = Vocabulary.EmptyString;
	
	UserQueryExecutor usrQueryExecutor;
	
	QEOrchestrator2ndStrategy qExecutorOrchestrator;
	
	QEOrchestrator optimQExecutorOrchestrator;
	
	QBOrchestrator qbuilderOrchestrator;

	public DatasetOrchestrator(String address) {
		this.datasetEndpoint = address;
		this.usrQueryExecutor = new UserQueryExecutor();
		
		this.qExecutorOrchestrator = new QEOrchestrator2ndStrategy(address);
		this.optimQExecutorOrchestrator = new QEOrchestrator(address);
		this.qbuilderOrchestrator = new QBOrchestrator(address);
	}
	
	public Map<List<String>, QueryResponse> processQuery(String queryString){	
		List<QueryExecutorTypeEnum> qExecutorNames = DatasetOrchestConfigurator.buildQueryExecutorList(datasetEndpoint);
		if(qExecutorNames.size() == 1) {
			return this.optimQExecutorOrchestrator.processQuery(queryString);
		}
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
	
	public Map<List<String>, QueryResponse> getResponses(List<List<String>> baseResult){		
		return this.optimQExecutorOrchestrator.findResponses(baseResult);		
	}

}
