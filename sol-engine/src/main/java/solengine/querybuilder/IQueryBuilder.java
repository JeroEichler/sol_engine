package solengine.querybuilder;

import java.util.List;
import java.util.concurrent.Callable;

/* ***************************************************************************************************************
 * Interface that establishes the fundamental behaviour of QueryBuilder.
 * 
 * A QueryBuilder must basically implement:
 * 		(1) a call() function that returns String object, representing a query string.
 * 		(2) a buildNewQuery method that encompasses the logic of writing a query from a set of resources.
 *****************************************************************************************************************/
public interface IQueryBuilder extends Callable<String>{

	public String buildNewQuery(List<String> resources);
}
