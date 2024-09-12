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
import project.pages.ShoppingCartPage;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static io.qameta.allure.Allure.step;

@Owner("Artur Reiner")
@Feature("Shopping cart")
@Tag("cart")
public class ShoppingCartPageTests extends BaseTest {

    ProductsPage productsPage = new ProductsPage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();

    @BeforeEach
    @DisplayName("Products page should be opened")
    void openProductsPage() {
        step("User should be logged in", () -> {
            new LoginPage().openLoginPage();
            new LoginPage().login("standard_user", "secret_sauce");
        });
        step("Products page should open", () -> {
            productsPage.appLogo.shouldBe(visible).shouldHave(text("Swag Labs"));
            productsPage.title.shouldHave(text("Products"));
        });
        step("Shopping cart button should be visible", () -> {
            productsPage.shoppingCart.shouldBe(visible);
        });
    }

    @Test
    @Story("Viewing products")
    @DisplayName("User should be able to navigate from shopping cart to products")
    void navigateBackToProducts() {
        step("Shopping cart should be opened", () -> {
            productsPage.shoppingCart.click();
            shoppingCartPage.title.shouldHave(text("Your cart"));
        });
        step("Click Continue Shopping Button", () -> {
            shoppingCartPage.continueShoppingBtn.click();
        });
        step("Products page should open", () -> {
            productsPage.title.shouldBe(visible).shouldHave(text("Products"));
        });
    }

    @Test
    @Tag("smoke")
    @Story("Removing product from shopping cart")
    @DisplayName("User should be able to remove product from shopping cart")
    void removeProductItem() {
        step("Add product item to shopping cart", () -> {
            productsPage.productBtn.click();
        });
        step("Product item should be added to shopping cart", () -> {
            productsPage.shoppingCartBadge.shouldHave(text("1"));
            productsPage.shoppingCart.click();
            shoppingCartPage.productItemsAddedToShoppingCart.shouldHave(sizeGreaterThan(0));
        });
        step("Click Remove button", () -> {
            shoppingCartPage.removeItemButton.shouldHave(text("Remove")).click();
        });
        step("Product item should be removed", () -> {
            shoppingCartPage.removedItem.should(exist);
            shoppingCartPage.removeItemButton.shouldNot(exist);
            shoppingCartPage.shoppingCartBadge.shouldNotBe(visible);
        });
    }

    @Test
    @Story("Deauthorization")
    @DisplayName("User should be able to log out from shopping cart")
    void logout() {
        step("Shopping cart should be opened", () -> {
            productsPage.shoppingCart.click();
            shoppingCartPage.title.shouldHave(text("Your Cart"));
        });
        step("Open sidebar and click Logout button", () -> {
            shoppingCartPage.logout();
        });
        step("Log in form should be opened", () -> {
            new LoginPage().loginBtn.shouldBe(visible);
        });
    }

    @Test
    @Story("Viewing shopping cart")
    @DisplayName("User should be able to see a product in shopping cart if they log out and log in again")
    void productItemShouldRemainInShoppingCartAfterReLogin() {
        step("Add product item to shopping cart", () -> {
            productsPage.productBtn.click();
        });
        step("Product item should be added to shopping cart", () -> {
            productsPage.shoppingCartBadge.shouldHave(text("1"));
            productsPage.shoppingCart.click();
            shoppingCartPage.productItemsAddedToShoppingCart.shouldHave(sizeGreaterThan(0));
        });
        step("Open sidebar and click Logout button", () -> {
            shoppingCartPage.logout();
        });
        step("User should be logged out", () -> {
            new LoginPage().loginBtn.shouldBe(visible);
        });
        step("User should log in", () -> {
            new LoginPage().login("standard_user", "secret_sauce");
        });
        step("Product item should remain in shopping cart", () -> {
            productsPage.shoppingCartBadge.shouldHave(text("1"));
            productsPage.shoppingCart.click();
            shoppingCartPage.productItemsAddedToShoppingCart.shouldHave(sizeGreaterThan(0));
        });
    }
}
