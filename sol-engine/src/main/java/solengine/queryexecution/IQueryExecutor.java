package solengine.queryexecution;

import java.util.concurrent.Callable;

import solengine.model.QueryResult;

/* ***************************************************************************************************************
 * Interface that establishes the fundamental behaviour of QueryExecutor
 * 
 * A QueryExecutor must basically implement a call() function that returns QueryResult object.
 *****************************************************************************************************************/
public interface IQueryExecutor extends Callable<QueryResult>{
	

}
