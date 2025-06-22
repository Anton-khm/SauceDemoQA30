package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

@Log4j2
public class LoginPage extends BasePage{

    //By - класс селениума про локаторы
    private static final By USER_NAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.xpath("//h3[@data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Открытие страницы LoginPage")
    public LoginPage open() {
        log.info("Opening LoginPage");
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public LoginPage isPageOpened(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        }catch (TimeoutException e){
            log.error(e.getMessage());
            Assert.fail("LoginPage isn't opened");
        }
        return this;
    }

    @Step("Вход в систему с именем пользователя: {username} и паролем: {password}")
    public ProductsPage login(String username, String password) {
        log.info("Log in with credential: '{}', '{}'", username, password);
        driver.findElement(USER_NAME_FIELD).sendKeys(username);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    public String getErrorMessage() {
       log.info("Getting error message");
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
