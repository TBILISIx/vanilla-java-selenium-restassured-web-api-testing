package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import basepage.BasePage;
import saucedemo.components.ProductCard;
import saucedemo.components.SideMenu;

public class InventoryPage extends BasePage {

    // Locators
    private final By productCards = By.className("inventory_item");
    private final By cartBadge = By.className("shopping_cart_badge");

    // Components (Composition principle like AbstractIUObject in Carina)
    private final SideMenu sideMenu;

    public InventoryPage(WebDriver driver) {
        super(driver);

        // Creating a SideMenu object and passing it the SAME browser driver.
        //"Create a SideMenu component and give it access to the browser I'm already using."
        this.sideMenu = new SideMenu(driver);
    }

    // Hunters mark to know the page is open in this case inventory_list that is shown on homepage
    @Override
    protected By marker() {
        return By.xpath("//*[@class='inventory_list']");
    }

    // Method to get product with exact name
    public ProductCard getProduct(String name) {
        return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards))
                .stream()
                .map(ProductCard::new)
                .filter(card -> card.getName().equals(name))

                // we can use here .toList() to return all matching names of products .findFirst() returns only first.
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + name));
    }

    public int getCartCount() {
        return Integer.parseInt(getText(cartBadge));
    }

    // Log Out Method
    public LoginPage logOut() {
        sideMenu.open();
        sideMenu.clickLogout();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilOpened();
        return loginPage;
    }

}
