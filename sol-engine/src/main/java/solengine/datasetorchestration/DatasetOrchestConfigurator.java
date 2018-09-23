package solengine.datasetorchestration;

import java.util.ArrayList;
import java.util.List;

import solengine.querybuilder.QueryBuilderTypeEnum;
import solengine.queryexecution.QueryExecutorTypeEnum;
import solengine.utils.Config;
import solengine.utils.Vocabulary;

public class DatasetOrchestConfigurator {
	
	public static List<QueryExecutorTypeEnum> buildQueryExecutorList(String dataset) {
		List<QueryExecutorTypeEnum> types = new ArrayList<QueryExecutorTypeEnum>();
		
		List<String> groups = Config.loadQueryExecutorGroups();
		
		// IF DBpedia
		if(dataset == Vocabulary.DBpediaEndpoint) {
			if(groups.contains("Analogy")) {			
								
//				if(domains.contains("Music")) {
					types.add(QueryExecutorTypeEnum.InfluenceAnalogy);
//				}
//				if(domains.contains("Tourism")) {
//					types.add(QueryExecutorTypeEnum.ArtworkAnalogy);
//					types.add(QueryExecutorTypeEnum.CollectionAnalogy);
//					types.add(QueryExecutorTypeEnum.VisitorAnalogy);
//				}
			}
			if(groups.contains("Surprise")) {	
				//if(domains.contains("Music")) {
					types.add(QueryExecutorTypeEnum.AssociationSurprisingObservation);
//					}
//					if(domains.contains("Tourism")) {
//						types.add(QueryExecutorTypeEnum.BuildingSurprisingObservationQE);
//					}
//				}
			}
		}
		
		//IF ANY Dataset
		if(groups.contains("Surprise")) {	
			types.add(QueryExecutorTypeEnum.SeeAlsoSurprisingObservation);
			types.add(QueryExecutorTypeEnum.SameAsSurprisingObservation);	
		}		
		if(groups.contains("Analogy")) {			
			types.add(QueryExecutorTypeEnum.HierarchieAnalogy);
//			types.add(QueryExecutorTypeEnum.HierarchieAnalogy_2);
//			types.add(QueryExecutorTypeEnum.HierarchieAnalogy_3);		
		}
		

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
