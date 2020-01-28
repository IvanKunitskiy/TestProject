package com.nymbus.base;

import com.nymbus.locator.Locator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);
    private static final int WAIT_TIMEOUT = 60;

    /*
     * General actions
     */

    private Actions getActions() {
        return new Actions(BaseTest.getDriver());
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(BaseTest.getDriver(), WAIT_TIMEOUT);
    }

    private WebDriverWait getWait(int seconds) {
        return new WebDriverWait(BaseTest.getDriver(), seconds);
    }

    public void deleteCookies() {
        LOG.info("delete cookies");
        BaseTest.getDriver().manage().deleteAllCookies();
    }

    public void refreshPage() {
        LOG.info("refresh web page");
        BaseTest.getDriver().navigate().refresh();
    }

    protected void switchToFrame(Locator frameName, Object... args) {
        LOG.info("switch to iframe '{}'", frameName);
        BaseTest.getDriver().switchTo().frame(getElement(frameName, args));
    }

    public void switchToDefaultContent() {
        LOG.info("switch to default content");
        BaseTest.getDriver().switchTo().defaultContent();
    }

    public void switchToTab(String tabName) {
        LOG.info("switch to tab name '{}'", tabName);
        ArrayList<String> tab = new ArrayList<>(BaseTest.getDriver().getWindowHandles());
        ArrayList<String> tabList = new ArrayList<>();
        for (int i = 0; i < tab.size(); i++) {
            tabList.add(i, BaseTest.getDriver().switchTo().window(tab.get(i)).getTitle());
            if (tabList.get(i).contains(tabName)) {
                BaseTest.getDriver().switchTo().window(tab.get(i));
                break;
            }
        }
    }

    public void switchToLastTab() {
        LOG.info("switch to last tab");
        ArrayList<String> tab = new ArrayList<>(BaseTest.getDriver().getWindowHandles());
        BaseTest.getDriver().switchTo().window(tab.get(tab.size() - 1));
    }

    public void switchToTab(int tabNumber) {
        LOG.info("switch to last tab '{}'", tabNumber);
        ArrayList<String> tab = new ArrayList<>(BaseTest.getDriver().getWindowHandles());
        BaseTest.getDriver().switchTo().window(tab.get(tabNumber));
    }

    /*
     * Work with elements
     */
    protected String getFilePath(String fileName) {
        LOG.info("upload file {}, from src/main/resources/", fileName);
        return new File("src/main/resources/" + fileName).getAbsolutePath();
    }

    protected WebElement getElement(Locator locator, Object... args) {
        return BaseTest.getDriver().findElement(locator.getLocator(args));
    }

    protected List<WebElement> getElements(Locator locator, Object... args) {
        return BaseTest.getDriver().findElements(locator.getLocator(args));
    }

    protected String getElementAttributeValue(String attributeName, Locator locator, Object... args) {
        LOG.info("get from '{}' element, attribute '{}'", locator, attributeName);
        return getElement(locator, args).getAttribute(attributeName);
    }

    protected String getElementCssValue(String cssValue, Locator locator, Object... args) {
        LOG.info("get from '{}' element, css value '{}'", locator, cssValue);
        return getElement(locator, args).getCssValue(cssValue);
    }

    protected String getElementText(Locator locator, Object... args) {
        LOG.info("get from text '{}' element", locator);
        return getElement(locator, args).getText();
    }

    protected List<String> getElementsText(Locator locator, Object... args) {
        LOG.info("get text from '{}' element", locator);
        List<String> elementsText = new ArrayList<>();
        List<WebElement> elements = getElements(locator, args);
        for (WebElement element : elements) {
            elementsText.add(element.getText().trim());
        }
        return elementsText;
    }

    /*
     * Input fields and text areas
     */
    protected void type(String value, Locator locator, Object... args) {
        LOG.info("type text '{}' to element '{}'", value, locator);
        WebElement inputElement = getElement(locator, args);
        inputElement.clear();
        inputElement.clear();
        inputElement.sendKeys(value);
    }

    public void wipeText(Locator locator, Object... args) {
        WebElement inputElement = getElement(locator, args);
        inputElement.clear();
        inputElement.clear();
    }

    protected void typeWithoutWipe(String value, Locator locator, Object... args) {
        LOG.info("type text '{}' to element '{}'", value, locator);
        WebElement inputElement = getElement(locator, args);
        inputElement.sendKeys(value);
    }

    protected boolean isElementEditable(Locator locator, Object... args) {
        LOG.info("check is element '{}' editable", locator);
        WebElement element = getElement(locator, args);
        try {
            element.clear();
            element.sendKeys("Test");
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    protected String getTextFromInput(Locator locator, Object... args) {
        return getElementAttributeValue("value", locator, args);
    }

    protected void submit(Locator locator, Object... args) {
        LOG.info("press submit '{}' element", locator);
        getElement(locator, args).submit();
    }

    /*
     * Checkboxes
     */
    protected boolean isCheckboxChecked(Locator locator, Object... args) {
        LOG.info("check is element '{}' checked", locator);
        return getElement(locator, args).isSelected();
    }

    /*
     * Clicks
     */
    protected void click(Locator locator, Object... args) {
        LOG.info("click on element '{}'", locator);
        getElement(locator, args).click();
    }

    protected void jsClick(Locator locator, Object... args) {
        LOG.info("click on element '{}'", locator);
        JavascriptExecutor executor = (JavascriptExecutor) BaseTest.getDriver();
        executor.executeScript("arguments[0].click();", getElement(locator, args));
    }

    protected void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) BaseTest.getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    /*
     * Count elements
     */
    private int getElementsCount(Locator locator, Object... args) {
        return getElementsCountWithWait(0, locator, args);
    }

    private int getElementsCountWithWait(int waitInSeconds, Locator locator, Object... args) {
        BaseTest.getDriver().manage().timeouts().implicitlyWait(waitInSeconds, TimeUnit.SECONDS);
        return getElements(locator, args).size();
    }

    protected boolean isElementDisplayed(Locator locator, Object... args) {
        LOG.info("check is element '{}' displayed", locator.toString(args));
        return (getElementsCount(locator, args) > 0);
    }

    protected boolean isElementNotDisplayed(Locator locator, Object... args) {
        LOG.info("check is element '{}' not displayed", locator.toString());
        return !(getElementsCount(locator, args) > 0);
    }

    protected boolean isElementDisplayedWithWait(int waitInSeconds, Locator locator, Object... args) {
        LOG.info("check is element '{}' displayed", locator.toString());
        return (getElementsCountWithWait(waitInSeconds, locator, args) > 0);
    }

    protected boolean isElementVisible(Locator locator, Object... args) {
        LOG.info("check is element '{}' visible", locator.toString());
        return isElementVisibleWithWait(0, locator, args);
    }

    protected boolean isElementNotVisible(Locator locator, Object... args) {
        LOG.info("check is element '{}' not visible", locator.toString());
        return !isElementVisibleWithWait(0, locator, args);
    }

    protected boolean isElementVisibleWithWait(int waitInSeconds, Locator locator, Object... args) {
        try {
            getWait(waitInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator.getLocator(args)));
        } catch (Throwable th) {
            return false;
        }
        return true;
    }

    /*
     * Element waits
     */
    protected WebElement waitIsElementVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitIsElementClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementClickable(Locator locator, Object... args) {
        getWait().until(ExpectedConditions.elementToBeClickable(locator.getLocator(args)));
    }

    protected void waitForElementVisibility(Locator locator, Object... args) {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(locator.getLocator(args)));
    }

    protected void waitForElementInvisibility(Locator locator, Object... args) {
        waitForElementInvisibilityWithWait(60, locator, args);
    }

    protected void waitForElementInvisibilityWithWait(int waitInSecondsBefore, Locator locator, Object... args) {
        getWait(waitInSecondsBefore).until(ExpectedConditions.invisibilityOfElementLocated(locator.getLocator(args)));
    }

    public void wait(int waitInSeconds) {
        try {
            Thread.sleep(waitInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Actions with mouse
     */

    protected void mouseDragAndDrop(Locator from, Locator to, Object... args) {
        Actions actions = new Actions(BaseTest.getDriver());
        actions.moveToElement(getElement(from, args));
        actions.dragAndDrop(getElement(from, args), getElement(to)).build().perform();
        actions.moveToElement(getElement(to));
        actions.release();
    }

    protected void mouseMoveToElement(Locator locator, Object... args) {
        LOG.info("move mouse to element '{}'", locator);
        getActions().moveToElement(getElement(locator, args)).build().perform();
    }

    //TODO change deprecate method
    protected void mouseUp(Locator locator, Object... args) {
        LOG.info("move mouse up to element '{}'", locator);
        Locatable mouseDownItem = (Locatable) getElement(locator, args);
        Mouse mouse = ((HasInputDevices) BaseTest.getDriver()).getMouse();
        mouse.mouseUp(mouseDownItem.getCoordinates());
    }

    protected void scrollToElement(Locator locator, Object... args) {
        LOG.info("scroll to element: '{}'", locator);
        waitForElementVisibility(locator);
        ((JavascriptExecutor) BaseTest.getDriver()).
            executeScript("arguments[0].scrollIntoView();", getElement(locator, args));
    }

    public void scrollDown() {
        getActions().sendKeys(Keys.PAGE_DOWN).perform();
    }
}
