package solengine.querybuilder;

import solengine.querybuilder.generic.WrongHypothesisQueryBuilder;

public class QueryBuilderFactory {
	/* ***************************************************************************************************************
	 * Function that encapsulates the creation of a IQueryBuilder object based on its type parameter.
	 * 
	 * Parameters:	(1) String type; //type of the desired IQueryBuilder
	 * 				(2) String endpoint;  //dataset endpoint address
	 * 				(3) String subject  //subject parameter of the query.
	 * Returns: sol_tool_1_4.QueryProcessing.IQueryProcessor.
	 * Obs.: For log and debug only.
	 *****************************************************************************************************************/
	public static IQueryBuilder createQueryTransformer(QueryBuilderTypeEnum type, String endpoint, String subquery){
		if(type.equals(QueryBuilderTypeEnum.WrongHypothesis)){
			return new WrongHypothesisQueryBuilder(endpoint, subquery);
		}
		return null;
	}
}
