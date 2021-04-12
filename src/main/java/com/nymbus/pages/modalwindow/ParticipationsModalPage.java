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
    
}
