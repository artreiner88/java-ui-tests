package project.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ShoppingCartPage extends BasePage {

    public final SelenideElement continueShoppingBtn = $("[data-test='continue-shopping']");
    public final SelenideElement removeItemButton = $(".cart_button");
    public final SelenideElement removedItem = $(".removed_cart_item");
    public final ElementsCollection productItemsAddedToShoppingCart = $$("[data-test='inventory-item'");
}
