package com.nymbus.pages.settings.tellerlocation;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class TellerLocationOverviewPage extends PageTools {

    private By tableRowByBranchNameSelector = By.xpath("//tr[td[@data-column-id='name' and contains(text(), '%s')]]");

    public void clickRowByBranchName(String branchName) {
        click(tableRowByBranchNameSelector, branchName);
    }
}
