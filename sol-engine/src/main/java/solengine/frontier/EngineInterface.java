package solengine.frontier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import solengine.datasetorchestration.DatasetOrchestrator;
import solengine.model.QueryAnalyser;
import solengine.model.QueryResult;
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

	public Map<List<String>, QueryResult>  processOnDatasets(String query, List<String> addresses, int step) {
		if(QueryAnalyser.isQueryValid(query)) {
			query = QueryAnalyser.limitQuery(query, step);
			this.datasets = this.initDatasetOrchestrator(addresses);

			for(DatasetOrchestrator datasetOrchestrator : datasets){
				Map<List<String>, QueryResult> tableResults = datasetOrchestrator.processQuery(query);
				return tableResults;
			}
		}
		else {
			return new ConcurrentHashMap<List<String>,QueryResult>();
		}
		return null;
	}

	private List<DatasetOrchestrator> initDatasetOrchestrator(List<String> addresses){
		List<DatasetOrchestrator> datasets = new ArrayList<>();
		for(String datasetAddress : addresses){
			datasets.add(new DatasetOrchestrator(datasetAddress));
		}
		return datasets;
	}

}
