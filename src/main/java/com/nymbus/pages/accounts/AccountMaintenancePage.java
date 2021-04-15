package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class AccountMaintenancePage extends PageTools {

    private final By viewAllMaintenanceHistoryLink = By.xpath("//button//span[contains(text(), 'View All History')]");
    private final By viewMoreButton = By.xpath("//button[@data-test-id='action-loadMore']");
    private final By changeTypeFields = By.xpath("//table//tr//td/span[text()='%s']");
    private final By rowsInTable = By.xpath("//table//tr");
    private final By rowOldValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[4]/span)[%s]");
    private final By rowNewValueByRowName = By.xpath("(//table//tr//td/span[text()='%s']//ancestor::node()[3]/td[5]/span)[%s]");
    private final By rowsCountByName = By.xpath("//table//tr//td/span[text()='%s']");

    @Step("Is more button visible")
    public boolean isMoreButtonVisible() {
        return isElementVisible(viewMoreButton);
    }

    @Step("Get row {0} old value by {1}")
    public String getRowOldValueByRowName(String text, int index) {
        waitForElementVisibility(rowOldValueByRowName, text, index);
        return getElementText(rowOldValueByRowName, text, index).trim();
    }

    @Step("Get row {0} new value by {1}")
    public String getRowNewValueByRowName(String text, int index) {
        waitForElementVisibility(rowNewValueByRowName, text, index);
        return getElementText(rowNewValueByRowName, text, index).trim();
    }

    @Step("Click 'ViewAllMaintenanceHistory' button")
    public void clickViewAllMaintenanceHistoryLink() {
        waitForElementVisibility(viewAllMaintenanceHistoryLink);
        waitForElementClickable(viewAllMaintenanceHistoryLink);
        click(viewAllMaintenanceHistoryLink);
    }

    @Step("Click 'ViewMore' button")
    public void clickViewMoreButton() {
        waitForElementVisibility(viewMoreButton);
        waitForElementClickable(viewMoreButton);
        click(viewMoreButton);
    }

    @Step("Get count of rows in table by change type")
    public int getChangeTypeElementsCount(String text) {
        waitForElementVisibility(changeTypeFields, text);
        return getElements(changeTypeFields, text).size();
    }

    @Step("Get count of rows in table by field name")
    public int getRowsCountByFieldName(String fieldName) {
        waitForElementVisibility(rowsCountByName, fieldName);
        return getElementsWithZeroOption(rowsCountByName, fieldName).size();
    }

    @Step("Wait for more button invisibility")
    public void waitForMoreButtonInvisibility() {
        waitForElementInvisibility(viewMoreButton);
    }

    @Step("Get count of rows in table")
    public int getRowsCount() {
        return getElements(rowsInTable).size();
    }

    /**
     * Tools
     */

    private final By chooseToolSelectorButton = By.xpath("//div[@data-test-id='field-toolSelect']");
    private final By chooseToolList = By.xpath("//li[contains(@role, 'option')]/div/span");
    private final By chooseToolSelectorOption = By.xpath("//ul[@role='listbox']//li[contains(@role, 'option')]/div[span[text()='%s']]");
    private final By toolsLaunchButton = By.xpath("//article[@ui-view='tools']//button/span[text()='Launch']");

    @Step("Click the 'Choose Tool' selector button")
    public void clickChooseToolOption(String chooseToolOption) {
        waitForElementVisibility(chooseToolSelectorOption, chooseToolOption);
        waitForElementClickable(chooseToolSelectorOption, chooseToolOption);
        click(chooseToolSelectorOption, chooseToolOption);
    }

    @Step("Returning list of 'Choose Tool' options")
    public List<String> getChooseToolList() {
        waitForElementVisibility(chooseToolList);
        waitForElementClickable(chooseToolList);
        return getElementsText(chooseToolList);
    }

    @Step("Click the 'Choose Tool' selector button")
    public void clickChooseToolSelectorButton() {
        waitForElementVisibility(chooseToolSelectorButton);
        scrollToPlaceElementInCenter(chooseToolSelectorButton);
        waitForElementClickable(chooseToolSelectorButton);
        click(chooseToolSelectorButton);
    }

    @Step("Click tools 'Launch' button")
    public void clickToolsLaunchButton() {
        waitForElementClickable(toolsLaunchButton);
        click(toolsLaunchButton);
    }

