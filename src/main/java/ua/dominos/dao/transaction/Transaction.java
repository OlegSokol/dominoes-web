package ua.dominos.dao.transaction;

/**
 * Transaction.
 */
public interface Transaction<T> {
	
	/**
	 * Execute current action.
	 */
    T execute();
}