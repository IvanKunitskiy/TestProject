package com.nymbus.pages.accounts.transactions;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Generator;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class EditAccountTransactionModal extends PageTools {
    private By descriptionField = By.xpath("//input[@data-test-id='field-uniqueeftdescription']");
    private By saveChanges = By.xpath("//button[contains(text(), 'Save Changes')]");

    @Step("Edit 'Description' field")
    public void editDescriptionField() {
        waitForElementClickable(descriptionField);
        typeWithoutWipe(Generator.genString(5), descriptionField);
    }

    @Step("Click the 'Save Changes' button")
    public void clickTheSaveChangesButton() {
        waitForElementClickable(saveChanges);
        click(saveChanges);
    }
}
