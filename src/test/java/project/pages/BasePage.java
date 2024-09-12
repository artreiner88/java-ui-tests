package project.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {

    public final SelenideElement appLogo = $(".app_logo");
    public final SelenideElement title = $("[data-test='title']");
    public final SelenideElement shoppingCart = $("[data-test='shopping-cart-link']");
    public final SelenideElement shoppingCartBadge = $("[data-test='shopping-cart-badge']");

    private final SelenideElement sideBarMenuButton = $("#react-burger-menu-btn");
    private final SelenideElement logoutBtn = $("[data-test='logout-sidebar-link']");

    public void logout() {
        sideBarMenuButton.click();
        logoutBtn.click();
    }
}
