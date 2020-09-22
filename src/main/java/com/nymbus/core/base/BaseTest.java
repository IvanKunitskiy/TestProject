package com.nymbus.core.base;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.allure.AllureLogger;
import com.nymbus.core.config.SelenideConfig;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.UserCredentials;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.util.EmptyStackException;

@Listeners({TestListener.class})
public class BaseTest extends AllureLogger {

    protected UserCredentials userCredentials;


    @BeforeMethod(alwaysRun = true, description = "Opening web browser...")
    public void setUp() {
        logInfo("Creating web driver configuration...");
        SelenideConfig.createBrowserConfig(System.getProperty("selenide.browser", "chrome"));

        logInfo("Open browser...");
        Selenide.open(Constants.URL);
        userCredentials = Constants.USERS.pop();

        Constants.USERNAME = userCredentials.getUserName();
        Constants.PASSWORD = userCredentials.getPassword();
        Constants.FIRST_NAME = userCredentials.getUserName();
        Constants.LAST_NAME = userCredentials.getUserName();

    }

    @AfterMethod(alwaysRun = true, description = "Closing web browser...")
    public void tearDown() {
        Constants.USERS.add(userCredentials);
        Selenide.closeWebDriver();
        logInfo("Web driver closed!");
    }
}
