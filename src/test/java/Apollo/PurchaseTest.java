package Apollo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class PurchaseTest {
    WebDriver driver;

    String URL = "https://www.apollo.ee/et";
    String bookName = "Little Prince";
    String storeName = "";

    By declineCookiesButton = By.xpath("//button[contains(text(), 'Ei nõustu')]");
    By searchBar = By.id("header-search-input");
    By searchButton = By.xpath("//span[contains(text(), 'Vaata kõiki')]");

    By bookPageItem = By.xpath("//li[contains(@class, 'styles_product-list__item')]");
    By availabilityButton = By.xpath("//span[contains(text(), 'Saadavus kauplustes')]");
    
    By validatingStore = By.xpath("//p[contains(text(),'Kristiine keskuse Apollo')]");
    By availableStore = By.xpath("//h3[text()='Tallinn']/following-sibling::ul//span[contains(text(), 'Saadaval')]/../../p");

    By storeElementOnList;


    @BeforeClass
    public void setUp(){
        driver = new FirefoxDriver();
        driver.get(URL);
        driver.manage().window().maximize();
    }


    @Test
    public void testPurchaseTest(){

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


        // 4. Check the availability, remember the store

        // 4.1 Click on the Availability Button
        WebElement availabilityElement = wait.until(ExpectedConditions.elementToBeClickable(availabilityButton));
        availabilityElement.click();

        // 4.2 Wait for text "Kristiine keskuse Apollo" to be present, to ensure that the status menu is visible
        WebElement storeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(validatingStore));
        storeElement.getText();

        // 4.3 Remember the store
        storeName = driver.findElement(availableStore).getText();
        System.out.println("Available store: " + storeName);
        storeElementOnList = By.xpath("//div[contains(text(), '" + storeName + "')]");

    }

    public void scrollToJSElement(By locator){
        WebElement element = driver.findElement(searchButton);
        String jsScript = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor)driver).executeScript(jsScript, element);
    }


    @AfterClass
    public void shutDown(){
//        driver.quit(); // Closed for debugging
    }
}

