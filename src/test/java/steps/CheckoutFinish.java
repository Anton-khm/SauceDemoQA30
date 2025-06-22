package steps;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

@Log4j2
public class CheckoutFinish {
    WebDriver driver;
    CheckoutPage checkoutPage;

    public CheckoutFinish(WebDriver driver) {
        this.driver = driver;
        checkoutPage = new CheckoutPage(driver);
    }

    public void checkoutFinish(String firstName, String lastName, String postalCode){
        log.info("Filling the form with firstName:'{}', lastName:'{}', postalCode:'{}'", firstName, lastName, postalCode);
        checkoutPage.isPageOpened()
                .checkout(firstName, lastName, postalCode);
    }
}
