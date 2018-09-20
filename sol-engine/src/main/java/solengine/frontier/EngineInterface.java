package solengine.frontier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import solengine.datasetorchestration.DatasetOrchestrator;
import solengine.model.QueryAnalyser;
import solengine.model.QueryResponse;
import solengine.utils.Vocabulary;

public class EngineInterface {
	
	DatasetOrchestrator dataset;
	List<DatasetOrchestrator> datasets;
	
	public List<String> availableDatasetAddresses(){
		List<String> datasetAddresses = new ArrayList<>();
		datasetAddresses.add(Vocabulary.DBpediaEndpoint);
		//datasetAddresses.add(Vocabulary.linkedMDBEndpoint);
		return datasetAddresses;
	}

	//TODO aggregating multiple dataset
	//today, it is returning only the first
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
				String newQuery = datasetOrchestrator.generateQuery(query);
				results.add(newQuery);
			}
			return results;
		}
		else {
			return new ArrayList<String>();
		}
	}

	private List<DatasetOrchestrator> initDatasetOrchestrator(List<String> addresses){
		List<DatasetOrchestrator> datasets = new ArrayList<>();
		for(String datasetAddress : addresses){
			datasets.add(new DatasetOrchestrator(datasetAddress));
		}
		return datasets;
	}

}
