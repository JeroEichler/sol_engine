package solengine.querybuilder;

import java.util.concurrent.Callable;

/* ***************************************************************************************************************
 * Interface that establishes the fundamental behaviour of QueryBuilder
 * 
 * A QueryBuilder must basically implement a call() function that returns String object, representing a query
 * string.
 *****************************************************************************************************************/
public interface IQueryBuilder extends Callable<String>{

}
