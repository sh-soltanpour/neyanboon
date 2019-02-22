package Factory;

import client.MetaDataClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import services.project.ProjectService;
import services.project.ProjectServiceImpl;
import services.user.UserService;
import services.user.UserServiceImpl;

public class ObjectFactory {

    private static ProjectService projectService;
    private static UserService userService;
    private static MetaDataClient metaDataClient;

    public static ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

    public static MetaDataClient getMetaDataClient() {
        if (metaDataClient == null) {
            String metaDataServer = "http://142.93.134.194:8000";
            metaDataClient = Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(MetaDataClient.class, metaDataServer);
        }
        return metaDataClient;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
