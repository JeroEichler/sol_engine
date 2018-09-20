package solengine.queryexecution;

import java.util.List;

import solengine.queryexecution.generic.*;
import solengine.queryexecution.music.*;
import solengine.queryexecution.tourism.*;

/* ***************************************************************************************************************
 * Class that encapsulates the creation of a IQueryExecutor object.
 * 
 *****************************************************************************************************************/
public class QueryExecutorFactory {

	/* ***************************************************************************************************************
	 * Function that encapsulates the creation of a IQueryExecutor object based on its type parameter.
	 * 
	 * Parameters:	(1) Enum type; 			//type of the desired IQueryQueryExecutor
	 * 				(2) String endpoint;  	//dataset endpoint address
	 * 				(3) String subject  	//subject parameter of the query.
	 * 
	 * Returns: solengine.queryexecution.IQueryExecutor.
	 * 
	 *****************************************************************************************************************/
	public static IQueryExecutor createQueryExecutor(QueryExecutorTypeEnum type, String endpoint, List<String> subject){
		if(type.equals(QueryExecutorTypeEnum.HierarchieAnalogy)){
			return new HierarchieAnalogyQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.HierarchieAnalogy_2)){
			return new HierarchieAnalogyQE_Alt2(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.HierarchieAnalogy_3)){
			return new HierarchieAnalogyQE_Alt3(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.SimilarityQueryProcessor)){
			return new SimilarityQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.SeeAlsoSurprisingObservation)){
			return new SeeAlsoSurprisingObservationQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.SameAsSurprisingObservation)){
			return new SameAsSurprisingObservationQE(endpoint, subject);
		}
		

		if(type.equals(QueryExecutorTypeEnum.InfluenceAnalogy)){
			return new InfluenceAnalogyQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.AssociationSurprisingObservation)){
			return new AssociationSurprisingObservationQE(endpoint, subject);
		}
		
		
		if(type.equals(QueryExecutorTypeEnum.ArtworkAnalogy)){
			return new ArtworkAnalogyQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.CollectionAnalogy)){
			return new CollectionAnalogyQE(endpoint, subject);
		}
		if(type.equals(QueryExecutorTypeEnum.VisitorAnalogy)){
			return new VisitorAnalogyQE(endpoint, subject);
		}
		else if(type.equals(QueryExecutorTypeEnum.BuildingSurprisingObservationQE)){
			return new BuildingSurprisingObservationQE(endpoint, subject);
		}
		
//		if(type.equals(QueryExecutorTypeEnum.BasicQueryProcessor)){
//			return new BasicQueryExecutor(endpoint, subject);
//		}
//		if(type.equals(QueryExecutorTypeEnum.SimpleBasicQueryProcessor)){
//			return new SimpleBasicQueryExecutor(endpoint, subject);
//		}
		
		return null;
	}

}
