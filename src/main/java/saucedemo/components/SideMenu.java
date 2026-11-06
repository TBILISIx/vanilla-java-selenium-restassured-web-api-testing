package saucedemo.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SideMenu {

    private final WebDriverWait wait;

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By menuContainer = By.className("bm-menu-wrap");
    private final By logoutLink = By.id("logout_sidebar_link");


    public SideMenu(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Helper built in open for sidebar/burgermenu button to be visible then click
    // And menu container to appear properly then to click logout
    @Step("Open side menu")
    public void open() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.attributeToBe(menuContainer, "aria-hidden", "false"));
    }

    @Step("Click logout in side menu")
    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

}