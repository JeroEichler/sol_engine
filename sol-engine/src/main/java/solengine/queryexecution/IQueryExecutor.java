package solengine.queryexecution;

import java.util.concurrent.Callable;

import solengine.model.QueryResponse;

/* ***************************************************************************************************************
 * Interface that establishes the fundamental behaviour of a QueryExecutor.
 * 
 * A QueryExecutor must basically implement a call() function that returns QueryResponse object.
 *****************************************************************************************************************/
public interface IQueryExecutor extends Callable<QueryResponse>{
	

}
