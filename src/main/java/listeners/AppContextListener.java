package listeners;

import factory.ObjectFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AppContextListener implements ServletContextListener {

    private ScheduledExecutorService projectFetchSchedule;
    private ScheduledExecutorService auctionSchedule;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        projectFetchSchedule = Executors.newSingleThreadScheduledExecutor();
        projectFetchSchedule.scheduleAtFixedRate(() ->
                ObjectFactory.getProjectService().fetchProjects(), 5, 5, TimeUnit.MINUTES);

        auctionSchedule = Executors.newSingleThreadScheduledExecutor();
        auctionSchedule.scheduleAtFixedRate(() ->
                ObjectFactory.getProjectService().auction(), 0, 1, TimeUnit.MINUTES);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        projectFetchSchedule.shutdownNow();
        auctionSchedule.shutdownNow();
    }

}