package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.AllureUtils;


import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CartPage open() {
        driver.get(BASE_URL + "/cart.html");
        return this;
    }

    @Override
    public CartPage isPageOpened(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        return this;
    }

    public boolean isProductInCart(String product) {
        return driver.findElement(By.xpath(String.format("//div[@class='cart_item']//*[text()='%s']", product)))
                .isDisplayed();
    }

    public String getProductFromCart(int index) {
        return driver.findElements(By.cssSelector(".inventory_item_name"))
                .get(index)
                .getText();
    }

    public ArrayList<String> getProductsName() {
        List<WebElement> allProductsElements = driver.findElements(By.cssSelector(".inventory_item_name"));
        ArrayList<String> names = new ArrayList<>();
        for (WebElement product : allProductsElements) {
            names.add(product.getText());
        }
        return names;
    }

    public double getProductPrice(String product) {
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
        AllureUtils.takeScreenshot(driver);
        return driver.findElement(By.cssSelector("[data-test='payment-info-value']")).getText().substring(11);
    }

    public String getShippingWay() {
        return driver.findElement(By.cssSelector("[data-test='shipping-info-value']")).getText();
    }

    public String getTotalSum(){
        return driver.findElement(By.cssSelector("[data-test='total-label']")).getText().substring(8);
    }

    public CartPage goToFinishPage(){
        driver.findElement(By.id("finish")).click();
        return this;
    }
}
