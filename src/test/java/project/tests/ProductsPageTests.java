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
import project.pages.ShoppingCartPage;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Owner("Artur Reiner")
@Feature("Products")
@Tag("product")
public class ProductsPageTests extends BaseTest {

    ProductsPage productsPage = new ProductsPage();
    ProductItemPage productItemPage = new ProductItemPage();

    @BeforeEach
    @DisplayName("Products page should be opened")
    void openProductsPage() {
        step("Log in", () -> {
            new LoginPage().openLoginPage();
            new LoginPage().login("standard_user", "secret_sauce");
        });
        step("Products page should open", () -> {
            productsPage.appLogo.shouldBe(visible).shouldHave(text("Swag Labs"));
            productsPage.title.shouldHave(text("Products"));
        });
    }

    @Test
    @Tag("smoke")
    @Story("Adding product to shopping cart")
    @DisplayName("User should be able to add a product to shopping cart")
    void addProductToCart() {
        step("Page should have products", () -> {
            productsPage.productItems.shouldHave(sizeGreaterThan(0));
        });
        step("Shopping cart should be empty", () -> {
            productsPage.shoppingCartBadge.shouldNotBe(visible);
        });
        step("Product should have the Add to cart button", () -> {
            productsPage.productBtn.shouldBe(visible).shouldBe(clickable);
            productsPage.productBtn.shouldHave(text("Add to cart"));
        });
        step("Add product to shopping cart", () -> {
            productsPage.productBtn.click();
        });
        step("Product should be added to shopping cart", () -> {
            productsPage.productBtn.shouldHave(text("Remove"));
            productsPage.shoppingCartBadge.shouldHave(text("1"));
        });
    }

    @Test
    @Tag("smoke")
    @Story("Removing product from shopping cart")
    @DisplayName("User should be able to remove a product from shopping cart")
    void removeProductFromCart() {
        step("Shopping cart should have a product added", this::addProductToCart);
        step("Remove product from shopping cart", () -> {
            productsPage.productBtn.click();;
        });
        step("Product should be removed from shopping cart", () -> {
            productsPage.productBtn.shouldHave(text("Add to cart"));
            productsPage.shoppingCartBadge.shouldNotBe(visible);
        });
    }

    @Test
    @Story("Products sorting")
    @DisplayName("User should be able to sort products by name")
    void sortProductsByName() {
        step("Page should have products", () -> {
            productsPage.productItems.shouldHave(sizeGreaterThan(0));
        });
        step("Sort products by name <descending> and verify it works", () -> {
            String nameSortedAsc = productsPage.productName.text();
            productsPage.sortProductsByNameDesc();
            String nameSortedDesc = productsPage.productName.text();
            assertTrue(
                    productsPage.firstShouldBeBiggerThanSecond(nameSortedDesc, nameSortedAsc),
                    "Sorting by Product name DESC failed");
        });
        step("Sort products by name  <ascending> and verify it works", () -> {
            String nameSortedDesc = productsPage.productName.text();
            productsPage.sortProductsByNameAsc();
            String nameSortedAsc = productsPage.productName.text();
            assertTrue(
                    productsPage.firstShouldBeBiggerThanSecond(nameSortedDesc, nameSortedAsc),
                    "Sorting by Product name ASC failed");
        });
    }

    @Test
    @Story("Products sorting")
    @DisplayName("User should be able to sort products by price")
    void sortProductsByPrice() {
        step("Page should have products", () -> {
            productsPage.productItems.shouldHave(sizeGreaterThan(0));
        });
        step("Sort products by price <high to low> and verify it works", () -> {
            productsPage.sortProductsByPriceLoHi();
            double minPrice = productsPage.getProductPrice();
            productsPage.sortProductsByPriceHiLo();
            double maxPrice = productsPage.getProductPrice();
            assertTrue(maxPrice > minPrice, "Sorting by Product price DESC failed");
        });
        step("Sort products by price <low to high> and verify it works", () -> {
            double maxPrice = productsPage.getProductPrice();
            productsPage.sortProductsByPriceLoHi();
            double minPrice = productsPage.getProductPrice();
            assertTrue(maxPrice > minPrice, "Sorting by Product price ASC failed");
        });
    }

    @Test
    @Tag("smoke")
    @Story("Viewing shopping cart")
    @DisplayName("User should be able to navigate to shopping cart")
    void navigateToShoppingCart() {
        step("Click on shopping cart", () -> {
            productsPage.shoppingCart.click();
        });
        step("Shopping cart should open", () -> {
            new ShoppingCartPage().title.shouldBe(visible).shouldHave(text("Your Cart"));
        });
    }

    @Test
    @Story("Viewing product details")
    @DisplayName("User should be able to navigate to a product details")
    void navigateToProductItem() {
        String productItemName =  productsPage.productName.text();
        String productPrice = productsPage.productPrice.text();
        step("Page should have products", () -> {
            productsPage.productItems.shouldHave(sizeGreaterThan(0));
        });
        step("Click on product item", () -> {
            productsPage.productName.click();
        });
        step("Product item page should open", () -> {
            productItemPage.backToProductsBtn.shouldBe(visible).shouldHave(text("Back to products"));
        });
        step("Product item name should match", () -> {
            productItemPage.productName.shouldHave(text(productItemName));
        });
        step("Product item price should match", () -> {
            productItemPage.productPrice.shouldHave(text(productPrice));
        });
        step("Add to cart button should be visible", () -> {
            productItemPage.productAddToCartBtn.shouldBe(visible).shouldHave(text("Add to cart"));
        });
    }

    @Test
    @Story("Deauthorization")
    @DisplayName("User should be able to log out from products page")
    void logout() {
        step("Open sidebar and click Logout button", () -> {
            productsPage.logout();
        });
        step("Log in form should be opened", () -> {
            new LoginPage().loginBtn.shouldBe(visible);
        });
    }
}
