package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AddNewLoanProductPage extends PageTools {

    private static By productDescriptionInput = By.xpath("//div[@data-field-id='(databean)name']//input[@name='field[(databean)name]']");
    private static By initialsInput = By.xpath("//div[@data-field-id='accounttypeinitials']//input[@name='field[accounttypeinitials]']");
    private static By saveChangesButton = By.xpath("//button[span[text()='Save Changes']]");
    private static By cancelButton = By.xpath("//button[span[text()='Cancel']]");
    private static By fieldCheckboxByName = By.xpath("//td[@data-column-id='dataentryfieldnumber_name'][div[@data-field-id='dataentryfieldnumber_name']//span[@class='xwidget_readonly_value' and text()='%s']]/preceding-sibling::td[2]/input");
    private static By readonlyToggleByName = By.xpath("//td[@data-column-id='dataentryfieldnumber_name'][div[@data-field-id='dataentryfieldnumber_name']//span[@class='xwidget_readonly_value' and text()='%s']]/following-sibling::td[2]//input[@type='checkbox']");

    @Step("Type value to 'Product Description' input")
    public void typeValueToProductDescriptionInput(String value) {
        type(value, productDescriptionInput);
    }

    @Step("Type value to 'Initials' input")
    public void typeValueToInitialsInput(String value) {
        type(value, initialsInput);
    }

    public boolean isFieldReadonlyByName(String fieldName) {
        return getSelenideElement(readonlyToggleByName, fieldName).isSelected();
    }

    public void clickReadonlyToggleByFieldName(String fieldName) {
        clickIfExist(readonlyToggleByName, fieldName);
    }

    public boolean isFieldCheckboxDisabledByName(String fieldName) {
        return getElementAttributeValue("readonly", fieldCheckboxByName, fieldName).equals("readonly");
    }

    public void clickSaveChangesButton() {
        click(saveChangesButton);
    }

    public void clickCancelButton() {
        click(cancelButton);
    }

}
