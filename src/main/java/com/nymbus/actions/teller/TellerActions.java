package com.nymbus.actions.teller;

import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;

public class TellerActions {

    public void assignAmountToAccount(Account chkAccount, String amountOfHundredBillsToCashIn, String destinationAmountValue) {
        Pages.aSideMenuPage().clickTellerMenuItem();
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerPage().clickCashInButton();
        Pages.cashInModalPage().waitCashInModalWindow();
        Pages.cashInModalPage().typeToHundredsItemCountInputField(amountOfHundredBillsToCashIn);
        Pages.cashInModalPage().clickOkButton();
        Pages.tellerPage().clickMiscCreditButton();
        Pages.tellerPage().typeDestinationAccountNumber(1, chkAccount.getAccountNumber());
        Pages.tellerPage().clickDestinationAccountSuggestionOption(chkAccount.getAccountNumber());
        Pages.tellerPage().waitForCreditTransferCodeVisible();
        Pages.tellerPage().typeDestinationAmountValue(1, destinationAmountValue);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductor().waitModalWindow();
        Pages.verifyConductor().clickVerifyButton();
        Pages.transactionCompleted().waitModalWindow();
        Pages.transactionCompleted().clickCloseButton();
    }
}
