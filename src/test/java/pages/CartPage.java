package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import tests.AllureUtils;


import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CartPage open() {
        log.info("Opening CartPage");
        driver.get(BASE_URL + "/cart.html");
        return this;
    }

    @Override
    public CartPage isPageOpened(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        } catch (TimeoutException e){
            log.error(e.getMessage());
            Assert.fail("CartPage isn't opened");
        }
        return this;
    }

    public boolean isProductInCart(String product) {
        log.info("{} is displayed in cart", product);
        return driver.findElement(By.xpath(String.format("//div[@class='cart_item']//*[text()='%s']", product)))
                .isDisplayed();
    }

    public String getProductFromCart(int index) {
        log.info("Getting product from cart");
        return driver.findElements(By.cssSelector(".inventory_item_name"))
                .get(index)
                .getText();
    }

    public ArrayList<String> getProductsName() {
        log.info("Getting product names");
        List<WebElement> allProductsElements = driver.findElements(By.cssSelector(".inventory_item_name"));
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : allProductsElements) {
            names.add(product.getText());
        }
        return names;
    }

    public double getProductPrice(String product) {
        log.info("Getting product prices");
        return Double.parseDouble(driver.findElement(
                        By.xpath(String.format(
                                "//*[text() = '%s']/ancestor::div[@class='cart_item']//" +
                                        "*[@class = 'inventory_item_price']", product)))
                .getText().replace("$", ""));
    }

    @Step("Переход на страницу оформления заказа")
    public CheckoutPage goToCheckout() {
        driver.findElement(By.id("checkout")).click();
        return new CheckoutPage(driver);
    }

    public String getPaymentInformation() {
        log.info("Getting payment information");
        AllureUtils.takeScreenshot(driver);
        return driver.findElement(By.cssSelector("[data-test='payment-info-value']")).getText().substring(11);
    }

    public String getShippingWay() {
        log.info("Getting shipping way");
        return driver.findElement(By.cssSelector("[data-test='shipping-info-value']")).getText();
    }

    public String getTotalSum(){
        log.info("Getting total sum");
        return driver.findElement(By.cssSelector("[data-test='total-label']")).getText().substring(8);
    }

    public CartPage goToFinishPage(){
        log.info("Opening finish page");
        driver.findElement(By.id("finish")).click();
        return this;
    }
}
