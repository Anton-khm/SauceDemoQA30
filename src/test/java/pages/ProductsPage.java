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
    public ProductsPage open(){
        driver.get(BASE_URL + "/inventory.html");
        return this;
    }

    @Override
    public ProductsPage isPageOpened(){
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
    public ProductsPage goToCart() {
        driver.findElement(CART_BUTTON).click();
        return this;
    }

    public ProductsPage chooseSorting(String sorting) {
        Select select = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        switch (sorting) {
            case "az":
                select.selectByValue("az");
                break;
            case "za":
                select.selectByValue("za");
                break;
            case "lohi":
                select.selectByValue("lohi");
                break;
            default:
                select.selectByValue("hilo");
                break;
        }
        return this;
    }

    public ProductsPage getInitialProductNames(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
//        ArrayList<String> names = new ArrayList<>();
        this.lastProductNames = new ArrayList<>();

        for (WebElement product : listOfProducts) {
            this.lastProductNames.add(product.getText());
        }
        if ("az".equals(sorting)) {
            Collections.sort(this.lastProductNames);
        } else if ("za".equals(sorting)) {
            Collections.sort(this.lastProductNames, Collections.reverseOrder());
        }

        return this;
    }

    public ProductsPage getInitialProductPrices(String sorting) {
        List<WebElement> listOfProductPrices = driver.findElements(By.cssSelector(".inventory_item_price"));
//        ArrayList<Double> prices = new ArrayList<>();
        this.lastProductPrices = new ArrayList<>();

        for (WebElement price : listOfProductPrices) {
            this.lastProductPrices.add(Double.parseDouble(price.getText().substring(1)));
        }

        if ("lohi".equals(sorting)) {
            Collections.sort(this.lastProductPrices);
        } else if ("hilo".equals(sorting)) {
            Collections.sort(this.lastProductPrices, Collections.reverseOrder());
        }

        return this;
    }

    public List<String> getLastProductNames() {
        return this.lastProductNames;
    }

    public List<Double> getLastProductPrices() {
        return this.lastProductPrices;
    }

    public boolean areNamesCorrectlySorted(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        ArrayList<String> names = new ArrayList<>();

        for (WebElement product : listOfProducts) {
            names.add(product.getText());
        }

        if("az".equals(sorting)) {
            Collections.sort(names);
        } else {
            Collections.sort(names, Collections.reverseOrder());
        }
        if (names.equals(getLastProductNames())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean arePricesCorrectlySorted(String sorting) {
        List<WebElement> listOfProductPrices = driver.findElements(By.cssSelector(".inventory_item_price"));
        ArrayList<Double> prices = new ArrayList<>();

        for (WebElement price : listOfProductPrices) {
            prices.add(Double.parseDouble(price.getText().substring(1)));
        }

        if("lohi".equals(sorting)) {
            Collections.sort(prices);
        } else {
            Collections.sort(prices, Collections.reverseOrder());
        }
        if (prices.equals(getLastProductPrices())) {
            return true;
        } else {
            return false;
        }
    }
}
