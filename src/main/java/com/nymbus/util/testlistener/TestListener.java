package com.nymbus.util.testlistener;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.nymbus.base.BaseTest.getDriver;

public class TestListener implements ITestListener {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    private byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOG.info("Started test: {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOG.info("Passed test: {}", getTestMethodName(iTestResult));
        doOnTest();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOG.info("Failed test: {}", getTestMethodName(iTestResult));
        doOnTest();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOG.info("Skipped test: {}", getTestMethodName(iTestResult));
        doOnTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOG.info("Test failed but it is in defined success ratio: {}", getTestMethodName(iTestResult));
        doOnTest();
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    private void doOnTest() {
        saveScreenshotPNG(getDriver());
    }
}
