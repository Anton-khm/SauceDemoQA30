package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartTest extends BaseTest {

    @Test(testName = "Проверка добавления товара в корзину", groups = {"smoke"}, priority = 1)
    public void checkCart() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"),
                "SO BAAAAD");
        assertEquals(cartPage.getProductFromCart(0),
                "Sauce Labs Backpack",
                "SO BAAAAAD");
        assertTrue(cartPage.getProductsName().contains("Sauce Labs Backpack"));
        assertEquals(cartPage.getProductPrice("Sauce Labs Backpack"), 29.99);
    }
}