package com.epam.bank.configuration;

import com.epam.bank.connection.ConnectionFactory;
import com.epam.bank.dao.UserDao;
import org.apache.catalina.UserDatabase;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankApplicationContextListener implements ServletContextListener
{
	/**
	 * The name of the JNDI lookup name
	 */
	private static final String LOG4J_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/log4j/configuration-resource";
	private static final String BANK_DB_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/jdbc/bankDB";
	private static final String USER_DB_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/users";
	private static final String DB_SQL = "db.sql";

	private static final Pattern SQL_PATTERN = Pattern.compile("[^;]*");

	private static final Logger LOGGER = Logger.getLogger(BankApplicationContextListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try
		{
			InitialContext initialContext = new InitialContext();

			initLog4j(initialContext);

			DataSource dataSource = getDataSource(initialContext);
			initConnectionFactory(dataSource);

			intDatabase();

			initUserDao(initialContext);

			LOGGER.info("Start Bank APP");
		}
		catch (NamingException e)
		{
			LOGGER.error(e.getMessage());

			throw new RuntimeException(e);
		}

	}

	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		LOGGER.info("Stop Bank APP");
	}

	private void initLog4j(InitialContext initialContext) throws NamingException {
		String filename = (String) initialContext.lookup(LOG4J_JNDI_CONFIGURATION_LOOKUP_NAME);
		PropertyConfigurator.configure(filename);
	}

	private DataSource getDataSource(InitialContext initialContext) throws NamingException {
		return (DataSource) initialContext.lookup(BANK_DB_JNDI_CONFIGURATION_LOOKUP_NAME);
	}

	private void initConnectionFactory(DataSource dataSource) throws NamingException {
		ConnectionFactory.initialization(dataSource);
	}

	private void intDatabase() {
		File file = getSQLfile();
		try (Scanner scanner = new Scanner(file);
			 Connection connection = ConnectionFactory.getConnection()) {
			connection.setAutoCommit(false);

			String line = "";
			while (scanner.hasNextLine()) {
				line += scanner.nextLine().trim();

				if (line.endsWith(";")) {
					try (PreparedStatement statement = connection.prepareStatement(line)) {
						statement.execute();
						line = "";
					} catch (SQLException e) {
						LOGGER.error(e.getMessage());
						connection.rollback();

						throw new RuntimeException(e);
					}
				}
			}

			connection.commit();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());

			throw new RuntimeException(e);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());

			throw new RuntimeException(e);
		}
	}

	private File getSQLfile() {
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(DB_SQL).getFile());
	}

	private void initUserDao(InitialContext initialContext) throws NamingException {
		UserDatabase userDatabase = (UserDatabase) initialContext.lookup(USER_DB_JNDI_CONFIGURATION_LOOKUP_NAME);
		UserDao.initialization(userDatabase);
	}
}
