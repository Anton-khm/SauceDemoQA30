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
                .saveSortedProductNamesSnapshot(sorting);
    }

    public void sortPrices(String sorting) {
        productsPage
                .isPageOpened()
                .chooseSorting(sorting)
                .saveSortedProductPricesSnapshot(sorting);
    }

    public boolean arePricesSortedCorrectly(String sorting) {
        return productsPage.arePricesCorrectlySorted(sorting);
    }

    public boolean areNamesSortedCorrectly(String sorting) {
        return productsPage.areNamesCorrectlySorted(sorting);
    }
}
