package steps;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Log4j2
public class LoginStep {
    WebDriver driver;
    LoginPage loginPage;


    public LoginStep(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
    }

    public void auth(String username, String password){
        log.info("Log in with credential: '{}', '{}'", username, password);
        loginPage.open()
                .isPageOpened()
                .login(username, password);
    }
}
