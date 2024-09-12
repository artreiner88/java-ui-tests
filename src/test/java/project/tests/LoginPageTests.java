package project.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import project.pages.LoginPage;
import project.pages.ProductsPage;

import static com.codeborne.selenide.Condition.text;
import static io.qameta.allure.Allure.step;
import static project.config.Project.authConfig;

@Owner("Artur Reiner")
@Feature("Authorization")
@Tag("login")
public class LoginPageTests extends BaseTest {

    LoginPage loginPage = new LoginPage();
    ProductsPage productsPage = new ProductsPage();

    @BeforeEach
    @DisplayName("Login page should be opened")
    void openLoginPage() {
        step("Open Login page", () -> {
            loginPage.openLoginPage();
        });
    }

    @Test
    @Tag("smoke")
    @Story("Successful authorization")
    @DisplayName("User with valid credentials should be able to log in")
    void loginStandard() {
        step("Positive  log in: correct username and password", () -> {
            loginPage.login(authConfig.username(), authConfig.password());
        });
        step("Log in should be successful", () -> {
            productsPage.title.shouldHave(text("Products"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("User with empty credentials should not be able to log in")
    void loginEmptyCredentials() {
        step("Negative log in: empty username and password", () -> {
            loginPage.login(null, null);
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Username is required"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("User with empty username should not be able to log in")
    void loginEmptyUsername() {
        step("Negative log in: empty username", () -> {
            loginPage.login(null, authConfig.password());
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Username is required"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("User with empty password should not be able to log in")
    void loginEmptyPassword() {
        step("Negative log in: empty password", () -> {
            loginPage.login(authConfig.username(), null);
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Password is required"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("User with unknown username should not be able to log in")
    void loginNonExistingUser() {
        step("Negative log in: user does not exist", () -> {
            loginPage.login(authConfig.nonExistingUser(), authConfig.password());
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Username and password do not match any user in this service"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("User with incorrect password should not be able to log in")
    void loginIncorrectPassword() {
        step("Negative log in: incorrect password", () -> {
            loginPage.login(authConfig.username(), authConfig.wrongPassword());
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Username and password do not match any user in this service"));
        });
    }

    @Test
    @Story("Unsuccessful authorization")
    @DisplayName("Locked out user should not be able to log in")
    void loginLockedOutUser() {
        step("Negative log in: locked out user", () -> {
            loginPage.login(authConfig.lockedOutUser(), authConfig.password());
        });
        step("Verify error message is visible", () -> {
            loginPage.errorMessageLabel.shouldHave(text("Epic sadface: Sorry, this user has been locked out."));
        });
    }
}
