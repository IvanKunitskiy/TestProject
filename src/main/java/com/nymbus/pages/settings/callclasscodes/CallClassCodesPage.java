package com.nymbus.pages.settings.callclasscodes;

import com.nymbus.core.base.PageTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CallClassCodesPage extends PageTools {

    private By searchInput = By.xpath("//input[@type='text' and @placeholder='Type to search']");
    private By searchButton = By.xpath("//div[@data-fieldname='searchButton']/div[@data-field-id='searchButton']//button[span[text()='Search']]");
    private By rowByCallCodeSelector = By.xpath("//table/tbody/tr[td[@data-column-id='code' and text()='%s']]");

    @Step("Wait for row with 'Call Code' visible")
    public void waitForCallCodeVisible(String callCode) {
        waitForElementVisibility(rowByCallCodeSelector, callCode);
    }

    @Step("Click row with 'Call Code' value")
    public void clickRowByCallCode(String callCode) {
        waitForElementClickable(rowByCallCodeSelector, callCode);
        click(rowByCallCodeSelector, callCode);
    }

    @Step("Type {text} to search field")
    public void typeToSearchField(String text) {
        waitForElementClickable(searchInput);
        type(text, searchInput);
    }

    @Step("Click the 'Search' button")
    public void clickSearchButton() {
        waitForElementClickable(searchButton);
        click(searchButton);
    }
}
