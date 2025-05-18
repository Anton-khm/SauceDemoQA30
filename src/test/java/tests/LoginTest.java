package tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTest {
//retryAnalyzer = Retry.class,
    @Test(testName = "Проверка успешного логина", priority = 2, groups = {"smoke"})
    public void checkSuccessLogin() {
        loginPage.open();
        loginPage.login(user, password);
        assertEquals(productsPage.getTitle(),"Products", "Логин не выполнен");
    }

    @Test(testName = "Проверка что логин не происходит при пустом пароле", enabled = false)
    public void checkLoginWithEmptyPassword() {
        loginPage.open();
        loginPage.login(user, "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Password is required", "SO BAAAD");
    }

    @Test(testName = "Проверка логина с пустым именем пользователя", priority = 1)
    public void checkLoginWithEmptyLogin() {
        loginPage.open();
        loginPage.login("", "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required", "SO BAAAD");
    }

    @Test(testName = "Проверка логина с пустыми именем и паролем", priority = 3, description = "Test", alwaysRun = true, invocationCount = 2)
    public void checkLoginWithInvalidLogin() {
        loginPage.open();
        loginPage.login("", "");
        assertEquals(loginPage.getErrorMessage(), "Epic sadface: Username is required", "SO BAAAD");
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
        loginPage.open();
        loginPage.login(user, password);
        assertEquals(loginPage.getErrorMessage(), message, "SO BAAAD");
    }
}
