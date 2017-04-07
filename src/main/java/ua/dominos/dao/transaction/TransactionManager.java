package ua.dominos.dao.transaction;

import org.apache.log4j.Logger;
import ua.dominos.dao.ConnectionPool;
import ua.dominos.dao.DbConnection;
import ua.dominos.exception.DominoesServiceException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction manager.
 */
public class TransactionManager {
	private static final Logger LOG = Logger.getLogger(TransactionManager.class);

	/**
	 * Execute transaction.
	 * 
	 * @param tr
	 *            specified transaction.
	 * @return result of transaction.
	 * @throws DominoesServiceException
	 */
	public <T> T execute(Transaction<T> tr) throws DominoesServiceException {
		Connection connection = DbConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			ConnectionPool.setConnection(connection);
			T result = tr.execute();
			connection.commit();
			return result;
		} catch (Exception ex) {
			tryToRollback(connection);
			throw new DominoesServiceException(ex.getMessage());
		} finally {
			tryToClose(connection);
			ConnectionPool.removeConnection();
		}

	}

	/**
	 * Execute action without transaction.
	 * 
	 * @param tr
	 *            specified action.
	 * @return result of action.
	 * @throws DominoesServiceException
	 */
	public <T> T executeWithoutTransaction(Transaction<T> tr) throws DominoesServiceException {
		Connection connection = DbConnection.getConnection();
		try {
			ConnectionPool.setConnection(connection);
			T result = tr.execute();
			return result;
		} catch (Exception ex) {
			throw new DominoesServiceException(ex.getMessage());
		} finally {
			tryToClose(connection);
			ConnectionPool.removeConnection();
		}
	}

	/**
	 * Try roll back when transaction was interrupted.
	 * 
	 * @param connection
	 *            specified connection.
	 */
	private void tryToRollback(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				LOG.warn("Rollback was not executed", ex);
			}
		}
	}

	/**
	 * Try to close current connection.
	 * 
	 * @param connection
	 *            specified connection.
	 */
	private void tryToClose(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				LOG.warn("Connection was not closed", ex);
			}
		}
	}
}
