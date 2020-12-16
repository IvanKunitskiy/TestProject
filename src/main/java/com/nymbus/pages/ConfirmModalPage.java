package com.nymbus.pages;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ConfirmModalPage extends PageTools {

    private final By yesButton = By.xpath("//button[span[text()='Yes']]");
    private final By noButton = By.xpath("//button[span[text()='No']]");
    private final By okButton = By.xpath("//button[contains(string(),'OK')]");
    private final By reprintCheck = By.xpath("//p[contains(string(),'Reprint check')]");
    private final By isCheck = By.xpath("//p[contains(string(),'Is check')]");

    @Step("Check reprint check visible")
    public boolean checkReprintButton(){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(reprintCheck);
    }

    @Step("Check reprint check visible")
    public String getReprintCheckNumber(){
        String text = getElementText(reprintCheck);
        return text.substring(text.indexOf("#")+1);
    }

    @Step("Check is check visible")
    public boolean checkIsCheck(){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(isCheck);
    }

    @Step("Click on 'Yes' button")
    public void clickYes() {
        waitForElementClickable(yesButton);
        jsClick(yesButton);
    }

    @Step("Click on 'No' button")
    public void clickNo() {
        waitForElementClickable(noButton);
        jsClick(noButton);
    }

    @Step("Click on 'OK' button")
    public void clickOk() {
        waitForElementClickable(okButton);
        jsClick(okButton);
    }
}
