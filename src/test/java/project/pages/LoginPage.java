package project.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    public final SelenideElement loginBtn = $("#login-button");
    public final SelenideElement errorMessageLabel = $("[data-test='error']");

    private final SelenideElement usernameTextField = $("#user-name");
    private final SelenideElement passwordTextField = $("#password");

    public void openLoginPage() {
        open("/");
    }

    public void login(String username, String password) {
        usernameTextField.setValue(username);
        passwordTextField.setValue(password);
        loginBtn.click();
    }
}
