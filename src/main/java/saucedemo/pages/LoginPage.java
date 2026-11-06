package saucedemo.pages;

import basepage.BasePage;
import config.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // Page-URL

    private static final String PAGE_URL = Configuration.getRequired("base_url");

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorText = By.xpath("//*[@data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /*
     * Opens the page and returns it, so calls can be chained.
     *
     * With a void method, the page object is created first and used on the next line:
     *
     *     LoginPage loginPage = initPage(LoginPage.class);
     *     loginPage.open();
     *
     * Because this method returns "this" (the same page object), creating, opening
     * and storing the page can happen in one line:
     *
     *     LoginPage loginPage = initPage(LoginPage.class).open();
     *
     * No new object is created. open() hands back the page it was called on,
     * and the variable loginPage points to it.
     *
     * Both styles work. The one-line style is just shorter and easier to read.
     */
    @Step("Open login page")
    public LoginPage open() {
        driver.get(PAGE_URL);
        waitUntilOpened();
        return this;
    }

    // Hunters mark to know the page is open in this case login button - no login without login button
    @Override
    protected By marker() {
        return loginButton;
    }

    @Step("Log in with valid credentials as '{0}'")
    // Successful login path
    public InventoryPage loginWithValidCredentials(String username, String password) {
        submitLoginForm(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitUntilOpened();
        return inventoryPage;
    }

    @Step("Log in with invalid credentials as '{0}'")
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

    @Step("Read login error message")
    // Gets error text after invalid login / password
    public String getErrorText() {
        return getText(errorText);
    }

}
