package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

@Log4j2
public class CheckoutPage extends BasePage{
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    private static final By FIRST_NAME_FIELD = By.id("first-name");
    private static final By LAST_NAME_FIELD = By.id("last-name");
    private static final By POSTAL_CODE_FIELD = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    @Override
    public CheckoutPage open() {
        log.info("Opening CheckoutPage");
        driver.get(BASE_URL + "/checkout-step-one.html");
        return this;
    }

    @Override
    public CheckoutPage isPageOpened(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code")));
        }catch (TimeoutException e){
            log.error(e.getMessage());
            Assert.fail("Checkout page isn't opened");
        }
        return this;
    }

    @Step("Заполнение информации о заказе: Имя: {firstName}, Фамилия: {lastName}, Почтовый индекс: {postalCode} и нажатие на Продолжить")
    public CheckoutPage checkout(String firstName, String lastName, String postalCode) {
        log.info("Making checkout with: firstName {}, lastName {}, postalCode {}", firstName, lastName, postalCode);
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        driver.findElement(POSTAL_CODE_FIELD).sendKeys(postalCode);
        driver.findElement(CONTINUE_BUTTON).click();
        return this;
    }

    public String getErrorMessage() {
        log.error("Getting error message");
        return driver.findElement(ERROR_MESSAGE).getText();
    }

    public boolean isContinueButtonPresent() {
        log.info("Checking if 'Continue' button is present");
        return driver.findElement(CONTINUE_BUTTON).isDisplayed();
    }

    public boolean isCancelButtonPresent() {
        log.info("Checking if 'Cancel' button is present");
        return driver.findElement(CANCEL_BUTTON).isDisplayed();
    }
}
