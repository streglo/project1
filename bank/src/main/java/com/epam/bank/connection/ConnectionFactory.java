package com.epam.bank.connection;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Future;

public class ConnectionFactory {

	static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class);

	private static volatile ConnectionFactory instance;

	private DataSource dataSource;

	private ConnectionFactory() { }

	private ConnectionFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void initialization(DataSource dataSource) {
		ConnectionFactory localInstance = instance;
		if (localInstance == null) {
			synchronized (ConnectionFactory.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new ConnectionFactory(dataSource);
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		ConnectionFactory localInstance = instance;
		if (localInstance == null) {
			throw new SQLException("Not initialization connection factory");
		}

		return instance.dataSource.getConnection();
	}

}
