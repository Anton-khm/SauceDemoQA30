package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import tests.AllureUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private List<String> lastProductNames;
    private List<Double> lastProductPrices;

    private static final By TITLE = By.cssSelector("[data-test=title]");
    private static final By CART_BUTTON = By.cssSelector(".shopping_cart_link");
    private static final String ADD_TO_CART_PATTERN = "//*[text() = '%s']/ancestor::div[@class = 'inventory_item']//button";

    public String getTitle() {
        return driver.findElement(TITLE).getText();
    }

    @Override
    public ProductsPage open() {
        driver.get(BASE_URL + "/inventory.html");
        return this;
    }

    @Override
    public ProductsPage isPageOpened() {
        List<WebElement> elements = driver.findElements(By.cssSelector("button[id^='add-to-cart']"));
        WebElement lastElement = elements.get(elements.size() - 1);
        wait.until(ExpectedConditions.visibilityOf(lastElement));
        return this;
    }

    @Step("Добавление товара с именем: {product} в корзину")
    public ProductsPage addToCart(String product) {
        driver.findElement(By.xpath(String.format(ADD_TO_CART_PATTERN, product))).click();
        return this;
        //    AllureUtils.takeScreenshot(driver);  --делает скрин на шаге
    }

    @Step("Переход в корзину")
    public CartPage goToCart() {
        driver.findElement(CART_BUTTON).click();
        return new CartPage(driver);
    }

    public ProductsPage chooseSorting(String sorting) {
        Select select = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        select.selectByValue(sorting);
        return this;
    }

    public ProductsPage saveSortedProductNamesSnapshot(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        this.lastProductNames = new ArrayList<>();

        for (WebElement product : listOfProducts) {
            this.lastProductNames.add(product.getText());
        }

        if ("az".equals(sorting)) {
            Collections.sort(this.lastProductNames);
        } else if ("za".equals(sorting)) {
            this.lastProductNames.sort(Collections.reverseOrder());
        }

        return this;
    }

    public ProductsPage saveSortedProductPricesSnapshot(String sorting) {
        List<WebElement> listOfProductPrices = driver.findElements(By.cssSelector(".inventory_item_price"));
        this.lastProductPrices = new ArrayList<>();

        for (WebElement price : listOfProductPrices) {
            this.lastProductPrices.add(Double.parseDouble(price.getText().substring(1)));
        }

        if ("lohi".equals(sorting)) {
            Collections.sort(this.lastProductPrices);
        } else if ("hilo".equals(sorting)) {
            this.lastProductPrices.sort(Collections.reverseOrder());
        }

        return this;
    }

    public boolean areNamesCorrectlySorted(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : listOfProducts) {
            names.add(product.getText());
        }

        ArrayList<String> expectedNames = new ArrayList<>(names);
        if ("az".equals(sorting)) {
            Collections.sort(expectedNames);
        } else if ("za".equals(sorting)) {
            expectedNames.sort(Collections.reverseOrder());
        }

        return names.equals(expectedNames);
    }

    public boolean arePricesCorrectlySorted(String sorting) {
        List<WebElement> listOfProductPrices = driver.findElements(By.cssSelector(".inventory_item_price"));
        ArrayList<Double> prices = new ArrayList<>();

        for (WebElement price : listOfProductPrices) {
            prices.add(Double.parseDouble(price.getText().substring(1)));
        }

        ArrayList<Double> expectedPrices = new ArrayList<>(prices);
        if ("lohi".equals(sorting)) {
            Collections.sort(expectedPrices);
        } else if ("hilo".equals(sorting)) {
            expectedPrices.sort(Collections.reverseOrder());
        }

        return prices.equals(expectedPrices);
    }
}
