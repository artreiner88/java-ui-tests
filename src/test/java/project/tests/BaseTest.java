package project.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import project.helpers.Attachment;
import project.helpers.DriverSettings;

import static io.qameta.allure.Allure.step;
import static project.config.Project.webConfig;

public class BaseTest {

    @BeforeAll
    @DisplayName("Make initial configuration")
    static void setUp() {
        step("Setting up", () -> {
            SelenideLogger.addListener("allure", new AllureSelenide());
            DriverSettings.configure();
        });
    }

    @AfterEach
    @DisplayName("Tear down")
    void tearDown() {
        step("Add attachments", () -> {
            Attachment.screenshotAs("Last screenshot");
            if (webConfig.remote()) {
                Attachment.addVideo();
            }
        });
        step("Close browser", Selenide::closeWebDriver);
    }
}
