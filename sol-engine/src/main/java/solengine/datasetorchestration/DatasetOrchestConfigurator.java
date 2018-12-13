package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;

import solengine.configuration.Config;
import solengine.configuration.QESystemConfiguration;
import solengine.querybuilder.QueryBuilderTypeEnum;
import solengine.queryexecution.QueryExecutorTypeEnum;

public class DatasetOrchestConfigurator {
	
	public static List<QueryExecutorTypeEnum> buildQueryExecutorList(String dataset) {
		List<QueryExecutorTypeEnum> types = new ArrayList<QueryExecutorTypeEnum>();

		QESystemConfiguration qeConfig = Config.qeConfiguration;
		
		// IF DBpedia
//		if(dataset == Vocabulary.DBpediaEndpoint) {
			if(qeConfig.InfluenceAnalogy) {
				types.add(QueryExecutorTypeEnum.InfluenceAnalogy);
			}
			if(qeConfig.AssociationSurprisingObservation) {
				types.add(QueryExecutorTypeEnum.AssociationSurprisingObservation);
			}
//		}
		
//		//IF ANY Dataset
		if(qeConfig.SeeAlsoSurprisingObservation) {	
			types.add(QueryExecutorTypeEnum.SeeAlsoSurprisingObservation);	
		}		
		if(qeConfig.SameAsSurprisingObservation) {	
			types.add(QueryExecutorTypeEnum.SameAsSurprisingObservation);	
		}	
		if(qeConfig.HierarchieAnalogy) {			
			types.add(QueryExecutorTypeEnum.HierarchieAnalogy);	
		}
		if(qeConfig.DifferenceInversion) {			
			types.add(QueryExecutorTypeEnum.DifferenceInversion);
		}
		
		return types;
	}
	
	public static List<QueryBuilderTypeEnum> buildQueryBuilderList(String dataset) {
		List<QueryBuilderTypeEnum> types = new ArrayList<QueryBuilderTypeEnum>();		
		types.add(QueryBuilderTypeEnum.WrongHypothesis);		
		return types;
	}

}
