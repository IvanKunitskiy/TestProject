package com.nymbus.pages.settings.callclasscodes;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class EditCallCodePage extends PageTools {

    private By productTypeSelector = By.xpath("//div[@data-field-id='accounttype']//input[@type='text']");
    private By productTypeSelectorOptionByName = By.xpath("//ul[@id='ui-id-6']//li/a[contains(text(), '%s')]");
    private By productTypeSelectorOptionByIndex = By.xpath("//ul[@id='ui-id-6']//li[%s]/a");
    private By saveChangesButton = By.xpath("//button[span[text()='Save Changes']]");

    @Step("Click the 'Product Type' selector button")
    public void clickProductTypeSelectorButton() {
        click(productTypeSelector);
    }

    @Step("Type {productType} to 'Product Type' field")
    public void typeToProductTypeField(String productType) {
        type(productType, productTypeSelector);
    }

    @Step("Click the 'Product Type' selector option by name")
    public void clickProductTypeSelectorOptionByName(String productTypeOption) {
        waitForElementClickable(productTypeSelectorOptionByName,  productTypeOption);
        click(productTypeSelectorOptionByName,  productTypeOption);
    }

    @Step("Click the 'Product Type' selector option by index")
    public void clickProductTypeSelectorOptionByIndex(int index) {
        waitForElementClickable(productTypeSelectorOptionByIndex,  index);
        click(productTypeSelectorOptionByIndex,  index);
    }

    @Step("Click the 'Save changes' button")
    public void clickSaveChangesButton() {
        click(saveChangesButton);
    }
}
