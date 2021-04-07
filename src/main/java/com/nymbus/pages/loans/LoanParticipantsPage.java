package com.nymbus.pages.loans;

import com.nymbus.core.base.PageTools;
import org.openqa.selenium.By;

public class LoanParticipantsPage extends PageTools {

    private final By loanParticipantNameByIndex = By.xpath("//table//tr[contains(@class, 'xwidget_grid_row')][%s]/td[@data-column-id='name']");
    private final By loanParticipantName= By.xpath("//table//tr[contains(@class, 'xwidget_grid_row')]/td[@data-column-id='name']");

    public String getLoanParticipantNameByIndex(int index) {
        return getElementText(loanParticipantNameByIndex, index);
    }

    public int getLoanParticipantCount() {
        return getElementsText(loanParticipantName).size();
    }
}
