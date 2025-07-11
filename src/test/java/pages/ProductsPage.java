package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import tests.AllureUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private List<String> productNames;
    private List<Double> productPrices;

    private static final By TITLE = By.cssSelector("[data-test=title]");
    private static final By CART_BUTTON = By.cssSelector(".shopping_cart_link");
    private static final String ADD_TO_CART_PATTERN = "//*[text() = '%s']/ancestor::div[@class = 'inventory_item']//button";

    public String getTitle() {
        return driver.findElement(TITLE).getText();
    }

    @Override
    public ProductsPage open() {
        log.info("Opening ProductsPage");
        driver.get(BASE_URL + "/inventory.html");
        return this;
    }

    @Override
    public ProductsPage isPageOpened() {
        try {
            List<WebElement> elements = driver.findElements(By.cssSelector("button[id^='add-to-cart']"));
            WebElement lastElement = elements.get(elements.size() - 1);
            wait.until(ExpectedConditions.visibilityOf(lastElement));
        }catch (TimeoutException e){
            log.error(e.getMessage());
            Assert.fail("ProductsPage isn't opened");
        }
        return this;
    }

    @Step("Добавление товара с именем: {product} в корзину")
    public ProductsPage addToCart(String product) {
        log.info("Adding product '{}' to cart", product);
        driver.findElement(By.xpath(String.format(ADD_TO_CART_PATTERN, product))).click();
        return this;
        //    AllureUtils.takeScreenshot(driver);  --делает скрин на шаге
    }

    @Step("Переход в корзину")
    public CartPage goToCart() {
        log.info("Opening CartPage");
        driver.findElement(CART_BUTTON).click();
        return new CartPage(driver);
    }

    public ProductsPage chooseSorting(String sorting) {
        log.info("Choosing product sorting: '{}'", sorting);
        Select select = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        select.selectByValue(sorting);
        return this;
    }

    public ProductsPage saveProductNames() {
        log.info("Saving product names to variable");
        this.productNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return this;
    }

    public ProductsPage saveProductPrices() {
        log.info("Saving product prices to variable");
        this.productPrices = driver.findElements(By.cssSelector(".inventory_item_price"))
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
        return this;
    }

    public List<String> getSavedProductNames() {
        return this.productNames;
    }

    public List<Double> getSavedProductPrices() {
        return this.productPrices;
    }

    public boolean areNamesCorrectlySorted(String sorting) {
        log.info("Checking if names correctly sorted");
        List<String> names = getSavedProductNames();
        List<String> sorted = new ArrayList<>(names);

        if ("az".equalsIgnoreCase(sorting)) {
            Collections.sort(sorted);
        } else if ("za".equalsIgnoreCase(sorting)) {
            sorted.sort(Collections.reverseOrder());
        } else {
            throw new IllegalArgumentException("Invalid name sorting value: " + sorting);
        }

        return names.equals(sorted);
    }

    public boolean arePricesCorrectlySorted(String sorting) {
        log.info("Checking if prices correctly sorted");
        List<Double> prices = getSavedProductPrices();
        List<Double> sorted = new ArrayList<>(prices);

        if ("lohi".equalsIgnoreCase(sorting)) {
            Collections.sort(sorted);
        } else if ("hilo".equalsIgnoreCase(sorting)) {
            sorted.sort(Collections.reverseOrder());
        } else {
            throw new IllegalArgumentException("Invalid price sorting value: " + sorting);
        }

        return prices.equals(sorted);
    }
}
