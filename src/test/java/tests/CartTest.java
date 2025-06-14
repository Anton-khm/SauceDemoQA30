package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartTest extends BaseTest {

    @Test(testName = "Проверка добавления товара в корзину", groups = {"smoke"}, priority = 1)
    @Epic("Корзина")
    @Feature("Добавление товара")
    @Story("Отображение товара в корзине")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Разработчик")
    @Description("Проверка добавления товара в корзину")
    @Flaky
    @Link(name="Документация", url = "https://www.saucedemo.com/")
    @TmsLink("TMS_T10")
    @Issue("TMS_11")
    public void checkCart() {
        loginPage.open()
                .isPageOpened()
                .login("standard_user", "secret_sauce");
        productsPage.isPageOpened()
                .addToCart("Sauce Labs Backpack")
                .goToCart();
        assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"),
                "SO BAAAAD");
        assertEquals(cartPage.getProductFromCart(0),
                "Sauce Labs Backpack",
                "SO BAAAAAD");
        assertTrue(cartPage.getProductsName().contains("Sauce Labs Backpack"));
        assertEquals(cartPage.getProductPrice("Sauce Labs Backpack"), 29.99);
    }
}