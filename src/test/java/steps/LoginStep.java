package steps;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginStep {
    WebDriver driver;
    LoginPage loginPage;


    public LoginStep(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
    }

    public void auth(String username, String password){
        loginPage.open()
                .isPageOpened()
                .login(username, password);
    }
}
