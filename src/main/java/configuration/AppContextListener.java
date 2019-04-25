package configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private final String databaseUrl = "jdbc:sqlite:neyanboon.db";

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.sqlite.JDBC");
            BasicConnectionPool.create(databaseUrl);
            System.out.println("Database connection initialized for Application.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Database connection closed for Application.");
    }


    private static final String createProject = "create table if not exists projects\n" +
            "(\n" +
            "  id text,\n" +
            "  title text,\n" +
            "  description text,\n" +
            "  imageUrl text,\n" +
            "  budget int,\n" +
            "  deadline datetime\n" +
            ");\n";

}