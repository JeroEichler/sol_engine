package solengine.queryexecution;


/* ***************************************************************************************************************
 * Enumerator of all possible QueryExecutor types.
 * 
 *****************************************************************************************************************/
public enum QueryExecutorTypeEnum {
	
	// generic
	SeeAlsoSurprisingObservation,
	SameAsSurprisingObservation,
	HierarchieAnalogy,
	HierarchieAnalogy_2,
	HierarchieAnalogy_3,
	
	// music
	InfluenceAnalogy,	
	AssociationSurprisingObservation,
	
	// tourism
	BuildingSurprisingObservationQE,
	ArtworkAnalogy,
	CollectionAnalogy,
	VisitorAnalogy,
	
	// not used because are not serendipity
	BasicQueryProcessor,
	SimilarityQueryProcessor,
	SimpleBasicQueryProcessor

}
