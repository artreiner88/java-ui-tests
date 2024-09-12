package project.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage extends BasePage {

    public final ElementsCollection productItems = $$("[data-test='inventory-item']");
    public final SelenideElement productName = $("[data-test='inventory-item-name']");
    public final SelenideElement productPrice = $("[data-test='inventory-item-price']");
    public final SelenideElement productBtn = $(".btn_inventory");

    private final SelenideElement productSortContainer = $("[data-test='product-sort-container']");
    private final SelenideElement productSorterByNameDesc = $("[value='za']");
    private final SelenideElement productSorterByNameAsc = $("[value='az']");
    private final SelenideElement productSorterByPriceDesc = $("[value='hilo']");
    private final SelenideElement productSorterByPriceAsc = $("[value='lohi']");

    public void sortProductsByNameDesc() {
        productSortContainer.click();
        productSorterByNameDesc.click();
    }

    public void sortProductsByNameAsc() {
        productSortContainer.click();
        productSorterByNameAsc.click();
    }

    public void sortProductsByPriceHiLo() {
        productSortContainer.click();
        productSorterByPriceDesc.click();
    }

    public void sortProductsByPriceLoHi() {
        productSortContainer.click();
        productSorterByPriceAsc.click();
    }

    public boolean firstShouldBeBiggerThanSecond(String a, String b) {
        return a.compareToIgnoreCase(b) == 1;
    }

    public Double getProductPrice() {
        return Double.parseDouble(productPrice.text().substring(1));
    }
}
