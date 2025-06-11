package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.time.Duration;

import static tests.AllureUtils.takeScreenshot;

@Listeners({TestListener.class})
public class BaseTest {
    WebDriver driver;
    SoftAssert softAssert;
    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CompletePage completePage;
    String user = System.getProperty("user");
    String password = System.getProperty("secret_sauce");

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true, description = "Открытие браузера")
    public void setup(@Optional ("chrome") String browser, ITestContext context) {
        if(browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            driver = new ChromeDriver(options);
        } else if(browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

        context.setAttribute("driver", driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        softAssert = new SoftAssert();
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        completePage = new CompletePage(driver);
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(driver);
        }
        driver.quit();
    }
}
