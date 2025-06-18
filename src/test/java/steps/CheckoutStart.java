package steps;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

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
