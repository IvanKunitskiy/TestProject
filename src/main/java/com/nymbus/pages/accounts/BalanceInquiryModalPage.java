package com.nymbus.pages.accounts;

import com.codeborne.selenide.SelenideElement;
import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BalanceInquiryModalPage extends PageTools {
    private final By printButton = By.xpath("//button[span[contains(text(), 'Print')]]");
    private final By closeButton = By.xpath("//button[contains(text(), 'Close')]");
    private final By closeModalButton = By.xpath("//button[contains(@aria-label, 'Close')]//span[contains(text(), '×')]");
    private final By balanceInquiryImage = By.xpath("//img[@id='receiptTemplate'][contains(@src, 'base64')]");
    private final By imageLoadSpinner =By.xpath("//div[@id='printReceipt']/dn-loading-spinner/div/svg");
    private final By printBalancesOnReceiptCheckbox = By.xpath("//span[contains(text(), 'Print Balances on Receipt')]/preceding-sibling::input");
    private final By transactionSuccessfullyCompletedLabel = By.xpath("//span[text()='Transaction was successfully completed.']");

    @Step("Click the 'Print' button")
    public void clickPrintButton() {
        waitForElementClickable(printButton);
        click(printButton);
    }

    @Step("Click the 'Close' button")
    public void clickCloseButton() {
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Click the 'Close modal' button")
    public void clickCloseModalButton() {
        waitForElementClickable(closeModalButton);
        click(closeModalButton);
    }

    @Step("Click the 'Print Balances on Receipt' checkbox")
    public void clickPrintBalancesOnReceiptCheckbox() {
        waitForElementClickable(printBalancesOnReceiptCheckbox);
        click(printBalancesOnReceiptCheckbox);
    }

    @Step("Get balance inquiry image")
    public SelenideElement getBalanceInquiryImage() {
        waitForElementVisibility(balanceInquiryImage);
        return getSelenideElement(balanceInquiryImage);
    }

    @Step("Get 'src' attribute of balance inquiry image")
    public String getBalanceInquiryImageSrc() {
        waitForElementVisibility(balanceInquiryImage);
        return getElementAttributeValue("src", balanceInquiryImage);
    }

    @Step("Wait for loading spinner invisibility")
    public void waitForLoadingSpinnerInvisibility() {
        waitForElementInvisibility(imageLoadSpinner);
    }

    @Step("Is 'Transaction was successfully completed.' label visible")
    public boolean isTransactionWasSuccessfullyCompletedLabelVisible() {
        return isElementVisible(transactionSuccessfullyCompletedLabel);
    }
}
