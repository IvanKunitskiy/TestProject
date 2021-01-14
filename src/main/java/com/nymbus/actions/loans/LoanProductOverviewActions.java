package com.nymbus.actions.loans;

import com.nymbus.actions.Actions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;

public class LoanProductOverviewActions {

    public void expandAllRows() {
        do {
            Pages.loanProductPage().clickLoadMoreResultsButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        while (Pages.loanProductPage().isLoadMoreResultsButtonVisible());
    }

    public void navigateToLoanProductOverviewPage() {
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();
        Pages.loansPage().clickViewAllLoanProductsLink();
    }

    public void searchTheLoanProduct(String queryText) {
        Pages.loanProductPage().clickSearchInput();
        Pages.loanProductPage().typeToSearchInput(queryText);
        Pages.loanProductPage().clickSearchButton();
        Pages.loanProductPage().waitForSearchButtonActive();
    }

    public boolean isLoanProductExists(String productDescription) {
        navigateToLoanProductOverviewPage();
        searchTheLoanProduct(productDescription);

        return Pages.loanProductPage().isRowWithProductDescriptionVisible(productDescription);
    }

    public void createLoanProduct(String loanProductName, String initials) {
        Pages.loanProductPage().clickAddNewButton();
        Pages.addNewLoanProductPage().typeValueToProductDescriptionInput(loanProductName);
        Pages.addNewLoanProductPage().typeValueToInitialsInput(initials);
        Actions.addNewLoanActions().disableReadonlyByFieldName("Loan Class Code");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Payment amount");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Payment Amount Type");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Payment Frequency");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Cycle Loan");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Interest Method");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Current effective rate");
        Actions.addNewLoanActions().disableReadonlyByFieldName("Days Base/Year Base");
        Pages.addNewLoanProductPage().clickSaveChangesButton();
        Pages.loanProductOverviewPage().waitForAddNewButtonClickable();
    }

    public void checkLoanProductExistAndCreateIfFalse(String loanProductName, String initials) {
        if (!isLoanProductExists(loanProductName)) {
            createLoanProduct(loanProductName, initials);
        }
    }

    public double getLoanProductEscrowPaymentValue(String productName) {
        navigateToLoanProductOverviewPage();
        expandAllRows();
        Pages.loanProductPage().clickLoanProductByName(productName);

        return Double.parseDouble(Pages.loanProductOverviewPage().getEscrowPaymentValue());
    }

}
