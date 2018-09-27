package solengine.utils;

/************************************************************************************************************
 * Class that localize external references of the application e.g. messages, datasets, URL and 
 * resources' URIs.
 *
 * 
 * **********************************************************************************************************/
public class Vocabulary {	

	/************************************************************************************************************
	 * Configuration properties 
	 * **********************************************************************************************************/
	public final static int searchResultsSize = 10;

	public static final String EmptyString = "";

	public static final String ErrorMessage = "ERROR: cause by network problems.";
	

	/************************************************************************************************************
	 * Datasets's sparql endpoints 
	 * **********************************************************************************************************/
	public final static String DBpediaEndpoint = "http://dbpedia.org/sparql";
	
	public final static String linkedMDBEndpoint = "http://www.linkedmdb.org/sparql";
	
	
	
	/************************************************************************************************************
	 * Properties's URIs 
	 * **********************************************************************************************************/
	
	/************************************************************************************************************
	 * RDF, RDFS & OWL Properties's URIs 
	 * **********************************************************************************************************/

	public final static String Rdfs_LabelProperty = "http://www.w3.org/2000/01/rdf-schema#label";
	
	public final static String Owl_SameAsProperty = "http://www.w3.org/2002/07/owl#sameAs";
	
	public final static String Rdfs_SeeAlsoProperty = "http://www.w3.org/2000/01/rdf-schema#seeAlso";

	public final static String Rdfs_SubclassOfProperty = "http://www.w3.org/2000/01/rdf-schema#subClassOf";

	public final static String Rdf_TypeProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	
	/************************************************************************************************************
	 * Dublin Core Properties's URIs 
	 * **********************************************************************************************************/

	public final static String DC_DescriptionProperty = "http://purl.org/dc/terms/description";	
	
	public final static String DC_SubjectProperty = "http://purl.org/dc/terms/subject";
	
	/************************************************************************************************************
	 * SKOS Properties's URIs 
	 * **********************************************************************************************************/

	public final static String Skos_BroaderProperty = "http://www.w3.org/2004/02/skos/core#broader";
	
	/************************************************************************************************************
	 * DBpedia Properties's URIs 
	 * **********************************************************************************************************/

	public final static String DB_AssociatedBandProperty = "http://dbpedia.org/ontology/associatedBand";

	public final static String DB_CollectionProperty = "http://dbpedia.org/property/collection";

	public final static String DB_InfluencedProperty = "http://dbpedia.org/ontology/influenced";

	public final static String DB_LocationProperty = "http://dbpedia.org/property/location";
	
	public final static String DB_SignificantBuildingProperty = "http://dbpedia.org/ontology/significantBuilding";

	public final static String DB_VisitorsProperty = "http://dbpedia.org/property/visitors";
	
	/************************************************************************************************************
	 * SOL-Tool Properties's URIs 
	 * **********************************************************************************************************/

	public final static String Stool_AnalogyProperty = "http://soltool.com/analogousTo";
	
	public final static String Stool_SimilarityProperty = "http://soltool.com/similarTo";

}
