package com.wawa87.webapp.listeners;

import com.wawa87.webapp.helpers.AppFileReader;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class AppServletContextListener implements ServletContextListener {
    JdbcConnectionPool connectionPool;
    Connection connection;
    Logger logger;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.initializeLogger();
        this.logger.info("Servlet Context Initialized...");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("logger", this.logger);

        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            DataSource jdbcDataSource = (DataSource) context.lookup("jdbc/appDb");
            this.connection = jdbcDataSource.getConnection();

            URL resource = sce.getServletContext().getResource("/WEB-INF/classes/dbSchema/schema.sql");
            String sqlSchema = AppFileReader.readFromInputStream(resource.openStream());
            this.connection.prepareCall(sqlSchema).execute();
            this.connection.close();
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.logger.info("Servlet Context Destroyed...");
        try {
            connection.close();
            connectionPool.dispose();
        } catch (SQLException e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    private void initializeLogger() {
        this.logger = LoggerFactory.getLogger("AppLogger");
        this.logger.info("LOGGER INITIALIZED");
    }
}