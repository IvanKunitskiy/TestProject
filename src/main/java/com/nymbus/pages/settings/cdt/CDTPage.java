package com.nymbus.pages.settings.cdt;

import com.nymbus.core.base.PageTools;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class CDTPage extends PageTools {
    private By searchLine = By.xpath("//input[@placeholder='Type to search']");
    private By searchButton = By.xpath("//button[span[text()='Search']]");
    private By noResultsDiv = By.xpath("//div[@class='xwidget_grid_nodata']");
    private By addNewLink = By.xpath("//a[text()='Add New']");
    private By results = By.xpath("//td[@class='xwidget_grid_cell xwidget_grid_cell_name']");


    @Step("Get results")
    public boolean checkResults(String name){
        waitForElementVisibility(results);
        List<String> elementsText = getElementsText(results);
        for (String s : elementsText) {
            if (s.equals(name)){
                return false;
            }
        }
        return true;
    }


    @Step("Search CDT temlate")
    public void searchCDTTemplate(String name){
        waitForElementVisibility(searchLine);
        type(name, searchLine);
        waitForElementClickable(searchButton);
        click(searchButton);
    }

    @Step("Check results")
    public boolean checkResultsDiv(){
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        return isElementVisible(noResultsDiv);
    }

    @Step("Click 'Add new' template")
    public void clickAddNew() {
        waitForElementClickable(addNewLink);
        click(addNewLink);
    }
}
