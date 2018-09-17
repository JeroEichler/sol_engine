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
	 * Parameters:	(1) String type; //type of the desired IQueryQueryExecutor
	 * 				(2) String endpoint;  //dataset endpoint address
	 * 				(3) String subject  //subject parameter of the query.
	 * Returns: solengine.queryexecution.IQueryExecutor.
	 * Obs.: For log and debug only.
	 *****************************************************************************************************************/
	public static IQueryExecutor createQueryExecutor(String type, String endpoint, List<String> subject){
//		if(type.equals("AnalogyQueryProcessor")){
//			return new AnalogyQueryProcessor(endpoint, subject);
//		}
//		if(type.equals("AnalogyQueryProcessor2")){
//			return new AnalogyQueryProcessor2(endpoint, subject);
//		}
//		if(type.equals("AnalogyQueryProcessor4")){
//			return new AnalogyQueryProcessor4(endpoint, subject);
//		}
//		if(type.equals("SimilarityQueryProcessor")){
//			return new SimilarityQueryProcessor(endpoint, subject);
//		}
		if(type.equals("SeeAlsoSurprisingObservation")){
			return new SeeAlsoSurprisingObservationQE(endpoint, subject);
		}
		if(type.equals("SameAsSurprisingObservation")){
			return new SameAsSurprisingObservationQE(endpoint, subject);
		}
		

		if(type.equals("InfluenceAnalogy")){
			return new InfluenceAnalogyQE(endpoint, subject);
		}
		if(type.equals("AssociationSurprisingObservation")){
			return new AssociationSurprisingObservationQE(endpoint, subject);
		}
		
		
		if(type.equals("ArtworkAnalogy")){
			return new ArtworkAnalogyQE(endpoint, subject);
		}
		if(type.equals("CollectionAnalogy")){
			return new CollectionAnalogyQE(endpoint, subject);
		}
		if(type.equals("VisitorAnalogy")){
			return new VisitorAnalogyQE(endpoint, subject);
		}
		else if(type.equals("BuildingSurprisingObservation")){
			return new BuildingSurprisingObservationQE(endpoint, subject);
		}
		
//		if(type.equals("BasicQueryExecutor")){
//			return new BasicQueryExecutor(endpoint, subject);
//		}
//		if(type.equals("SimpleBasicQueryExecutor")){
//			return new SimpleBasicQueryExecutor(endpoint, subject);
//		}
		
		return null;
	}

}
