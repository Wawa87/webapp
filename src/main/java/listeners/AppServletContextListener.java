package listeners;

import helpers.AppFileReader;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        sce.getServletContext().setAttribute("logger", this.logger);

        try {
            this.connectionPool = JdbcConnectionPool.create("jdbc:h2:../webapps/webapp/WEB-INF/test_db", "sa", "sa");
            this.connection = this.connectionPool.getConnection();
            sce.getServletContext().setAttribute("dbConnection", this.connection);

            URL resource = sce.getServletContext().getResource("/WEB-INF/classes/dbSchema/schema.sql");
            String sqlSchema = AppFileReader.readFromInputStream(resource.openStream());
            this.connection.prepareCall(sqlSchema).execute();
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