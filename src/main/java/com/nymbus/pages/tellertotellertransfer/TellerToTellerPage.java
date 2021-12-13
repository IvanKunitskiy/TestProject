package com.nymbus.pages.tellertotellertransfer;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TellerToTellerPage extends PageTools {

    private By modalWindow = By.xpath("//div[@class='modal-content']");
    private By cashDrawerName = By.xpath("//div[@name='cashDrawerTemplate']/a/span/span");
    private By newTransferButton = By.xpath("//button/span");
    private By actionArrow = By.xpath("(//div[@ng-model='transfer.tellerrole'])[%s]");
    private By actionArrowList = By.xpath("//div[@ng-model='transfer.tellerrole']");
    private By approverArrow = By.xpath("(//div[@ng-model='transfer.otherCashDrawerId'])[%s]");
    private By approverArrowList = By.xpath("//div[@ng-model='transfer.otherCashDrawerId']");
    private By approwerSpanList = By.xpath("//span[contains(string(),'%s')]");
    private By approwerSpan = By.xpath("(//span[contains(string(),'%s')])[%s]");
    private By notesArray = By.xpath("//textarea");
    private By notes = By.xpath("(//textarea)[%s]");
    private By hundredsDenomination = By.xpath("//td[text()='Hundreds']/following-sibling::td//input");
    private By inputHundredsArray = By.xpath("//input[@name='onehundredsloose']");
    private By inputHundreds = By.xpath("(//input[@name='onehundredsloose'])[%s]");
    private By commitButton = By.xpath("//button[text()='Commit']");
    private By status = By.xpath("(//td[@ng-if='transfer.config.tellerprocessingstatusid.isShow'])[%s]");
    private By statusList = By.xpath("//td[@ng-if='transfer.config.tellerprocessingstatusid.isShow']");
    private By number = By.xpath("(//td[@ng-if='transfer.config.transactionid.isShow'])[%s]");
    private By numberList = By.xpath("//td[@ng-if='transfer.config.transactionid.isShow']");
    private By approveButton = By.xpath("(//button[contains(string(),'Approve')])[%s]");
    private By approveButtonList = By.xpath("//button[contains(string(),'Approve')]");
    private By noResultsP = By.xpath("//td[contains(string(),'%s')]");

    @Step("Wait 'Proof Date Login' modal window")
    public void waitModalWindow() {
        waitForElementVisibility(modalWindow);
        waitForElementClickable(modalWindow);
    }

    @Step("Get 'Cash Drawer' name")
    public String getCashDrawerName() {
        waitForElementVisibility(cashDrawerName);
        return getElementText(cashDrawerName).trim();
    }

    @Step("Click add new transfer")
    public void clickAddNewTransfer() {
        waitForElementVisibility(newTransferButton);
        waitForElementVisibility(newTransferButton);
        click(newTransferButton);
    }

    @Step("Click apprower")
    public void clickApprover(String teller) {
        waitForElementVisibility(approverArrow,1);
        int size = getElements(approverArrowList).size();
        waitForElementVisibility(approverArrow, size);
        waitForElementClickable(approverArrow, size);
        click(approverArrow, size);
        waitForElementVisibility(approwerSpan,teller, 1);
        int sizeSpan = getElements(approwerSpanList, teller).size();
        waitForElementVisibility(approwerSpan, teller, sizeSpan);
        waitForElementClickable(approwerSpan, teller, sizeSpan);
        click(approwerSpan, teller, sizeSpan);
    }

    @Step("Click action")
    public void clickAction(String action) {
        waitForElementVisibility(actionArrow,1);
        int size = getElements(actionArrowList).size();
        waitForElementVisibility(actionArrow,size);
        waitForElementClickable(actionArrow,size);
        click(actionArrow,size);
        waitForElementVisibility(approwerSpan,action, 1);
        int sizeSpan = getElements(approwerSpanList, action).size();
        waitForElementVisibility(approwerSpan, action, sizeSpan);
        waitForElementClickable(approwerSpan, action, sizeSpan);
        click(approwerSpan, action, sizeSpan);
    }

    @Step("Input notes")
    public void typeNotes(String notes) {
        int size = getElements(this.notesArray).size();
        waitForElementVisibility(this.notes, size);
        type(notes, this.notes, size);
    }

    @Step("Set {0} in hundreds amount")
    public void typeHundredsAmountValue(String value) {
        int size = getElements(inputHundredsArray).size();
        //shouldNotBeEmpty(hundredsDenomination);
        waitForElementClickable(inputHundreds, size);
        jsType(value, inputHundreds, size);
        jsRiseOnchange(inputHundreds, size);
    }

    @Step("Click commit transfer")
    public void clickCommitTransfer() {
        waitForElementVisibility(commitButton);
        waitForElementVisibility(commitButton);
        click(commitButton);
    }

    @Step("Get status transfer")
    public String getStatusTransfer() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementVisibility(statusList);
        int size = getElements(statusList).size();
        waitForElementVisibility(status,size);
        return getElementAttributeValue("innerHTML", status, size);
    }

    @Step("Get number transfer")
    public String getNumberTransfer() {
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        waitForElementVisibility(numberList);
        int size = getElements(numberList).size();
        waitForElementVisibility(number,size);
        return getElementAttributeValue("innerHTML", number, size);
    }

    @Step("Click Approve Button")
    public void clickApproveButton() {
        waitForElementVisibility(approveButtonList);
        int size = getElements(approveButtonList).size();
        waitForElementVisibility(approveButton,size);
        click(approveButton,size);
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
    }

    @Step("No results is visible")
    public boolean noTransactionsByNumber(String number){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return getElementsWithZeroOption(noResultsP,number).size() == 0;
    }

}
