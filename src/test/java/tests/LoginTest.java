package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(description = "This is test for SuccessfulLogin Path with correct username and password")
    public void testSuccessfulLogin() {

        // Create Login Page
        LoginPage loginPage = new LoginPage(driver);

        // Open SauceDemo
        loginPage.open();

        // Login with valid credentials
        HomePage homePage = loginPage.login("standard_user", "secret_sauce");

        // Verify that HomePage is opened
        Assert.assertTrue(
                homePage.isOpened(3), "HomePage was not opened after successful login!"
        );

    }

    @Test(description = "This is test for UnSuccessfulLogin Path with Wrong username and password")
    public void testUnSuccessfulLogin() {

        SoftAssert softAssert = new SoftAssert();

        // Create Login Page
        LoginPage loginPage = new LoginPage(driver);

        // Open SauceDemo
        loginPage.open();

        // Login with valid credentials
        HomePage homePage = loginPage.login("Greetings_Neo o_^ ", "WAKE_UP_NEO");

        // Verify that we get error text on unsuccessful login

        softAssert.assertEquals(
                loginPage.getErrorText(), "Epic sadface: Username and password do not match any user in this service");

        // Verify that HomePage is not opened
        softAssert.assertFalse(
                homePage.isOpened(3), "HomePage still opened after unsuccessful login!"
        );

        softAssert.assertTrue(
                loginPage.isOpened(3), "In case of unsuccessful login, the LoginPage should be open"
        );

        // Assert All
        softAssert.assertAll();

    }

    @Test(description = "Successful LogIn And LogOut")

    public void testLogInLogOut() {

        // Create Login Page
        LoginPage loginPage = new LoginPage(driver);

        // Open SauceDemo
        loginPage.open();

        // Login with valid credentials
        HomePage homePage = loginPage.login("standard_user", "secret_sauce");

        // Verify that HomePage is opened
        Assert.assertTrue(
                homePage.isOpened(3), "HomePage was not opened after successful login!"
        );

        // Log out
        loginPage = homePage.logOut();

        Assert.assertTrue(
                loginPage.isOpened(3), "Login page was not opened after logout!"
        );
    }

}
