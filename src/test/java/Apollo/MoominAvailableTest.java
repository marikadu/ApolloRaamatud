package Apollo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class MoominAvailableTest {

    WebDriver driver;

    String URL = "https://www.apollo.ee/et";
    String bookName = "Moomin";

    By declineCookiesButton = By.xpath("//button[contains(text(), 'Ei nõustu')]");
    By searchBar = By.id("header-search-input");
    By searchButton = By.xpath("//span[contains(text(), 'Vaata kõiki')]");

    By bookPageItem = By.xpath("//li[contains(@class, 'styles_product-list__item')]");
    By availabilityButton = By.xpath("//span[contains(text(), 'Saadavus kauplustes')]");

    By availabilityStatus = By.xpath("//span[contains(text(), 'Läbi müüdud!')]"); // Gets the first element (Online Store)
    By store = By.xpath("//p[contains(text(),'Kristiine keskuse Apollo')]");

        @BeforeClass
        public void setUp(){
            driver = new FirefoxDriver();
            driver.get(URL);
            driver.manage().window().maximize();
        }


        @Test
        public void testFirstMoominBookAvailable(){

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. Decline cookies
            WebElement declineCookiesElement = wait.until(ExpectedConditions.visibilityOfElementLocated(declineCookiesButton));
            declineCookiesElement.click();


            // 2. Find a book
            driver.findElement(searchBar).click();
            driver.findElement(searchBar).sendKeys(bookName, Keys.ENTER);

            // 3. Open the book page
            scrollToJSElement(bookPageItem);
            WebElement bookElement = wait.until(ExpectedConditions.elementToBeClickable(bookPageItem));
            bookElement.click();


            // 4. Check the availability

            // 4.1 Click on the Availability Button
            WebElement availabilityElement = wait.until(ExpectedConditions.elementToBeClickable(availabilityButton));
            availabilityElement.click();

            // 4.2 Wait for text "Kristiine keskuse Apollo" to be present, to ensure that the status menu is visible
            WebElement storeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(store));
            storeElement.getText();

            // 4.3 Check the status
            Assert.assertTrue(isAvailable(availabilityStatus), "\n The book is not available.");
            // Actual "false" -> book is not available online
            // Actual "true" -> book is available online
        }

        public void scrollToJSElement(By locator){
            WebElement element = driver.findElement(searchButton);
            String jsScript = "arguments[0].scrollIntoView();";
            ((JavascriptExecutor)driver).executeScript(jsScript, element);
        }

        boolean isAvailable(By by) {
            try {
                driver.findElement(by);
                return false; // If the element "Not Available" is present for the online store, return false ->
                // -> the book is not available online
            } catch (NoSuchElementException e) {
                return true;
            }
        }

        @AfterClass
        public void shutDown(){
            driver.quit();
        }
}
