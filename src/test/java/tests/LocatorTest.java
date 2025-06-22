package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.SourceType;
import org.testng.annotations.Test;

public class LocatorTest extends BaseTest {

    @Test(testName = "Проверка всех видов локаторов", groups = {"regression"}, priority = 11)
    public void checkLocator() {
        driver.get("https://www.saucedemo.com/");
        loginPage.open();
        loginPage.login(user, password);
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        driver.findElement(By.name("add-to-cart-sauce-labs-bike-light"));
        driver.findElement(By.className("product_sort_container"));
        driver.findElement(By.tagName("select"));
        driver.findElement(By.linkText("Twitter"));
        driver.findElement(By.partialLinkText("book"));
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        driver.findElement(By.xpath("//*/span[text()='Products']"));
        driver.findElement(By.xpath("//*/button[contains(@id,'burger-menu')]"));
        driver.findElement(By.xpath("//*/a[contains(text(),'Link')]"));
        // Множество предков
        driver.findElement(By.xpath("//a[@id='reset_sidebar_link']/ancestor::nav"));
        driver.findElement(By.xpath("//a[@id='reset_sidebar_link']/ancestor-or-self::a"));
        driver.findElements(By.xpath("//*[@style]"));
        driver.findElement(By.xpath("//div[@class='bm-menu']/child::*"));
        // Множество потомков
        driver.findElement(By.xpath("//div[@class='bm-menu']/descendant::*"));
        driver.findElement(By.xpath("//div[@class='bm-menu']/descendant::a"));
        driver.findElement(By.xpath("//div[@class='bm-menu']/descendant-or-self::*"));
        //Множество следующих за текущим на всех уровнях:
        driver.findElement(By.xpath("//div[@class='inventory_container']/following::*"));
        //Множество следующих за текущим на том же уровне:
        driver.findElement(By.xpath("//div[@id='menu_button_container']/following-sibling::*"));
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']/parent::*"));
        //Множество предшествующих текущему на всех уровнях:
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']/preceding::*"));
        //Множество предшествующих текущему на том же уровне:
        driver.findElement(By.xpath("//div[@id='shopping_cart_container']/preceding-sibling::*"));
        //Возврат текущего элемента
        driver.findElement(By.xpath("//div[@id='shopping_cart_container']/self::*"));
        driver.findElement(By.xpath("//nav[@class='bm-item-list']/node()"));
        driver.findElement(By.xpath("//nav[@class='bm-item-list']/a[last()]"));
        driver.findElement(By.xpath("//nav[@class='bm-item-list']/a[position()=2]"));
        driver.findElement(By.xpath("//nav[count(a)=4]"));
        driver.findElement(By.xpath("id('header_container')"));
        //Вернет текст
        String expression = "string(//div[@class='app_logo'])";
        System.out.println(expression);
        //возвращает string вместо WebElement, поэтому ошибка
        //driver.findElement(By.xpath("string(//div[@class='app_logo'])"));
        //Найдет по объединенному тексту
        driver.findElement(By.xpath("//div[contains(text(), concat('Swag', ' ', 'Labs'))]"));
        //Выбирает теги по длине
        driver.findElement(By.xpath("//*[string-length(name())>=6]"));
        //Логическое или
        driver.findElement(By.xpath("//*[@class='bm-menu' or @class='inventory_item']"));
        //Логическое И
        driver.findElement(By.xpath("//div[button and img]"));
        //Вернет true если есть элемент
        //возвращает true/false вместо WebElement, поэтому ошибка
//      driver.findElement(By.xpath("boolean(//div[button and img])"));

        //CSS
        driver.findElement(By.cssSelector(".title"));
        driver.findElement(By.cssSelector(".bm-item.menu-item"));
        driver.findElement(By.cssSelector(".right_component .select_container"));
        driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack"));
        driver.findElement(By.cssSelector("body"));
        driver.findElement(By.cssSelector("nav.bm-item-list"));
        driver.findElement(By.cssSelector("[class='title']"));
        driver.findElement(By.cssSelector("[class~='btn_small']"));
        driver.findElement(By.cssSelector("[class|='social']"));
        driver.findElement(By.cssSelector("[href^='https']"));
        driver.findElement(By.cssSelector("[href$='.png']"));
        driver.findElement(By.cssSelector("[class*='social']"));
    }
}
