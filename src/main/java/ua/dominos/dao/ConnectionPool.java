package ua.dominos.dao;

import java.sql.Connection;

/**
 * Connection pool.
 */
public class ConnectionPool {
	private static final ThreadLocal<Connection> threadLocalScope = new ThreadLocal<>();

	/**
	 * Set current connection to thread local.
	 * 
	 * @param connection
	 *            specified connection.
	 */
	public static void setConnection(Connection connection) {
		threadLocalScope.set(connection);
	}

	/**
	 * Get connection from current thread.
	 * 
	 * @return connection.
	 */
	public static Connection getConnection() {
		return threadLocalScope.get();
	}

	/**
	 * Remove connection from current thread.
	 */
	public static void removeConnection() {
		threadLocalScope.remove();
	}
}
