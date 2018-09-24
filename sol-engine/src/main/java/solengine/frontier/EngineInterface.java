package solengine.frontier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import solengine.datasetorchestration.DatasetOrchestrator;
import solengine.model.QueryAnalyser;
import solengine.model.QueryResponse;
import solengine.utils.Vocabulary;

/* ***************************************************************************************************************
 * Class that acts as interface for the rest of the application.
 * 
 *****************************************************************************************************************/
public class EngineInterface {
	
	List<DatasetOrchestrator> datasets;
	
	/* ***************************************************************************************************************
	* Method that provides a list of dataset endpoints that the application may work with.
	* 
	*****************************************************************************************************************/
	public List<String> availableDatasetAddresses(){
		List<String> datasetAddresses = new ArrayList<>();
		datasetAddresses.add(Vocabulary.DBpediaEndpoint);
		datasetAddresses.add(Vocabulary.linkedMDBEndpoint);
		return datasetAddresses;
	}

	//TODO aggregating multiple dataset
	//today, it is returning only the first
	/* ***************************************************************************************************************
	* Method that provides a HashMap of QueryResponses indexed by the  result string.
	* 
	*****************************************************************************************************************/
	public Map<List<String>, QueryResponse> processOnDatasets(String query, List<String> addresses, int limit, int step) {
		query = QueryAnalyser.limitQueryWithOffset(query, limit, step);
		if(QueryAnalyser.isQueryValid(query)) {
			this.datasets = this.initDatasetOrchestrator(addresses);

			for(DatasetOrchestrator datasetOrchestrator : datasets){
				Map<List<String>, QueryResponse> tableResults = datasetOrchestrator.processQuery(query);
				return tableResults;
			}
		}
		else {
			return new ConcurrentHashMap<List<String>,QueryResponse>();
		}
		return null;
	}
	
	public List<String> createQueries(String query, List<String> addresses) {
		if(QueryAnalyser.isQueryValid(query)) {
			List<String> results = new ArrayList<String>();
			
			this.datasets = this.initDatasetOrchestrator(addresses);

			for(DatasetOrchestrator datasetOrchestrator : datasets){
				List<String> newQueries = datasetOrchestrator.generateQuery(query);
				results.addAll(newQueries);
			}
			return results;
		}
		else {
			return new ArrayList<String>();
		}
	}

	/* ***************************************************************************************************************
	* Method that initiate a list of DatasetOrchestrators.
	* 
	*****************************************************************************************************************/
	private List<DatasetOrchestrator> initDatasetOrchestrator(List<String> addresses){
		List<DatasetOrchestrator> datasets = new ArrayList<>();
		for(String datasetAddress : addresses){
			datasets.add(new DatasetOrchestrator(datasetAddress));
		}
		return datasets;
	}

}
