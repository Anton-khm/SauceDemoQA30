package steps;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Log4j2
public class CartStep {
    WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;

    public CartStep(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    public void addProductToCart(String username, String password, String product){
        log.info("Adding product '{}' to cart", product);
        loginPage.open()
                .isPageOpened()
                .login(username, password)
                .isPageOpened()
                .addToCart(product)
                .goToCart();
    }
}
