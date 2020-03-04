package com.nymbus.actions.modalwindow;

import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class DebitCardModalWindowActions {
    public void selectBinControl(DebitCard debitCard) {
        Pages.debitCardModalWindow().clickOnBinNumberInputField();
        Pages.debitCardModalWindow().clickOnBinNumberDropdownValue(debitCard.getBinControl().getBinNumber());
        Assert.assertFalse(Pages.debitCardModalWindow().getDescriptionInputFieldValue().isEmpty());
    }

    public void addNewDebitCard(DebitCard debitCard) {
        selectBinControl(debitCard);
        Pages.debitCardModalWindow().clickOnNextButton();

        Pages.debitCardModalWindow().typeToCardNumberInputField(debitCard.getCardNumber());
        Pages.debitCardModalWindow().typeToNameOnCardInputField(debitCard.getNameOnCard());
        Pages.debitCardModalWindow().typeToSecondLineEmbossingInputField(debitCard.getSecondLineEmbossing());
//        Pages.debitCardModalWindow().typeToAccountInputField(debitCard.getAccounts().get(0)); // TODO: Need to think about this field
//        Pages.debitCardModalWindow().selectCardDesign(debitCard.getCardDesign());
        Pages.debitCardModalWindow().selectCardStatus(debitCard.getCardStatus());
        Pages.debitCardModalWindow().clickOnYesButton();
        Pages.debitCardModalWindow().typeToPinOffsetInputField(debitCard.getPinOffset());
//        Pages.debitCardModalWindow().typeToDateEffectiveInputField(debitCard.getDateEffective()); // TODO: Contains value by the default. Maybe solution is the set current day on 'DebitCardFactory' class
        Pages.debitCardModalWindow().typeToATMDailyLimitNumberAmtInputField(String.valueOf(debitCard.getATMDailyDollarLimit()));
        Pages.debitCardModalWindow().typeToATMDailyLimitNumberNbrInputField(String.valueOf(debitCard.getATMTransactionLimit()));
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberAmtInputField(String.valueOf(debitCard.getDBCDailyDollarLimit()));
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberNbrInputField(String.valueOf(debitCard.getDBCTransactionLimit()));
    }
}
