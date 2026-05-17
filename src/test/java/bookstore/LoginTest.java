package bookstore;

import core.BaseTest;
import core.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import bookstore.LoginPage;

public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test(priority = 1, groups = {"smoke"}, description = "Test successful login")
    public void testLoginSuccess() {

        logger.info("Memulai test login dengan credenrial standard user");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("User login dengan standard user");
        loginPage.login(config.getProperty("standardUser"), config.getProperty("password"));

        logger.info("Verify user sukses login dan melihat welcome message");
        Assert.assertTrue(loginPage.verifySuccessLogin(),
                "User should be redirected to the inventory page after successful login");

        logger.info("TestLoginSuccess sudah dijalankan dengan sukses");

    }

    @Test(priority = 2, groups = {"smoke"}, description = "Test Failed login")
    public void testFailedLogin() {

        logger.info("Memulai test login dengan credenrial standard user");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("User login dengan standard user");
        loginPage.login(config.getProperty("User"), config.getProperty("password"));

        logger.info("Verify user gagal login dan melihat error message");
        Assert.assertTrue(loginPage.verifyFailedLogin(),
                "User should be redirected to the inventory page after successful login");

        logger.info("TestLoginFailed sudah dijalankan dengan sukses");
    }
}
