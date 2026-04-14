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

    By bookPageItem = By.xpath("//li[contains(@class, 'styles_product-list__item')]");
    By availabilityButton = By.xpath("//span[contains(text(), 'Saadavus kauplustes')]");

    By validatingStore = By.xpath("//p[contains(text(),'Kristiine keskuse Apollo')]");
    By availableStore = By.xpath("//h3[text()='Tallinn']/following-sibling::ul//span[contains(text(), 'Saadaval')]/../../p");

    By closeAvailabilityButton = By.xpath("//button[contains(@class, 'styles_layout-overlay__close')]");
    By addToCartButton = By.xpath("//span[contains(text(), 'Lisa ostukorvi')]");
    By cartButtonBy = By.xpath("//span[contains(text(), 'Ava ostukorv')]");

    By proceedToCheckoutBy = By.xpath("//span[contains(text(), 'Vormista ost')]");

    By chooseStoreButton = By.xpath("//span[contains(text(), 'Apollo kauplused')]");
    By shopList = By.xpath("//input[contains(@aria-controls, 'shop-drop')]");
    By storeElementOnList;
    By continueCheckoutButton = By.xpath("//span[contains(text(), 'Jätka')]");

    By firstNameField = By.id("firstname");
    By lastNameField = By.id("lastname");
    By emailField = By.id("email");
    By telephoneField = By.id("telephone");
    By continueToPaymentButton = By.xpath("//span[contains(text(), 'Edasi')]");



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

        // 4.4 Close availability menu
        driver.findElement(closeAvailabilityButton).click();


        // 5. Add book to Cart
        driver.findElement(addToCartButton).click();

        // 6. Order
        // 6.1 Go to Cart
        WebElement cartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(cartButtonBy));
        cartButton.click();

        // 6.2 Proceed to Checkout
        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckoutBy));
        checkoutButton.click();

        // 6.3 Choose a Store
        WebElement chooseStore = wait.until(ExpectedConditions.visibilityOfElementLocated(chooseStoreButton));
        chooseStore.click();

        driver.findElement(shopList).click();
        driver.findElement(shopList).sendKeys(storeName, Keys.ENTER); // Type in the store name

        driver.findElement(storeElementOnList).click(); // Choose the store
        driver.findElement(continueCheckoutButton).click(); // Continue


        // 7. Fill in the contact form
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        firstName.sendKeys("Jane"); // Waiting for the first input field to be valid. The next fields do not require waiting

        driver.findElement(lastNameField).sendKeys("Doe");
        driver.findElement(emailField).sendKeys("jane.doe@mail.com");
        driver.findElement(telephoneField).sendKeys("+1234567890");

        scrollToJSElement(continueToPaymentButton);
        driver.findElement(continueToPaymentButton).click();

    }

    public void scrollToJSElement(By locator){
        WebElement element = driver.findElement(locator);
        String jsScript = "arguments[0].scrollIntoView();";
        ((JavascriptExecutor)driver).executeScript(jsScript, element);
    }


    @AfterClass
    public void shutDown(){
        driver.quit();
    }
}

