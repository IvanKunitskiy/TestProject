package com.nymbus.pages.accounts;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountCommercialAnalysisPage extends PageTools {

    private By tableResults = By.xpath("//section[@ui-view='results']/table/tbody");
    private By dateByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[1]/span");
    private By userByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[2]/span");
    private By chargeCodeByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[3]/span");
    private By descriptionByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[4]/span");
    private By rateTypeByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[5]/span");
    private By volumeByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[6]/span");
    private By exportedByRowIndex = By.xpath("//section[@ui-view='results']/table/tbody/tr[%s]/td[7]/span/dn-switch/div/div/span[2]");
    private By tableRow = By.xpath("//section[@ui-view='results']/table/tbody/tr");
    private By dateToInput = By.xpath("//input[@data-test-id='field-dateto']");
    private By filterButton = By.xpath("//button[text()='Filter']");

    @Step("Wait for table results")
    public void waitForTableResults() {
        waitForElementVisibility(tableResults);
    }

    @Step("Get 'Date' from table by row index")
    public String getDateFromTableByRowIndex(int index) {
        return getElementText(dateByRowIndex, 1).trim();
    }

    @Step("Get 'User' from table by row index")
    public String getUserByRowIndex(int index) {
        return getElementText(userByRowIndex, 1).trim();
    }

    @Step("Get 'Charge Code' from table by row index")
    public String getChargeCodeByRowIndex(int index) {
        return getElementText(chargeCodeByRowIndex, 1).trim();
    }

    @Step("Get 'Description' from table by row index")
    public String getDescriptionByRowIndex(int index) {
        return getElementText(descriptionByRowIndex, 1).trim();
    }

    @Step("Get 'Rate Type' from table by row index")
    public String getRateTypeByRowIndex(int index) {
        return getElementText(rateTypeByRowIndex, 1).trim();
    }

    @Step("Get 'Volume' from table by row index")
    public String getVolumeFromTableByRowIndex(int index) {
        return getElementText(volumeByRowIndex, 1).trim();
    }

    @Step("Get 'Date' from table by row index")
    public String getExportedByRowIndex(int index) {
        return getElementText(exportedByRowIndex, 1).trim();
    }

    @Step("Get records count from table")
    public int getRecordsCount() {
        return getElements(tableRow).size();
    }

    @Step("Set 'Date To' filter")
    public void setDateTo(String date) {
        waitForElementClickable(dateToInput);
        typeWithoutWipe("", dateToInput);
        SelenideTools.sleep(1);
        typeWithoutWipe(date, dateToInput);
    }

    @Step("Click the 'Filter' button")
    public void clickFilterButton() {
        waitForElementClickable(filterButton);
        click(filterButton);
    }
}
