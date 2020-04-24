package com.nymbus.pages.webadmin.accountstransactions;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountsPage extends PageTools {

    private By rootID = By.xpath("//input[@id='rootundefined_searchableBox']");
    private By rootIDLink = By.xpath("//span[@class='rulesui-tree-item']/span[contains(text(), '%s')]");
    private By accountStatusField = By.xpath("//input[@fieldname='action_accountstatus']");
    private By saveChangesButton = By.xpath("//input[@value='Save Changes']");

    @Step("Click 'RootID' link")
    public void clickRootIDLink(String accountNumber) {
        waitForElementClickable(rootIDLink, accountNumber);
        click(rootIDLink, accountNumber);
    }

    @Step("Wait for 'RootID' link")
    public void waitForRootIDLink(String accountNumber) {
        waitForElementVisibility(rootIDLink, accountNumber);
    }

    @Step("Click 'Save Changes' button")
    public void clickSaveChangesButton() {
        waitForElementClickable(accountStatusField);
        SelenideTools.sleep(5);
        click(saveChangesButton);
    }

    @Step("Set value to 'Account Status' field")
    public void setAccountStatus(String value) {
        waitForElementVisibility(accountStatusField);
        waitForElementClickable(accountStatusField);
        type(value, accountStatusField);
    }

    @Step("Set value to 'rootId' field")
    public void setValueToRootIDField(String value) {
        waitForElementVisibility(rootID);
        waitForElementClickable(rootID);
        type(value, rootID);
    }

}
