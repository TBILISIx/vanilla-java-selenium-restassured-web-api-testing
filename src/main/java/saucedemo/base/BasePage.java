package saucedemo.base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    protected WebDriver driver;
    protected WebDriverWait driverWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.driverWait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }

    protected void click(By locator) {
        driverWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected abstract By marker();

    public void waitUntilOpened() {
        waitUntilOpened(DEFAULT_TIMEOUT_SECONDS);
    }

    public void waitUntilOpened(int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(marker()));
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    getClass().getSimpleName() + " was not opened within " + seconds + "s. "
                            + "Missing element: " + marker() + ". Current URL: " + driver.getCurrentUrl(), e);
        }
    }

    public boolean isOpened(int seconds) {
        try {
            waitUntilOpened(seconds);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
