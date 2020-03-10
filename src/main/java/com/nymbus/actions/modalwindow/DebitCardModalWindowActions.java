package com.nymbus.actions.modalwindow;

import com.nymbus.models.client.other.account.type.CHKAccount;
import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class DebitCardModalWindowActions {
    public void selectBinControl(DebitCard debitCard) {
        Pages.debitCardModalWindow().clickOnBinNumberInputField();
        Pages.debitCardModalWindow().clickOnBinNumberDropdownValue(debitCard.getBinControl().getBinNumber());
        Assert.assertFalse(Pages.debitCardModalWindow().getDescriptionInputFieldValue().isEmpty());
    }

    public void fillDebitCard(DebitCard debitCard) {
        selectBinControl(debitCard);
        Pages.debitCardModalWindow().clickOnNextButton();

        Pages.debitCardModalWindow().typeToCardNumberInputField(debitCard.getCardNumber());
        Pages.debitCardModalWindow().typeToNameOnCardInputField(debitCard.getNameOnCard());
        Pages.debitCardModalWindow().typeToSecondLineEmbossingInputField(debitCard.getSecondLineEmbossing());
        Pages.debitCardModalWindow().selectAccount(((CHKAccount) debitCard.getAccounts().get(0)).getAccountNumber());
//        Pages.debitCardModalWindow().selectCardDesign(debitCard.getCardDesign()); // TODO: Need for find where I can fill card designs
        Pages.debitCardModalWindow().selectCardStatus(debitCard.getCardStatus());
        Pages.debitCardModalWindow().clickOnYesButton();
        Pages.debitCardModalWindow().typeToPinOffsetInputField(debitCard.getPinOffset());
        /*Pages.debitCardModalWindow().typeToATMDailyLimitNumberAmtInputField(debitCard.getATMDailyDollarLimit());          // This values will fill automatically
        Pages.debitCardModalWindow().typeToATMDailyLimitNumberNbrInputField(debitCard.getATMTransactionLimit());            // Values described on Bin Control settings
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberAmtInputField(debitCard.getDBCDailyDollarLimit());
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberNbrInputField(debitCard.getDBCTransactionLimit());*/
        Pages.debitCardModalWindow().selectTransactionTypeAllowedSelect(debitCard.getTranslationTypeAllowed());
        Pages.debitCardModalWindow().setChargeForCardReplacementToggle(debitCard.isChargeForCardReplacement());
        Pages.debitCardModalWindow().setAllowForeignTransactionsToggle(debitCard.isAllowForeignTransactions());
    }
}
