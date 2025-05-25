package tests;

import io.qameta.allure.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutTest extends BaseTest{
    @Test(testName = "Проверка на видимость кнопок Cancel и Continue", groups = {"smoke"}, priority = 2)
    public void checkIfButtonsAreVisible() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        assertTrue(checkoutPage.isCancelButtonPresent());
        assertTrue(checkoutPage.isContinueButtonPresent());
    }

    @Test(testName = "Проверка на корректность информации о доставке", groups = {"smoke"}, priority = 3)
    @Epic("Корзина")
    @Feature("Оформление заказа")
    @Story("Отображение информации о доставке")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Разработчик")
    @Description("Проверка отображения корректной информации о доставке")
    @Link(name="Документация", url = "https://www.saucedemo.com/")
    @TmsLink("TMS_T1")
    @Issue("TMS_1")
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

    @Test(testName = "Проверка на отображение сообщения об ошибке при заполнении формы чекаут с пустым Именем",
            groups = {"smoke"},
            priority = 4)
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

    @Test(testName = "Проверка на отображение сообщения об ошибке при заполнении формы чекаут с пустым Индексом",
            groups = {"smoke"},
            priority = 5)
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

    @Test(testName = "Проверка успешного чекаута",
            groups = {"smoke"},
            priority = 6)
    public void checkCompleteCheckout() {
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.goToCart();
        cartPage.goToCheckout();
        checkoutPage.checkout("Test", "Test", "123456");
        cartPage.goToFinishPage();
        assertEquals(completePage.checkSuccessOrderMessage(), "Thank you for your order!", "SO BAAAAAD");
    }

    @Test(testName = "Проверка сортировки продуктов по имени от A до Z",
            groups = {"smoke"},
            priority = 7)
    public void checkSortingByNameASC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductNames("az");
        productsPage.chooseSorting("az");
        assertTrue(productsPage.areNamesCorrectlySorted("az"));
    }

    @Test(testName = "Проверка сортировки продуктов по имени от Z до A",
            groups = {"smoke"},
            priority = 8)
    public void checkSortingByNameDESC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductNames("za");
        productsPage.chooseSorting("za");
        assertTrue(productsPage.areNamesCorrectlySorted("za"));
    }

    @Test(testName = "Проверка сортировки продуктов по цене по возрастанию",
            groups = {"smoke"},
            priority = 9)
    public void checkSortingByPriceASC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductPrices("lohi");
        productsPage.chooseSorting("lohi");
        assertTrue(productsPage.arePricesCorrectlySorted("lohi"));
    }

    @Test(testName = "Проверка сортировки продуктов по цене по убыванию",
            groups = {"smoke"},
            priority = 10)
    public void checkSortingByPriceDESC(){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductPrices("hilo");
        productsPage.chooseSorting("hilo");
        assertTrue(productsPage.arePricesCorrectlySorted("hilo"));
    }

    @DataProvider
    public Object[][] sortingDataNames(){
        return new Object[][]{
                {"az"},
                {"za"}
        };
    }

    @DataProvider
    public Object[][] sortingDataPrices(){
        return new Object[][]{
                {"lohi"},
                {"hilo"}
        };
    }

    @Test(testName = "Проверка сортировки по имени", dataProvider = "sortingDataNames")
    public void sortingTest(String sorting){
            loginPage.open();
            loginPage.login("standard_user", "secret_sauce");
            productsPage.getInitialProductNames(sorting);
            productsPage.chooseSorting(sorting);
            assertTrue(productsPage.areNamesCorrectlySorted(sorting));
    }

    @Test(testName = "Проверка сортировки по цене", dataProvider = "sortingDataPrices")
    public void sortingTest2(String sorting){
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        productsPage.getInitialProductPrices(sorting);
        productsPage.chooseSorting(sorting);
        assertTrue(productsPage.arePricesCorrectlySorted(sorting));
    }
}
