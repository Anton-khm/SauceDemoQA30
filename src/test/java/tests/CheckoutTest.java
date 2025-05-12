package tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutTest extends BaseTest{
    @Test
    public void checkIfButtonsAreVisible() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        assertTrue(checkoutPage.isCancelButtonPresent());
        assertTrue(checkoutPage.isContinueButtonPresent());
    }

    @Test
    public void checkSuccessDeliveryInfo() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        checkoutPage.checkout("Test", "Test", "123456");
        assertEquals(cartPage.getPaymentInformation(),
                "31337",
                "SO BAAAAAD");
        assertEquals(cartPage.getShippingWay(),
                "Free Pony Express Delivery!",
                "SO BAAAAAD");
        assertEquals(cartPage.getTotalSum(),
                "32.39",
                "SO BAAAAAD");
    }

    @Test
    public void checkErrorIfFirstNameEmpty() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        checkoutPage.checkout("", "Test", "234344");
        assertEquals(checkoutPage.getErrorMessage(),
                "Error: First Name is required",
                "SO BAAAAAD");
    }

    @Test
    public void checkErrorIfPostalEmpty() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        checkoutPage.checkout("Test", "Test", "");
        assertEquals(checkoutPage.getErrorMessage(),
                "Error: Postal Code is required",
                "SO BAAAAAD");
    }

    @Test
    public void checkCompleteCheckout() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        cartPage.goToFinishPage();
        assertEquals(completePage.checkSuccessOrderMessage(), "Thank you for your order!", "SO BAAAAAD");
    }

    @Test
    public void checkSortingByNameASC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductNames("az");
        productsPage.chooseSorting("az");
        assertTrue(productsPage.areNamesCorrectlySorted("az"));
    }

    @Test
    public void checkSortingByNameDESC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductNames("za");
        productsPage.chooseSorting("za");
        assertTrue(productsPage.areNamesCorrectlySorted("za"));
    }

    @Test
    public void checkSortingByPriceASC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductPrices("lohi");
        productsPage.chooseSorting("lohi");
        assertTrue(productsPage.arePricesCorrectlySorted("lohi"));
    }

    @Test
    public void checkSortingByPriceDESC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductPrices("hilo");
        productsPage.chooseSorting("hilo");
        assertTrue(productsPage.arePricesCorrectlySorted("hilo"));
    }
}
