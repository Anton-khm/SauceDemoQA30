package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CompletePage extends BasePage{
    public CompletePage(WebDriver driver) {
        super(driver);
    }

    public String checkSuccessOrderMessage(){
        return driver.findElement(By.cssSelector("[data-test='complete-header']")).getText();
    }
}
