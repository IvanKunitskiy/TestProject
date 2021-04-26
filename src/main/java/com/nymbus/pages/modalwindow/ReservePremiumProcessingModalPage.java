package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ReservePremiumProcessingModalPage extends PageTools {

    private final By modalWindow = By.xpath("//div[@class='modal-content']");
    private final By closeButton = By.xpath("(//button[span[text()='×']])[2]");
    private final By addNewLoanReservePremiumButton = By.xpath("//button/span[text()='Add New Loan Reserve/Premium']");
    private final By effectiveDate = By.xpath("//input[@data-test-id='field-effectivedate']");
    private final By reservePremiumAmount = By.xpath("//input[@data-test-id='field-originalamount']");
    private final By deferredYesNoSwitch = By.xpath("//dn-switch[@id='isdeferred']");
    private final By deferredYesNoSwitchValue = By.xpath("//dn-switch[@id='isdeferred']//div/span[1]");
    private final By reservePremiumCode = By.xpath("//div[@id='loanreserveid']");
    private final By reservePremiumCodeOption = By.xpath("//div[@data-test-id='field-loanreserveid']//ul/li/div/span");
    private final By reservePremiumCodeOptionByText = By.xpath("//div[@data-test-id='field-loanreserveid']//ul/li/div/span[text()='%s']");
    private final By reservePremiumTerm = By.xpath("//input[@id='terminmonths']");
    private final By reservePremiumDeferringStartDate = By.xpath("//input[@id='deferringstartdate']");
    private final By irsReportablePointsPaidSwitch = By.xpath("//dn-switch[@id='irspointspaid']");
    private final By irsReportablePointsPaidSwitchValue = By.xpath("//dn-switch[@id='irspointspaid']//div/span[2]");
    private final By commitTransactionButton = By.xpath("//button[text()='Commit Transaction']");
    private final By glOffset = By.xpath("//div[@data-test-id='field-gloffsetaccountid']/div/input");
    private final By glOffsetOptionByText = By.xpath("//div[@role='option']/div/div[contains(text(), '%s')]");
    private final By listOfGlOffset = By.xpath("//div[@id='gloffsetaccountid']//div[@role='option']/div/div");
    private final By reservePremiumRecordFromTableByIndex = By.xpath("//div[@class='row']/table//tr[contains(@class, 'previewableItem')][%s]");

    @Step("Click the Reserve/Premium record from table by index")
    public void clickReservePremiumRecordFromTableByIndex(int index) {
        waitForElementClickable(reservePremiumRecordFromTableByIndex, index);
        click(reservePremiumRecordFromTableByIndex, index);
    }

    @Step("Click the 'Close' button")
    public void clickCloseButton() {
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Click the 'Commit Transaction' button")
    public void clickCommitTransactionButton() {
        waitForElementClickable(commitTransactionButton);
        click(commitTransactionButton);
    }

    @Step("Click the 'Reserve/Premium Code' option by name")
    public void clickReservePremiumCodeOptionByName(String option) {
        waitForElementClickable(reservePremiumCodeOptionByText, option);
        click(reservePremiumCodeOptionByText, option);
    }

    @Step("Click the 'Gl Offset' option by name")
    public void clickGlOffsetByTextOptionByName(String option) {
        waitForElementClickable(glOffsetOptionByText, option);
        click(glOffsetOptionByText, option);
    }

    @Step("Click the 'Reserve/Premium Code' input")
    public void clickReservePremiumCode(String text) {
        waitForElementClickable(reservePremiumCode, text);
        click(reservePremiumCode, text);
    }

    @Step("Get the list of 'Reserve/Premium Code'")
    public List<String> getListOfReservePremiumCode() {
        return getElementsText(reservePremiumCodeOption);
    }

    @Step("Get the list of 'Gl Offset'")
    public List<String> getListOfGlOffset() {
        return getElementsText(listOfGlOffset);
    }

    @Step("Wait for modal window visibility")
    public void waitForModalWindowVisibility() {
        waitForElementVisibility(modalWindow);
    }

    @Step("Click the 'Add New Loan Reserve/Premium' button")
    public void clickAddNewLoanReservePremiumButton() {
        waitForElementClickable(addNewLoanReservePremiumButton);
        click(addNewLoanReservePremiumButton);
    }

    @Step("Set 'Effective date' value")
    public void setEffectiveDate(String date) {
        waitForElementClickable(effectiveDate);
        typeWithoutWipe("", effectiveDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, effectiveDate);
    }

    @Step("Set 'Reserve/Premium Amount' value")
    public void setReservePremiumAmount(String value) {
        waitForElementClickable(reservePremiumAmount);
        type(value, reservePremiumAmount);
    }

    @Step("Set 'Reserve/Premium Term' value")
    public void setReservePremiumTerm(String value) {
        waitForElementClickable(reservePremiumTerm);
        type(value, reservePremiumTerm);
    }

    @Step("Set 'Gl Offset' value")
    public void setGlOffset(String value) {
        waitForElementClickable(glOffset);
        type(value, glOffset);
    }

    @Step("Click the 'Deferred Yes/No' switch")
    public void clickDeferredYesNoSwitch() {
        waitForElementClickable(deferredYesNoSwitch);
        click(deferredYesNoSwitch);
    }

    @Step("Get 'Deferred Yes/No' value")
    public String getDeferredYesNoSwitchValue() {
        waitForElementClickable(deferredYesNoSwitchValue);
        return getElementText(deferredYesNoSwitch).trim();
    }

    @Step("Click the 'IRS Reportable Points Paid' switch")
    public void clickIrsReportablePointsPaidSwitch() {
        waitForElementClickable(irsReportablePointsPaidSwitch);
        click(irsReportablePointsPaidSwitch);
    }

    @Step("Get 'IRS Reportable Points Paid' value")
    public String getIrsReportablePointsPaidSwitchValue() {
        waitForElementClickable(irsReportablePointsPaidSwitchValue);
        return getElementText(irsReportablePointsPaidSwitchValue).trim();
    }

    @Step("Set 'Reserve/Premium Deferring Start Date' value")
    public void setReservePremiumDeferringStartDate(String date) {
        waitForElementClickable(reservePremiumDeferringStartDate);
        typeWithoutWipe("", reservePremiumDeferringStartDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, reservePremiumDeferringStartDate);
    }

    /**
     *  Reserve / Premium record overview
     */

    private final By adjustmentAmount = By.xpath("//tr/td[label[text()='Adjustment Amount']]/following-sibling::td/dn-field-view");
    private final By reservePremiumOriginalAmount = By.xpath("//tr/td[label[text()='Reserve/Premium Original Amount']]/following-sibling::td/dn-field-view//span/span");
    private final By reservePremiumUnamortized = By.xpath("//tr/td[label[text()='Reserve/Premium Unamortized']]/following-sibling::td/dn-field-view//span/span");
    private final By reservePremiumMaturityDate = By.xpath("//tr/td[label[text()='Reserve/Premium Maturity Date']]/following-sibling::td/dn-field-view//span/span");
    private final By reservePremiumDeferringStartDateValue = By.xpath("//tr/td[label[text()='Reserve/Premium Deferring Start Date']]/following-sibling::td/dn-field-view//span/span");
    private final By reservePremiumTermValue = By.xpath("//tr/td[label[text()='Reserve/Premium Term']]/following-sibling::td/dn-field-view//span/span");

    @Step("Get 'Reserve/Premium Maturity Date' value")
    public String getReservePremiumMaturityDate() {
        waitForElementClickable(reservePremiumMaturityDate);
        return getElementText(reservePremiumMaturityDate).trim();
    }

    @Step("Get 'Reserve/Premium Deferring Start Date' value")
    public String getReservePremiumDeferringStartDateValue() {
        waitForElementClickable(reservePremiumDeferringStartDateValue);
        return getElementText(reservePremiumDeferringStartDateValue);
    }

    @Step("Get 'Reserve/Premium Term' value")
    public String getReservePremiumTermValue() {
        waitForElementClickable(reservePremiumTermValue);
        return getElementText(reservePremiumTermValue);
    }

    @Step("Get 'Adjustment Amount' value")
    public String getAdjustmentAmount() {
        waitForElementClickable(adjustmentAmount);
        return getElementText(adjustmentAmount);
    }

    @Step("Get 'Reserve/Premium Original Amount' value")
    public String getReservePremiumOriginalAmount() {
        waitForElementClickable(reservePremiumOriginalAmount);
        return getElementText(reservePremiumOriginalAmount).replaceAll("[^0-9.]", "");
    }

    @Step("Get 'Reserve/Premium Unamortized' value")
    public String getReservePremiumUnamortized() {
        waitForElementClickable(reservePremiumUnamortized);
        return getElementText(reservePremiumUnamortized).replaceAll("[^0-9.]", "");
    }
}
