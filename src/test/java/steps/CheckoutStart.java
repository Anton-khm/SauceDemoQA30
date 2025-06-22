package steps;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Log4j2
public class CheckoutStart {
    WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;

    public CheckoutStart(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
    }

    public void goToCheckout(String username, String password, String product){
        log.info("Making checkout of product: '{}'", product);
        loginPage.open()
                .isPageOpened()
                .login(username, password)
                .isPageOpened()
                .addToCart(product)
                .goToCart()
                .isPageOpened()
                .goToCheckout();
    }
}
