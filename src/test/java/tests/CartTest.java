package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.pages.InventoryPage;
import saucedemo.pages.LoginPage;

public class CartTest extends BaseTest {

    @Test(description = "Add a product to the cart")
    public void testAddProductToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        InventoryPage inventoryPage = loginPage.loginWithValidCredentials("standard_user", "secret_sauce");

        inventoryPage.getProduct("Sauce Labs Backpack").addToCart();

        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Cart should contain 1 item");
    }

}

