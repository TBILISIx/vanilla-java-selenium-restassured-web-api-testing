package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.base.BasePage;

public class LoginPage extends BasePage {

    // Page-URL

    private static final String PAGE_URL = "https://www.saucedemo.com";

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorText = By.xpath("//*[@data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Open site (SauceDemo)
    public void open() {
        driver.get(PAGE_URL);
        waitUntilOpened();
    }

    // Hunters mark to know the page is open in this case login button - no login without login button
    @Override
    protected By marker() {
        return loginButton;
    }

    // Successful login path
    public InventoryPage loginWithValidCredentials(String username, String password) {
        submitLoginForm(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitUntilOpened();
        return inventoryPage;
    }

    // Unsuccessful login path
    public LoginPage loginWithInvalidCredentials(String username, String password) {
        submitLoginForm(username, password);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(errorText));
        return this;
    }

    private void submitLoginForm(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    // Gets error text after invalid login / password
    public String getErrorText() {
        return getText(errorText);
    }

}
