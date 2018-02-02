package com.epam.bank.configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.epam.bank.connection.ConnectionFactory;
import org.apache.catalina.UserDatabase;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BankApplicationContextListener implements ServletContextListener
{
	/**
	 * The name of the JNDI lookup name
	 */
	private static final String LOG4J_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/log4j/configuration-resource";
	private static final String BANK_DB_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/jdbc/bankDB";
	private static final String USER_DB_JNDI_CONFIGURATION_LOOKUP_NAME = "java:comp/env/users";

	static final Logger LOGGER = Logger.getLogger(BankApplicationContextListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		InitialContext initialContext = null;
		try
		{
			initialContext = new InitialContext();

			initLog4j(initialContext);

			DataSource dataSource = getDataSource(initialContext);
			initConnectionFactory(dataSource);

			UserDatabase userDatabase = (UserDatabase) initialContext.lookup(USER_DB_JNDI_CONFIGURATION_LOOKUP_NAME);

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
		DataSource result = (DataSource) initialContext.lookup(BANK_DB_JNDI_CONFIGURATION_LOOKUP_NAME);
		return result;
	}

	private void initConnectionFactory(DataSource dataSource) throws NamingException {
		ConnectionFactory.initialization(dataSource);
	}

}
