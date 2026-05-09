package saucedemo; //tolong diperhatikan di package ini

import core.BaseTest;
import core.DriverManager;
import core.TestUtils;
import saucedemo.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTest extends BaseTest {

    @Test(priority = 3, groups = {"smoke"}, description = "Test successful login")
    public void testLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(config.getProperty("standardUser"), config.getProperty("password"));


        Assert.assertTrue(loginPage.isUserLoggedInSuccessfully(),
                "User should be able to see the Products page after logging in with valid credentials");


        Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory"),
                "User should be redirected to the inventory page after successful login");


        Assert.assertFalse(loginPage.isErrorMessageDisplayed(),
                "User should not see any error message after successful login");
    }

    @Test(priority = 2, description = "Test failed login scenario")
    public void testFailedLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(config.getProperty("failedUser"), config.getProperty("password"));


        SoftAssert softAssert = new SoftAssert();


        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "User should see an error message when login fails");


        softAssert.assertFalse(loginPage.isUserLoggedInSuccessfully(),
                "User should not be able to access the Products page with invalid credentials");


        softAssert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "User should be informed that the account has been locked");


        softAssert.assertAll();
    }


    @DataProvider(name = "loginCredentials", parallel = true)
    public Object[][] loginCredentials() {
        return TestUtils.getTestData("src/test/resources/data/login-test-data.xlsx", "Sheet1");
    }


    @Test(priority = 1, dataProvider = "loginCredentials", description = "Data-driven login test")
    public void testDataDriven(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(username, password);


        SoftAssert softAssert = new SoftAssert();


        if (expectedResult.equalsIgnoreCase("success")) {
            softAssert.assertTrue(loginPage.isUserLoggedInSuccessfully(),
                    "User with username '" + username + "' should be able to login successfully");


            softAssert.assertTrue(loginPage.getCurrentUrl().contains("inventory"),
                    "User should be redirected to the inventory page after successful login");
        } else {
            softAssert.assertTrue(loginPage.isErrorMessageDisplayed(),
                    "User with username '" + username + "' should see an error message");


            softAssert.assertFalse(loginPage.isUserLoggedInSuccessfully(),
                    "User should not be able to access the Products page with invalid credentials");
        }


        softAssert.assertAll();
    }


}

