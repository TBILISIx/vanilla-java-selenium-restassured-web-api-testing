package saucedemo.testsui;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import saucedemo.pages.InventoryPage;
import saucedemo.pages.LoginPage;

public class CartTest extends BaseTest {

    @Test(description = "Add a product to the cart")
    public void testAddProductToCart() {

        // Creating, Opening and Storing Best practice
        LoginPage loginPage = initializePage(LoginPage.class).open();

        InventoryPage inventoryPage = loginPage.loginWithValidCredentials("standard_user", "secret_sauce");

        inventoryPage.getProduct("Sauce Labs Backpack").addToCart();

        Assert.assertEquals(inventoryPage.getCartCount(), 1, "Cart should contain 1 item");
    }

}

