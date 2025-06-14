package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CompletePage extends BasePage{
    public CompletePage(WebDriver driver) {
        super(driver);
    }

    public String checkSuccessOrderMessage(){
        return driver.findElement(By.cssSelector("[data-test='complete-header']")).getText();
    }

    @Override
    public CompletePage open(){
        driver.get(BASE_URL + "/checkout-step-two.html");
        return this;
    }

    @Override
    public CompletePage isPageOpened(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        return this;
    }
}
