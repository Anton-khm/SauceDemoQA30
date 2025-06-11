package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    private static final By TITLE = By.cssSelector("[data-test=title]");
    private static final By CART_BUTTON = By.cssSelector(".shopping_cart_link");


    private static final String ADD_TO_CART_PATTERN = "//*[text() = '%s']/ancestor::div[@class = 'inventory_item']//button";

    public String getTitle() {
        return driver.findElement(TITLE).getText();
    }

    public void addToCart(String product) {
        driver.findElement(By.xpath(String.format(ADD_TO_CART_PATTERN, product))).click();
    }

    public void goToCart() {
        driver.findElement(CART_BUTTON).click();
    }

    public void chooseSorting(String sorting) {
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
    }

    public ArrayList<String> getInitialProductNames(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : listOfProducts) {
            names.add(product.getText());
        }
        if (sorting == "az") {
                Collections.sort(names);
                return names; }
                else if (sorting == "za") {
                Collections.sort(names, Collections.reverseOrder());
                return names;
            }
                return names;
    }

    public ArrayList<Double> getInitialProductPrices(String sorting) {
        List<WebElement> listOfProductPrices = driver.findElements(By.cssSelector(".inventory_item_price"));
        ArrayList<Double> prices = new ArrayList<>();
        for (WebElement price : listOfProductPrices) {
            prices.add(Double.parseDouble(price.getText().substring(1)));
        }

        if (sorting == "lohi") {
            Collections.sort(prices);
            return prices;
        }
                else {
                Collections.sort(prices, Collections.reverseOrder());
                return prices;
            }
    }

    public boolean areNamesCorrectlySorted(String sorting) {
        List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".inventory_item_name"));

        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : listOfProducts) {
            names.add(product.getText());
        }
        if(sorting == "az") {
            Collections.sort(names);
        } else {
            Collections.sort(names, Collections.reverseOrder());
        }
        if (names.equals(getInitialProductNames(sorting))) {
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
        if(sorting == "lohi") {
            Collections.sort(prices);
        } else {
            Collections.sort(prices, Collections.reverseOrder());
        }
        if (prices.equals(getInitialProductPrices(sorting))) {
            return true;
        } else {
            return false;
        }
    }
}