//    /**
//     * Participations
//     */
//    private final By leftParticipant = By.xpath("//span[contains(string(),'Participant')]");
//    private final By rightParticipant = By.xpath("//h5[contains(string(),'Participation Settings')]");
//    private final By addNewButton = By.xpath("//span[contains(string(),'Add New Participation')]/../..");
//    private final By participantStatus = By.xpath("//div[@name='status']");
//    private final By participantId = By.xpath("//div[@name='participantid']");
//    private final By autotestParticipant = By.xpath("//li[contains(string(),'autotest')]/div/span");
//    private final By percentageSold = By.xpath("//input[@name='percentagesold']");
//    private final By servicingFee = By.xpath("//input[@name='servicefeepercentage']");
//    private final By soldDate = By.xpath("//input[@name='solddate']");
//    private final By saveButton = By.xpath("//span[contains(string(),'Save Changes')]");
//    private final By autotestRecord = By.xpath("//span[contains(string(),'autotest') and @text='value']/span");
//    private final By status = By.xpath("//span[contains(string(),'Pending') and @text='value']/span");
//    private final By percentage = By.xpath("//label[contains(string(),'Percentage Sold')]/../../td//span/span");
//    private final By servicing = By.xpath("//label[contains(string(),'Servicing Fee')]/../../td//span/span");
//
//    private final By participantBalance = By.xpath("//label[contains(string(),'Participant Balance')]/../../td//span/span");
//    private final By date = By.xpath("//label[contains(string(),'Sold Date')]/../../td//span/span");
//    private final By repurchaseDate = By.xpath("//label[contains(string(),'Repurchase Date')]/../../td//span/span");
//    private final By repurchaseAmount = By.xpath("//label[contains(string(),'Repurchase Amount')]/../../td//span/span");
//    private final By partAccruedInterest = By.xpath("//label[contains(string(),'Participant Accrued Interest')]/../../td//span/span");
//    private final By fiOwnedInterest = By.xpath("//label[contains(string(),'FI Owned Accrued Interest')]/../../td//span/span");
//    private final By partServicingFee = By.xpath("//label[contains(string(),'Participation Servicing Fee')]/../../td//span/span");
//    private final By repurchaseButton = By.xpath("//button[contains(string(),'Repurchase')]");
//    private final By sellButton = By.xpath("//button[contains(string(),'Sell')]");
//
//    @Step("Check left participant")
//    public void checkLeftParticipant() {
//        waitForElementVisibility(leftParticipant);
//    }
//
//    @Step("Check right participant")
//    public void checkRightParticipant() {
//        waitForElementVisibility(rightParticipant);
//    }
//
//    @Step("Click 'Add New' button")
//    public void clickAddNewButton() {
//        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
//        waitForElementVisibility(addNewButton);
//        waitForElementClickable(addNewButton);
//        click(addNewButton);
//        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
//    }
//
//    @Step("Check 'Status' is disabled")
//    public boolean isStatusDisabled() {
//        waitForElementVisibility(participantStatus);
//        return Boolean.parseBoolean(getElementAttributeValue("disabled", participantStatus));
//    }
//
//    @Step("Select 'Participant'")
//    public void selectParticipant() {
//        waitForElementVisibility(participantId);
//        click(participantId);
//        waitForElementVisibility(autotestParticipant);
//        click(autotestParticipant);
//    }
//
//    @Step("Type 'Percentage Sold'")
//    public void inputPercentageSold(String percent) {
//        waitForElementVisibility(percentageSold);
//        type(percent, percentageSold);
//    }
//
//    @Step("Type 'Servicing Fee'")
//    public void inputServicingFee(String servicingFee) {
//        waitForElementVisibility(this.servicingFee);
//        type(servicingFee, this.servicingFee);
//    }
//
//    @Step("Type 'Sold date'")
//    public void inputSoldDate(String date) {
//        waitForElementVisibility(soldDate);
//        type(date, soldDate);
//    }
//
//    @Step("Click 'Save' button")
//    public void clickSaveButton() {
//        waitForElementVisibility(saveButton);
//        click(saveButton);
//    }
//
//    @Step("Click record")
//    public void clickAutotestRecord() {
//        waitForElementVisibility(autotestRecord);
//        click(autotestRecord);
//    }
//
//    @Step("Check Pending Status")
//    public void checkPendingStatus(){
//        waitForElementVisibility(status);
//    }
//
//    @Step("Check Percentage Sold")
//    public boolean checkPercentageSold(String sold){
//        waitForElementVisibility(percentage);
//        return getElementText(percentage).equals(sold + "%");
//    }
//
//    @Step("Check Servicing Fee")
//    public boolean checkServicingFee(String fee){
//        waitForElementVisibility(servicing);
//        System.out.println(getElementText(servicing));
//        return getElementText(servicing).equals(fee + "%");
//    }
//
//    @Step("Check Part Balance")
//    public boolean checkPartBalance(String balance){
//        waitForElementVisibility(participantBalance);
//        return getElementText(participantBalance).equals(balance);
//    }
//
//    @Step("Check Sold Date")
//    public boolean checkSoldDate(String date){
//        waitForElementVisibility(this.date);
//        return getElementText(this.date).equals(date);
//    }
//
//    @Step("Check repurchase date")
//    public boolean checkRepurchaseDate(String date){
//        waitForElementVisibility(repurchaseDate);
//        return getElementText(repurchaseDate).equals(date);
//    }
//
//    @Step("Check repurchase amount")
//    public boolean checkRepurchaseAmount(String amount){
//        waitForElementVisibility(repurchaseAmount);
//        return getElementText(repurchaseAmount).equals(amount);
//    }
//
//    @Step("Check participant accrued interest")
//    public boolean checkPartAccruedInterest(String interest){
//        waitForElementVisibility(partAccruedInterest);
//        System.out.println(getElementText(partAccruedInterest));
//        return getElementText(partAccruedInterest).replaceAll("[^0-9.]", "").equals(interest);
//    }
//
//    @Step("Check FI owned accrued interest")
//    public boolean checkFIOwnedAccruedInterest(String interest){
//        waitForElementVisibility(fiOwnedInterest);
//        return getElementText(fiOwnedInterest).replaceAll("[^0-9.]", "").equals(interest);
//    }
//
//    @Step("Check participant servicing fee")
//    public boolean checkPartServicingFee(String fee){
//        waitForElementVisibility(partServicingFee);
//        return getElementText(partServicingFee).replaceAll("[^0-9.]", "").equals(fee);
//    }
//
//    @Step("Check repurchase button disabled")
//    public boolean isRepurchaseButtonDisabled(){
//        waitForElementVisibility(repurchaseButton);
//        return Boolean.parseBoolean(getDisabledElementAttributeValue("disabled", repurchaseButton));
//    }
//
//    @Step("Click Sell button")
//    public void clickSellButton(){
//        waitForElementVisibility(sellButton);
//        click(sellButton);
//    }




}