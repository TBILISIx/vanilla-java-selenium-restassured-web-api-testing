package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // Page-URL
    private static final String PAGE_URL = "https://www.saucedemo.com/inventory.html";

    // Locators
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By burgerMenuContainer = By.className("bm-menu-wrap");
    private final By logOut = By.id("logout_sidebar_link");


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {

        return PAGE_URL.equals(driver.getCurrentUrl());
    }

    // Hunters mark to know the page is open in this case inventory_list that is shown on homepage
    @Override
    public By marker() {
        return By.xpath("//*[@class='inventory_list']");
    }


    // Helper Method for BurgerMenuVisibility
    private void waitForBurgerMenuToOpen() {
        driverWait.until(ExpectedConditions.attributeToBe(burgerMenuContainer, "aria-hidden", "false"));
    }



    // Log Out Method
    public LoginPage logOut() {
        click(burgerMenuButton);
        waitForBurgerMenuToOpen();
        click(logOut);
        return new LoginPage(driver);
    }

}
