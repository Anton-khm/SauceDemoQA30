package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ProductsPage;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
//retryAnalyzer = Retry.class,
    @Test(testName = "Проверка успешного логина", priority = 1, groups = {"smoke"})
    public void checkSuccessLogin() {
        loginStep.auth("standard_user", "secret_sauce");
        productsPage = new ProductsPage(driver);
        assertEquals(productsPage.getTitle(),"Products", "Логин не выполнен");
    }

    @Test(testName = "Проверка что логин не происходит при пустом пароле", priority = 4)
    public void checkLoginWithEmptyPassword() {
        loginStep.auth(user, "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required", "SO BAAAD");
    }

    @Test(testName = "Проверка логина с пустым именем пользователя", priority = 3)
    public void checkLoginWithEmptyLogin() {
        loginStep.auth("", "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required", "SO BAAAD");
    }

    @Test(testName = "Проверка что логин не происходит при неправильных логине и пароле", priority = 2)
    public void checkLoginInvalidCredentials() {
        loginStep.auth("rwe", "rew");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username and password do " +
                "not match any user in this service", "SO BAAAD");
    }

    @DataProvider
    public Object[][] loginData() {
        return new Object[][] {
                {"standard_user", "", "Epic sadface: Password is required"},
                {"standard_user", "123132131", "Epic sadface: Username and password do not match any user in this service"},
        };
    }

    @Test(testName = "Проверка логина", dataProvider = "loginData")
    public void login(String user, String password, String message) {
        loginStep.auth(user, password);
        assertEquals(loginPage.getErrorMessage(), message, "SO BAAAD");
    }
}
