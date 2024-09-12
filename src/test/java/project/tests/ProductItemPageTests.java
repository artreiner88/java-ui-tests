package project.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import project.pages.LoginPage;
import project.pages.ProductItemPage;
import project.pages.ProductsPage;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static io.qameta.allure.Allure.step;

@Owner("Artur Reiner")
@Feature("Product details")
@Tag("product")
public class ProductItemPageTests extends BaseTest {

    ProductItemPage productItemPage = new ProductItemPage();
    ProductsPage productsPage = new ProductsPage();

    @BeforeEach
    @DisplayName("Product details page should be opened")
    void openProductItemPage() {
        step("Log in", () -> {
            new LoginPage().openLoginPage();
            new LoginPage().login("standard_user", "secret_sauce");
        });
        step("Products page should open", () -> {
            productsPage.title.shouldHave(text("Products"));
        });
        step("Page should have products", () -> {
            productsPage.productItems.shouldHave(sizeGreaterThan(0));
        });
        step("Click on product", () -> {
            productsPage.productName.click();
        });
        step("Product item page should open", () -> {
            productItemPage.backToProductsBtn.shouldBe(visible).shouldBe(clickable).shouldHave(text("Back to products"));
            productItemPage.productName.shouldBe(visible);
        });
    }

    @Test
    @Story("Adding product to shopping cart")
    @DisplayName("User should be able to add product to shopping cart")
    void addProductItemToShoppingCart() {
        step("Click add to shopping cart button", () -> {
            productItemPage.productAddToCartBtn.shouldBe(visible).shouldHave(text("Add to cart")).click();
        });
        step("Product should be added to shopping cart", () -> {
            productItemPage.shoppingCartBadge.shouldHave(text("1"));
            productItemPage.productRemoveFromCartBtn.shouldBe(visible).shouldHave(text("Remove"));
        });
    }

    @Test
    @Story("Removing product from shopping cart")
    @DisplayName("User should be able to remove product from shopping cart")
    void removeProductFromShoppingCart() {
        step("Product should be added to shopping cart", this::addProductItemToShoppingCart);
        step("Remove product from shopping cart", () -> {
            productItemPage.productRemoveFromCartBtn.click();
        });
        step("Product should be removed from shopping cart", () -> {
            productItemPage.shoppingCartBadge.shouldNotBe(visible);
            productItemPage.productAddToCartBtn.shouldBe(visible).shouldHave(text("Add to cart"));
        });
    }

    @Test
    @Story("Deauthorization")
    @DisplayName("User should be able to log out from product details page")
    void logout() {
        step("Open sidebar and click Logout button", () -> {
            productItemPage.logout();
        });
        step("Log in form should be opened", () -> {
            new LoginPage().loginBtn.shouldBe(visible);
        });
    }
}
