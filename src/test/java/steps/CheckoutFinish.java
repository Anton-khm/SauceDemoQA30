package steps;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CheckoutFinish {
    WebDriver driver;
    CheckoutPage checkoutPage;

    public CheckoutFinish(WebDriver driver) {
        this.driver = driver;
        checkoutPage = new CheckoutPage(driver);
    }

    public void checkoutFinish(String firstName, String lastName, String postalCode){
        checkoutPage.isPageOpened()
                .checkout(firstName, lastName, postalCode);
    }
}
