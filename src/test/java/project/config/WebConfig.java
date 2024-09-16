package project.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/${env}.properties",
        "classpath:config/local.properties"
})
public interface WebConfig extends Config {

    @Key("browser.baseUrl")
    String baseUrl();

    @Key("browser.browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browser.browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("browser.remote")
    Boolean remote();

    @Key("browser.selenoidUrl")
    String selenoidUrl();

    @Key("browser.videoStorage")
    String videoStorage();
}
