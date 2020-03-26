package com.nymbus.core.base;

import com.codeborne.selenide.*;
import com.nymbus.core.allure.AllureLogger;
import com.nymbus.core.utils.LocatorParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PageTools extends AllureLogger {

    private static String getPreviousMethodNameAsText() {
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        String replacedMethodName = methodName.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
        return replacedMethodName.substring(0, 1).toUpperCase() + replacedMethodName.substring(1).toLowerCase();
    }

    private By byLocator(By by, Object... args) {
        return LocatorParser.parseLocator(by, args);
    }

    protected SelenideElement getSelenideElement(By by, Object... args) {
        return $(byLocator(by, args));
    }

    protected Actions getActions() {
        return Selenide.actions();
    }

    /**
     * Should be
     */
    protected ElementsCollection shouldBe(CollectionCondition condition, By by, Object... args) {
        return $$(byLocator(by, args)).shouldBe(condition);
    }

    protected SelenideElement shouldBe(Condition condition, By by, Object... args) {
        return $(byLocator(by, args)).shouldBe(condition);
    }

    /**
     * Main Actions
     */
    protected void click(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        shouldBe(Condition.visible, by, args).click();
    }

    protected void jsClick(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        Selenide.executeJavaScript("arguments[0].click();", shouldBe(Condition.exist, by, args));
    }

    protected void actionClick(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        Actions builder = getActions();
        builder.moveToElement(getWebElement(byLocator(by, args))).click();
        builder.perform();
    }

    protected void type(String text, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + " '" + text + "', element --> " + byLocator(by, args));
        wipeText(by, args);
        shouldBe(Condition.visible, by, args).append(text);
    }

    protected void uploadFile(String filePath, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + " '" + filePath + "', element --> " + byLocator(by, args));
        wipeText(by, args);
        shouldBe(Condition.enabled, by, args).uploadFile(new File(filePath));
    }

    protected void typeWithoutWipe(String text, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + " '" + text + "', element --> " + byLocator(by, args));
        shouldBe(Condition.visible, by, args).append(text);
    }

    protected void wipeText(By by, Object... args) {
        int stringSize = shouldBe(Condition.enabled, by, args).getWrappedElement().getAttribute("value").length();
        for (int i = 0; i < stringSize; i++) {
            shouldBe(Condition.enabled, by, args).sendKeys(Keys.BACK_SPACE);
        }
    }

    protected void typeIntoFrame(String text, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + " '" + text + "', element --> " + byLocator(by, args));
        shouldBe(Condition.visible, by, args).clear();
        shouldBe(Condition.visible, by, args).sendKeys(text);
    }

    protected void selectOption(String option, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + " --> " + option + ", element --> " + byLocator(by, args));
        shouldBe(Condition.visible, by, args).selectOption(option);
    }

    protected void mouseHover(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        shouldBe(Condition.visible, by, args).hover();
    }

    protected void clickEnterButton() {
        getActions().sendKeys(Keys.ENTER).perform();

    }

    protected void waitForElementVisibility(By by, Object... args) {
        shouldBe(Condition.visible, by, args);
    }

    protected void waitForElementInvisibility(By by, Object... args) {
        shouldBe(Condition.hidden, by, args);
    }

    protected void waitForElementClickable(By by, Object... args) {
        shouldBe(Condition.visible, by, args);
        shouldBe(Condition.enabled, by, args);
    }

    /**
     * Is condition
     */
    /*Working without wait*/
    protected boolean isCondition(Condition condition, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", condition --> " + condition.getName() + ", element --> " + byLocator(by, args));
        return getSelenideElement(by, args).is(condition);
    }

    /*Working with wait*/
    protected boolean isElementVisible(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        return isCondition(Condition.visible, by, args);
    }

    /**
     * Getters
     */
    protected String getElementText(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        return shouldBe(Condition.enabled, by, args).text();
    }

    protected String getElementAttributeValue(String attr, By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", element --> " + byLocator(by, args));
        return shouldBe(Condition.visible, by, args).attr(attr);
    }

    protected List<SelenideElement> getElements(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", elements --> " + byLocator(by, args));
        return shouldBe(sizeGreaterThan(0), by, args);
    }

    protected List<String> getElementsText(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", elements --> " + byLocator(by, args));
        return shouldBe(sizeGreaterThan(0), by, args).texts();
    }

    protected void scrollToElement(By by, Object... args) {
        logInfo(getPreviousMethodNameAsText() + ", elements --> " + byLocator(by, args));
        waitForElementVisibility(by);
//        getSelenideElement(by, args).scrollTo();
        Selenide.executeJavaScript("arguments[0].scrollIntoView();", getWebElement(by, args));
    }

    protected WebElement getWebElement(By by, Object... args){
        return WebDriverRunner.getWebDriver().findElement(byLocator(by, args));
    }
}
