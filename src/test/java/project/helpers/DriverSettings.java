package project.helpers;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static project.config.Project.webConfig;

public class DriverSettings {

    public static void configure() {
        Configuration.baseUrl = webConfig.baseUrl();
        Configuration.browser = webConfig.browser();
        Configuration.browserSize = webConfig.browserSize();

        switch (Configuration.browser) {
            case "chrome":
                setChromeOptions();
                break;
            case "firefox":
                setFirefoxOptions();
                break;
        }

        if (webConfig.remote()) {
            Configuration.remote = webConfig.selenoidUrl();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }

    private static void setChromeOptions() {
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--no-sandbox")
                .addArguments("--disable-infobars")
                .addArguments("--disable-popup-blocking")
                .addArguments("--disable-notifications")
                .addArguments("--lang=en-US")
                .setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    }

    private static void setFirefoxOptions() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("dom.webnotifications.enabled", false);  // Disable notifications
        Configuration.browserCapabilities = new FirefoxOptions()
                .setProfile(firefoxProfile);
    }
}
