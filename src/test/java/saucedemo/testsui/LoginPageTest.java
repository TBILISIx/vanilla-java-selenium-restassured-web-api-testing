package saucedemo.testsui;

import basetest.BaseTest;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import saucedemo.pages.InventoryPage;
import saucedemo.pages.LoginPage;

public class LoginPageTest extends BaseTest {

    @Test(description = "This is test for SuccessfulLogin Path with correct username and password")
    public void testSuccessfulLogin() {

        // Creating, Opening and Storing Best practice below the old one
        LoginPage loginPage = initializePage(LoginPage.class).open();


        // Login with valid credentials
        InventoryPage inventoryPage = loginPage.loginWithValidCredentials("standard_user", "secret_sauce");

        // Verify that HomePage is opened
        Assert.assertTrue(
                inventoryPage.isOpened(3), "HomePage was not opened after successful login!"
        );

    }

    // DataProvider implementation

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                {"Greetings_Neo o_^ ", "WAKE_UP_NEO", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"},
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "This is test for UnSuccessfulLogin Path with Wrong username and password")
    public void testUnSuccessfulLogin(String username, String password, String expectedError) {

        SoftAssert softAssert = new SoftAssert();

        // Old one, object creation first then on that object using method.
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.open();

        // Login with Invalid credentials
        loginPage.loginWithInvalidCredentials(username, password);

        // Verify that we get error text on unsuccessful login

        softAssert.assertEquals(
                loginPage.getErrorText(), expectedError);

        softAssert.assertTrue(
                loginPage.isOpened(3), "In case of unsuccessful login, the LoginPage should be open"
        );

        // Assert All
        softAssert.assertAll();

    }

    @Test(description = "Successful LogIn And LogOut")

    public void testLogInLogOut() {

        System.out.println(Thread.currentThread().getName() + " -> " + ((RemoteWebDriver) getDriver()).getSessionId());

        // Creating, Opening and Storing Best practice
        LoginPage loginPage = initializePage(LoginPage.class).open();

        // Login with valid credentials
        InventoryPage inventoryPage = loginPage.loginWithValidCredentials("standard_user", "secret_sauce");

        // Verify that HomePage is opened
        Assert.assertTrue(
                inventoryPage.isOpened(3), "HomePage was not opened after successful login!"
        );

        // Log out
        loginPage = inventoryPage.logOut();

        Assert.assertTrue(
                loginPage.isOpened(3), "Login page was not opened after logout!"
        );
    }

    @Test(description = "Failed login followed by successful login in the same session")
    public void testFailedThenSuccessfulLogin() {

        // Creating, Opening and Storing Best practice
        LoginPage loginPage = initializePage(LoginPage.class).open();

        loginPage.loginWithInvalidCredentials("Greetings_Neo o_^ ", "WAKE_UP_NEO");
        InventoryPage inventoryPage = loginPage.loginWithValidCredentials("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isOpened(3), "HomePage was not opened after correcting credentials!");

    }

}
