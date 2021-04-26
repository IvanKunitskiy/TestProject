package com.nymbus.actions.loans;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;

public class LoanReserveActions {

    public void expandAllRows() {
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        while (Pages.loansReservePage().isLoadMoreResultsButtonVisible()){
            Pages.loansReservePage().clickLoadMoreResultsButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
    }

    public boolean isLoanReserveExists(String reserveCode) {
        navigateToLoanReserveOverviewPage();
        expandAllRows();

        return Pages.loansReservePage().isRowWithReserveCodeVisible(reserveCode);
    }

    public void navigateToLoanReserveOverviewPage() {
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansReservePage().waitForLoanReservePageLoaded();
        Pages.loansPage().clickViewAllLoanReserves();
        Pages.loansReservePage().waitForLoanReservePageLoaded();
    }

    public void inputLoanReserveFields(Account loanAccount, String origAmount, String term){
        Pages.reservePremiumProcessingModalPage().inputEffectiveDate(loanAccount.getDateOpened());
        Pages.reservePremiumProcessingModalPage().inputOriginalAmount(origAmount);
        Pages.reservePremiumProcessingModalPage().inputReserveId("autotest");
        Pages.reservePremiumProcessingModalPage().inputTermInMonth(term);
        Pages.reservePremiumProcessingModalPage().inputDeferringStartDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),
                2));
        Pages.reservePremiumProcessingModalPage().inputGLAccount("Dumm");
        Pages.reservePremiumProcessingModalPage().clickCommitButton();
    }

}
