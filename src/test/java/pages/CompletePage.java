package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

@Log4j2
public class CompletePage extends BasePage{
    public CompletePage(WebDriver driver) {
        super(driver);
    }

    public String checkSuccessOrderMessage(){
        log.info("Checking success order message");
        return driver.findElement(By.cssSelector("[data-test='complete-header']")).getText();
    }

    @Override
    public CompletePage open(){
        log.info("Opening CompletePage");
        driver.get(BASE_URL + "/checkout-step-two.html");
        return this;
    }

    @Override
    public CompletePage isPageOpened(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        }catch (TimeoutException e){
            log.error(e.getMessage());
            Assert.fail("CompletePage isn't opened");
        }
        return this;
    }
}
