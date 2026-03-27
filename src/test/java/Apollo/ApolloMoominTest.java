package Apollo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ApolloMoominTest {

    WebDriver driver;

    String URL = "https://www.apollo.ee/et";
    String bookName = "Moomin";

    By declineCookiesButton = By.xpath("//button[contains(text(), 'Ei nõustu')]");


        @BeforeClass
        public void setUp(){
            driver = new FirefoxDriver();
            driver.get(URL);
            driver.manage().window().maximize();
        }


        @Test
        public void testMoomin(){

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // 1. Decline cookies
            WebElement declineCookiesElement = wait.until(ExpectedConditions.visibilityOfElementLocated(declineCookiesButton));
            declineCookiesElement.click();


        }
}
