package com.nymbus.pages.clients.maintenance;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MaintainceStatementProcessingPage extends PageTools {

    private final By accountNumber = By.xpath("//span[contains(string(),'%s')]");
    private final By editButton = By.xpath("//*[contains(text(), 'Edit')]");
    private final By plus = By.xpath("(//a[@ng-click='toggleExpanded()'])[%s]");
    private final By addSecondaryAccount = By.xpath("//button[contains(string(),'Add Secondary Account')]");
    private final By select = By.xpath("//span[@class='select2-chosen ng-binding']");
    private final By saveButton = By.xpath("//span[text()='Save']");
    private final By hasSecondary = By.xpath("(((//tr[@class='hoverPointer'])[%s])//td[3])//span/span");
    private final By secondaryInfo = By.xpath("//td[@colspan='5']//span/span");

    @Step("Check is account present by number")
    public boolean isAccountPresent(String number) {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return getElementsWithZeroOption(accountNumber, number).size()>0;
    }

    @Step("Click 'Edit' button")
    public void clickEditButton() {
        waitForElementVisibility(editButton);
        waitForElementClickable(editButton);
        click(editButton);
    }

    @Step("Click plus for account")
    public void clickPlusForAccount(int index) {
        waitForElementVisibility(plus, index);
        waitForElementClickable(plus, index);
        click(plus, index);
    }

    @Step("Click 'Add Secondary account' for account")
    public void clickAddSecondaryAccount() {
        waitForElementVisibility(addSecondaryAccount);
        waitForElementClickable(addSecondaryAccount);
        click(addSecondaryAccount);
    }

    @Step("Click 'Select' for account")
    public void clickSelectSecondaryAccount() {
        waitForElementVisibility(select);
        waitForElementClickable(select);
        click(select);
    }

    @Step("Click account by number")
    public void clickAccountByNumber(String number) {
        waitForElementVisibility(accountNumber,number);
        waitForElementClickable(accountNumber,number);
        click(accountNumber,number);
    }

    @Step("Click 'Save' button")
    public void clickSaveButton() {
        waitForElementVisibility(saveButton);
        waitForElementClickable(saveButton);
        click(saveButton);
    }

    @Step("Has secondary Account")
    public String hasSecondaryAccount(int index) {
        waitForElementVisibility(hasSecondary, index);
        return getElementText(hasSecondary, index);
    }

    @Step("Check secondary info")
    public boolean checkSecondaryInfo(String number){
        waitForElementVisibility(secondaryInfo);
        return getElementText(secondaryInfo).contains(number);
    }

}
