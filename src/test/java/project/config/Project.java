package project.config;

import org.aeonbits.owner.ConfigFactory;

public class Project {
    public static WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());
    public static AuthConfig authConfig = ConfigFactory.create(AuthConfig.class, System.getProperties());
}
