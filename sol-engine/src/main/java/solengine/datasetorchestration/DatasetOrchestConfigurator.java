package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;

import solengine.configuration.Config;
import solengine.configuration.QESystemConfiguration;
import solengine.model.Vocabulary;
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
	
	public static List<QueryExecutorTypeEnum> OLD_buildQueryExecutorList(String dataset) {
		List<QueryExecutorTypeEnum> types = new ArrayList<QueryExecutorTypeEnum>();
		
		List<String> groups = Config.loadQueryExecutorGroups();
		
		// IF DBpedia
		if(dataset == Vocabulary.DBpediaEndpoint) {
			if(groups.contains("Analogy")) {			
								
//				if(domains.contains("Music")) {
//					types.add(QueryExecutorTypeEnum.InfluenceAnalogy);
//				}
//				if(domains.contains("Tourism")) {
//					types.add(QueryExecutorTypeEnum.ArtworkAnalogy);
//					types.add(QueryExecutorTypeEnum.CollectionAnalogy);
//					types.add(QueryExecutorTypeEnum.VisitorAnalogy);
//				}
			}
			if(groups.contains("Surprise")) {	
				//if(domains.contains("Music")) {
//					types.add(QueryExecutorTypeEnum.AssociationSurprisingObservation);
//					}
//					if(domains.contains("Tourism")) {
//						types.add(QueryExecutorTypeEnum.BuildingSurprisingObservationQE);
//					}
//				}
			}
		}
		
//		//IF ANY Dataset
//		if(groups.contains("Surprise")) {	
			types.add(QueryExecutorTypeEnum.SeeAlsoSurprisingObservation);
//			types.add(QueryExecutorTypeEnum.SameAsSurprisingObservation);	
//		}		
//		if(groups.contains("Analogy")) {			
//			types.add(QueryExecutorTypeEnum.HierarchieAnalogy);
////			types.add(QueryExecutorTypeEnum.HierarchieAnalogy_2);
////			types.add(QueryExecutorTypeEnum.HierarchieAnalogy_3);		
//		}
//		
//
//		types.add(QueryExecutorTypeEnum.DifferenceInversion);
		

		//not serendipitously
//		types.add(QueryExecutorTypeEnum.BasicQueryProcessor);
//		types.add(QueryExecutorTypeEnum.SimpleBasicQueryProcessor);
//		types.add(QueryExecutorTypeEnum.SimilarityQueryProcessor);
		
		return types;
	}
	
	public static List<QueryBuilderTypeEnum> buildQueryBuilderList(String dataset) {
		List<QueryBuilderTypeEnum> types = new ArrayList<QueryBuilderTypeEnum>();		
		types.add(QueryBuilderTypeEnum.WrongHypothesis);		
		return types;
	}

}
