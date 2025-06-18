package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public LoginPage isPageOpened(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        return this;
    }

    @Step("Вход в систему с именем пользователя: {username} и паролем: {password}")
    public ProductsPage login(String username, String password) {
        driver.findElement(USER_NAME_FIELD).sendKeys(username);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    public String getErrorMessage() {
       return driver.findElement(ERROR_MESSAGE).getText();
    }
}
