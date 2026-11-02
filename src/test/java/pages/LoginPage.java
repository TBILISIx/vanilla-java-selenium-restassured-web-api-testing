package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
    }

    // Hunters mark to know the page is open in this case login button - no login without login button
    @Override
    public By marker() {
        return loginButton;
    }

    public HomePage login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);

        return new HomePage(driver);
    }

    // Gets error text after invalid login / password
    public String getErrorText() {
        return getText(errorText);
    }

}
