package com.nymbus.pages.modalwindow;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ParticipationsModalPage extends PageTools {

    private final By addNewParticipationsButton = By.xpath("//dn-add-button[@button-title='Add New Participation']/button");

    private final By participantSelectorButton = By.xpath("//div[@id='participantid']");
    private final By participantList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By participantSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");
    private final By soldDate = By.xpath("//input[@data-test-id='field-solddate']");
    private final By percentageSold = By.xpath("//input[@data-test-id='field-percentagesold']");
    private final By participantRow = By.xpath("//table//tr[contains(@class, 'previewableItem')][%s]");
    private final By sellButton = By.xpath("//button[text()='Sell']");
    private final By participantStatusByIndex = By.xpath("//table//tr[%s]/td[4]");
    private final By participantSoldStatusByIndex = By.xpath("//table//tr[%s]/td[4]//span[text()='Sold']");
    private final By participantRepurchaseStatusByIndex = By.xpath("//table//tr[%s]/td[4]//span[text()='Repurchased']");
    private final By closeButton = By.xpath("//div[@class='modal-content']//button[contains(@class, 'close')]");
    private final By repurchaseButton = By.xpath("//button[text()='Repurchase']");
    private final By saveButton = By.xpath("//button[span[text()='Save Changes']]");
    private final By participantBalance = By.xpath("(//tr/td/dn-field-view//span/span)[9]");
    private final By okButton = By.xpath("//button[contains(string(),'OK')]");


    private final By leftParticipant = By.xpath("//span[contains(string(),'Participant')]");
    private final By rightParticipant = By.xpath("//h5[contains(string(),'Participation Settings')]");
    private final By addNewButton = By.xpath("//span[contains(string(),'Add New Participation')]/../..");
    private final By participantStatus = By.xpath("//div[@name='status']");
    private final By participantId = By.xpath("//div[@name='participantid']");
    private final By autotestParticipant = By.xpath("//li[contains(string(),'autotest')]/div/span");
    private final By servicingFee = By.xpath("//input[@name='servicefeepercentage']");
    private final By autotestRecord = By.xpath("//span[contains(string(),'autotest') and @text='value']/span");
    private final By status = By.xpath("//span[contains(string(),'Pending') and @text='value']/span");
    private final By percentage = By.xpath("//label[contains(string(),'Percentage Sold')]/../../td//span/span");
    private final By partPercentage = By.xpath("//label[contains(string(),'Participation Percent Sold')]/../../td//span/span");
    private final By servicing = By.xpath("//label[contains(string(),'Servicing Fee')]/../../td//span/span");

    private final By participantBalanceLabel = By.xpath("//label[contains(string(),'Participant Balance')]/../../td//span/span");
    private final By participantCurrentBalanceLabel = By.xpath("//label[contains(string(),'Participant Current Balance')]/../../td//span/span");
    private final By fiOwnedBalanceLabel = By.xpath("//label[contains(string(),'FI Owned Balance')]/../../td//span/span");
    private final By date = By.xpath("//label[contains(string(),'Sold Date')]/../../td//span/span");
    private final By repurchaseDate = By.xpath("//label[contains(string(),'Repurchase Date')]/../../td//span/span");
    private final By repurchaseAmount = By.xpath("//label[contains(string(),'Repurchase Amount')]/../../td//span/span");
    private final By partAccruedInterest = By.xpath("//label[contains(string(),'Participant Accrued Interest')]/../../td//span/span");
    private final By fiOwnedInterest = By.xpath("//label[contains(string(),'FI Owned Accrued Interest')]/../../td//span/span");
    private final By partServicingFee = By.xpath("//label[contains(string(),'Participation Servicing Fee')]/../../td//span/span");
    private final By alertMessage = By.xpath("//p[text()='The Sell will post transactions that can be viewed on Transaction History']");

    @Step("Get 'Participant Balance' value")
    public String getParticipantBalance() {
        waitForElementVisibility(participantBalance);
        return getElementText(participantBalance).replaceAll("[^0-9.]", "");
    }

    @Step("Click the 'Save' button")
    public void clickSaveButton() {
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Click the 'Close' button")
    public void clickCloseButton() {
        waitForElementClickable(closeButton);
        click(closeButton);
    }

    @Step("Click the participant row")
    public void waitForSoldStatusVisibleByIndex(int index) {
        waitForElementVisibility(participantSoldStatusByIndex, index);
    }

    @Step("Click the participant row")
    public void waitForRepurchaseStatusVisibleByIndex(int index) {
        waitForElementVisibility(participantRepurchaseStatusByIndex, index);
    }

    @Step("Click the participant row")
    public String getParticipantStatusByIndex(int index) {
        waitForElementClickable(participantStatusByIndex, index);
        return getElementText(participantStatusByIndex, index).trim();
    }

    @Step("Click the 'Repurchase' button")
    public void clickRepurchaseButton() {
        waitForElementClickable(repurchaseButton);
        click(repurchaseButton);
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
    }

    @Step("Click the 'Sell' button")
    public void clickSellButton() {
        waitForElementClickable(sellButton);
        click(sellButton);
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
    }

    @Step("Click the participant row")
    public void clickParticipantRowByIndex(int index) {
        waitForElementClickable(participantRow, index);
        click(participantRow, index);
    }

    @Step("Click the 'Add new particiaptions' button")
    public void clickAddNewParticipationsButton() {
        waitForElementClickable(addNewParticipationsButton);
        click(addNewParticipationsButton);
    }

    @Step("Click the 'Participant' selector button")
    public void clickParticipantSelectorOption(String option) {
        waitForElementVisibility(participantSelectorOption, option);
        waitForElementClickable(participantSelectorOption, option);
        click(participantSelectorOption, option);
    }

    @Step("Returning list of 'Participant' options")
    public List<String> getParticipantList() {
        waitForElementVisibility(participantList);
        waitForElementClickable(participantList);
        return getElementsText(participantList);
    }

    @Step("Click the 'Participant' selector button")
    public void clickParticipantSelectorButton() {
        waitForElementVisibility(participantSelectorButton);
        scrollToPlaceElementInCenter(participantSelectorButton);
        waitForElementClickable(participantSelectorButton);
        click(participantSelectorButton);
    }

    @Step("Set 'Sold date' value")
    public void setSoldDate(String date) {
        waitForElementClickable(soldDate);
        typeWithoutWipe("", soldDate);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        typeWithoutWipe(date, soldDate);
    }

    @Step("Set 'Percentage Sold' value")
    public void setPercentageSold(String percentage) {
        waitForElementClickable(percentageSold);
        type(percentage, percentageSold);
    }

    @Step("Check alert message")
    public void checkAlertMessage() {
        waitForElementVisibility(alertMessage);
    }

    @Step("Click Ok button")
    public void clickOk() {
        waitForElementVisibility(okButton);
        click(okButton);
    }


    @Step("Check left participant")
    public void checkLeftParticipant() {
        waitForElementVisibility(leftParticipant);
    }

    @Step("Check right participant")
    public void checkRightParticipant() {
        waitForElementVisibility(rightParticipant);
    }

    @Step("Click 'Add New' button")
    public void clickAddNewButton() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        waitForElementVisibility(addNewButton);
        waitForElementClickable(addNewButton);
        click(addNewButton);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
    }

    @Step("Check 'Status' is disabled")
    public boolean isStatusDisabled() {
        waitForElementVisibility(participantStatus);
        return Boolean.parseBoolean(getElementAttributeValue("disabled", participantStatus));
    }

    @Step("Select 'Participant'")
    public void selectParticipant() {
        waitForElementVisibility(participantId);
        click(participantId);
        waitForElementVisibility(autotestParticipant);
        click(autotestParticipant);
    }

    @Step("Type 'Percentage Sold'")
    public void inputPercentageSold(String percent) {
        waitForElementVisibility(percentageSold);
        type(percent, percentageSold);
    }

    @Step("Type 'Servicing Fee'")
    public void inputServicingFee(String servicingFee) {
        waitForElementVisibility(this.servicingFee);
        type(servicingFee, this.servicingFee);
    }

    @Step("Type 'Sold date'")
    public void inputSoldDate(String date) {
        waitForElementVisibility(soldDate);
        type(date, soldDate);
    }

    @Step("Click record")
    public void clickAutotestRecord() {
        waitForElementVisibility(autotestRecord);
        click(autotestRecord);
    }

    @Step("Check Pending Status")
    public void checkPendingStatus() {
        waitForElementVisibility(status);
    }

    @Step("Check Percentage Sold")
    public boolean checkPercentageSold(String sold) {
        waitForElementVisibility(percentage);
        return getElementText(percentage).equals(sold + "%");
    }

    @Step("Check Part Percentage Sold")
    public boolean checkPartPercentageSold(String sold) {
        waitForElementVisibility(partPercentage);
        return getElementText(partPercentage).equals(sold + "%");
    }

    @Step("Check Servicing Fee")
    public boolean checkServicingFee(String fee) {
        waitForElementVisibility(servicing);
        return getElementText(servicing).equals(fee + "%");
    }

    @Step("Check Part Balance")
    public boolean checkPartBalance(String balance) {
        waitForElementVisibility(participantBalanceLabel);
        return getElementText(participantBalanceLabel).replaceAll("[^0-9.]", "").equals(balance);
    }

    @Step("Check Part Current Balance")
    public boolean checkPartCurrentBalance(String balance) {
        waitForElementVisibility(participantCurrentBalanceLabel);
        return getElementText(participantCurrentBalanceLabel).replaceAll("[^0-9.]", "").equals(balance);
    }

    @Step("Check Fi Owned Balance")
    public boolean checkFiOwnedBalance(String balance) {
        waitForElementVisibility(fiOwnedBalanceLabel);
        return getElementText(fiOwnedBalanceLabel).replaceAll("[^0-9.]", "").equals(balance);
    }

    @Step("Check Sold Date")
    public boolean checkSoldDate(String date) {
        waitForElementVisibility(this.date);
        return getElementText(this.date).equals(date);
    }

    @Step("Check repurchase date")
    public boolean checkRepurchaseDate(String date) {
        waitForElementVisibility(repurchaseDate);
        return getElementText(repurchaseDate).equals(date);
    }

    @Step("Check repurchase amount")
    public boolean checkRepurchaseAmount(String amount) {
        waitForElementVisibility(repurchaseAmount);
        return getElementText(repurchaseAmount).replaceAll("[^0-9.]", "").equals(amount);
    }

    @Step("Check participant accrued interest")
    public boolean checkPartAccruedInterest(String interest) {
        waitForElementVisibility(partAccruedInterest);
        return getElementText(partAccruedInterest).replaceAll("[^0-9.]", "").equals(interest);
    }

    @Step("Check FI owned accrued interest")
    public boolean checkFIOwnedAccruedInterest(String interest) {
        waitForElementVisibility(fiOwnedInterest);
        String accinterest = getElementText(fiOwnedInterest).replaceAll("[^0-9.]", "");
        if (!accinterest.equals(interest)) {
            return accinterest.equals(Double.parseDouble(interest) - 0.01 + "");
        }
        return true;
    }

    @Step("Check participant servicing fee")
    public boolean checkPartServicingFee(String fee) {
        waitForElementVisibility(partServicingFee);
        return getElementText(partServicingFee).replaceAll("[^0-9.]", "").equals(fee);
    }

    @Step("Check repurchase button disabled")
    public boolean isRepurchaseButtonDisabled() {
        waitForElementVisibility(repurchaseButton);
        return Boolean.parseBoolean(getDisabledElementAttributeValue("disabled", repurchaseButton));
    }

}
