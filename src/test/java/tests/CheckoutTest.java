package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutTest extends BaseTest {
    @Test(testName = "Проверка на видимость кнопок Cancel и Continue", groups = {"smoke"}, priority = 2)
    public void checkIfButtonsAreVisible() {
        checkoutStart.goToCheckout("standard_user", "secret_sauce", "Sauce Labs Backpack");
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
    @Link(name = "Документация", url = "https://www.saucedemo.com/")
    @TmsLink("TMS_T1")
    @Issue("TMS_1")
    public void checkSuccessDeliveryInfo() {
        checkoutStart.goToCheckout(user, password, "Sauce Labs Backpack");
        checkoutFinish.checkoutFinish("Test", "Test", "123456");
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
        checkoutStart.goToCheckout(user, password, "Sauce Labs Backpack");
        checkoutFinish.checkoutFinish("", "Test", "234344");
        assertEquals(checkoutPage.getErrorMessage(),
                "Error: First Name is required",
                "SO BAAAAAD");
    }

    @Test(testName = "Проверка на отображение сообщения об ошибке при заполнении формы чекаут с пустым Индексом",
            groups = {"smoke"},
            priority = 5)
    public void checkErrorIfPostalEmpty() {
        checkoutStart.goToCheckout(user, password, "Sauce Labs Backpack");
        checkoutFinish.checkoutFinish("Test", "Test", "");
        assertEquals(checkoutPage.getErrorMessage(),
                "Error: Postal Code is required",
                "SO BAAAAAD");
    }

    @Test(testName = "Проверка успешного чекаута",
            groups = {"smoke"},
            priority = 6)
    public void checkCompleteCheckout() {
        checkoutStart.goToCheckout(user, password, "Sauce Labs Backpack");
        checkoutFinish.checkoutFinish("Test", "Test", "123456");
        cartPage.goToFinishPage();
        assertEquals(completePage.checkSuccessOrderMessage(), "Thank you for your order!", "SO BAAAAAD");
    }

    @Test(testName = "Проверка сортировки продуктов по имени от A до Z",
            groups = {"smoke"},
            priority = 7)
    public void checkSortingByNameASC() {
        loginStep.auth(user, password);
        sortingStep.sortNames("az");
        assertTrue(sortingStep.areNamesSortedCorrectly("az"));
    }

    @Test(testName = "Проверка сортировки продуктов по имени от Z до A",
            groups = {"smoke"},
            priority = 8)
    public void checkSortingByNameDESC() {
        loginStep.auth(user, password);
        sortingStep.sortNames("za");
        assertTrue(sortingStep.areNamesSortedCorrectly("za"));
    }

    @Test(testName = "Проверка сортировки продуктов по цене по возрастанию",
            groups = {"smoke"},
            priority = 9)
    public void checkSortingByPriceASC() {
        loginStep.auth(user, password);
        sortingStep.sortPrices("lohi");
        assertTrue(sortingStep.arePricesSortedCorrectly("lohi"));
    }

    @Test(testName = "Проверка сортировки продуктов по цене по убыванию",
            groups = {"smoke"},
            priority = 10)
    public void checkSortingByPriceDESC() {
        loginStep.auth(user, password);
        sortingStep.sortPrices("hilo");
        assertTrue(sortingStep.arePricesSortedCorrectly("hilo"));
    }

    @DataProvider
    public Object[][] sortingDataNames() {
        return new Object[][]{
                {"az"},
                {"za"}
        };
    }

    @DataProvider
    public Object[][] sortingDataPrices() {
        return new Object[][]{
                {"lohi"},
                {"hilo"}
        };
    }

    @Test(testName = "Проверка сортировки по имени", dataProvider = "sortingDataNames")
    public void sortingTest(String sorting) {
        loginStep.auth(user, password);
        sortingStep.sortNames(sorting);
        assertTrue(sortingStep.areNamesSortedCorrectly(sorting));
    }

    @Test(testName = "Проверка сортировки по цене", dataProvider = "sortingDataPrices")
    public void sortingTest2(String sorting) {
        loginStep.auth(user, password);
        sortingStep.sortPrices(sorting);
        assertTrue(sortingStep.arePricesSortedCorrectly(sorting));
    }
}
