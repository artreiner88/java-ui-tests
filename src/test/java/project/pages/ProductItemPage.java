package project.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProductItemPage extends BasePage {

    public final SelenideElement backToProductsBtn = $("[data-test='back-to-products']");
    public final SelenideElement productName = $("[data-test='inventory-item-name']");
    public final SelenideElement productPrice = $("[data-test='inventory-item-price']");
    public final SelenideElement productAddToCartBtn = $("[data-test='add-to-cart']");
    public final SelenideElement productRemoveFromCartBtn = $("[data-test='remove']");
}
