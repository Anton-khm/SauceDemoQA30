package steps;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;

@Log4j2
public class SortingStep {
    WebDriver driver;
    private final ProductsPage productsPage;

    public SortingStep(WebDriver driver) {
        this.driver = driver;
        productsPage = new ProductsPage(driver);
    }

    public void sortNames(String sorting) {
        log.info("Sorting names");
        productsPage
                .isPageOpened()
                .chooseSorting(sorting)
                .saveProductNames();
    }

    public void sortPrices(String sorting) {
        log.info("Sorting prices");
        productsPage
                .isPageOpened()
                .chooseSorting(sorting)
                .saveProductPrices();
    }

    public boolean arePricesSortedCorrectly(String sorting) {
        log.info("Checking if names correctly sorted");
        return productsPage.arePricesCorrectlySorted(sorting);
    }

    public boolean areNamesSortedCorrectly(String sorting) {
        log.info("Checking if prices correctly sorted");
        return productsPage.areNamesCorrectlySorted(sorting);
    }
}
