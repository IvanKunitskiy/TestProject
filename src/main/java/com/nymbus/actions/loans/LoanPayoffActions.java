package com.nymbus.actions.loans;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class LoanPayoffActions {

    public void setBalanceUsed(BalanceUsed balanceUsed) {
        Pages.loanPayoffPrepaymentPenaltyModalPage().clickBalanceUsedSelectorButton();
        Pages.loanPayoffPrepaymentPenaltyModalPage().clickBalanceUsedSelectorOption(balanceUsed.getBalanceUsed());
    }

    public void setPenaltyCalculationType(PenaltyCalculationType penaltyCalculationType) {
        Pages.loanPayoffPrepaymentPenaltyModalPage().clickPenaltyCalculationTypeSelectorButton();
        Pages.loanPayoffPrepaymentPenaltyModalPage().clickPenaltyCalculationTypeSelectorOption(penaltyCalculationType.getPenaltyCalculationType());
    }

    public String getPrepaymentPenaltyAmount() {
        while (Pages.loanPayoffPrepaymentPenaltyModalPage().getPrepaymentPenaltyAmount().equals("")) {
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        return Pages.loanPayoffPrepaymentPenaltyModalPage().getPrepaymentPenaltyAmount();
    }
}
