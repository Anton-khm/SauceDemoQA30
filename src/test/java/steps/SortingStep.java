package steps;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;

public class SortingStep {
    WebDriver driver;
    private final ProductsPage productsPage;

    public SortingStep(WebDriver driver) {
        this.driver = driver;
        productsPage = new ProductsPage(driver);
    }

    public void sortNames(String sorting) {
        productsPage
                .isPageOpened()
                .chooseSorting(sorting)
                .saveProductNames();
    }

    public void sortPrices(String sorting) {
        productsPage
                .isPageOpened()
                .chooseSorting(sorting)
                .saveProductPrices();
    }

    public boolean arePricesSortedCorrectly(String sorting) {
        return productsPage.arePricesCorrectlySorted(sorting);
    }

    public boolean areNamesSortedCorrectly(String sorting) {
        return productsPage.areNamesCorrectlySorted(sorting);
    }
}
