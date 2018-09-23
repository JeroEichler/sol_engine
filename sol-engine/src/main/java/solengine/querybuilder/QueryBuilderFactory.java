package solengine.querybuilder;

import solengine.querybuilder.generic.WrongHypothesisQB;

public class QueryBuilderFactory {
	/* ***************************************************************************************************************
	 * Function that encapsulates the creation of a IQueryBuilder object based on its type parameter.
	 * 
	 * Parameters:	(1) QueryBuilderTypeEnum type; //type of the desired IQueryBuilder
	 * 				(2) String endpoint;  //dataset endpoint address
	 * 				(3) String subject  //subject parameter of the query.
	 * Returns: IQueryBuilder instance.
	 *****************************************************************************************************************/
	public static IQueryBuilder createQueryTransformer(QueryBuilderTypeEnum type, String endpoint, String subquery){
		if(type.equals(QueryBuilderTypeEnum.WrongHypothesis)){
			return new WrongHypothesisQB(endpoint, subquery);
		}
		return null;
	}
}
