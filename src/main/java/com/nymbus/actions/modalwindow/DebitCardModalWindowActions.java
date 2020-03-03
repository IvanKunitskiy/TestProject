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

    }
}
