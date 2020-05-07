package com.nymbus.actions.modalwindow;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
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
        Pages.debitCardModalWindow().selectAccount(debitCard.getAccounts().get(0));
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

    public void verifyDebitCardFilledValues(DebitCard debitCard) {
        Assert.assertEquals(
                Pages.debitCardModalWindow().getATMDailyLimitNumberAmt(),
                debitCard.getBinControl().getATMDailyDollarLimit(),
                "Value from 'ATM Daily Limit Number Amt' field is not equal to value from 'Bin Control' daily limit"
        );
        Assert.assertEquals(
                Pages.debitCardModalWindow().getATMDailyLimitNumberNbr(),
                debitCard.getBinControl().getATMTransactionLimit(),
                "Value from 'ATM Daily Limit Number Nbr' field is not equal to 'Bin Control' transaction limit"
        );
        Assert.assertEquals(
                Pages.debitCardModalWindow().getDebitPurchaseDailyLimitNumberAmt(),
                debitCard.getBinControl().getDBCDailyDollarLimit(),
                "Value from 'Debit Purchase Daily Limit Number Amt' field is not equal to value from 'Bin Control' daily limit"
        );
        Assert.assertEquals(
                Pages.debitCardModalWindow().getDebitPurchaseDailyLimitNumberNbr(),
                debitCard.getBinControl().getDBCTransactionLimit(),
                "Value from 'Debit Purchase Daily Limit Number Nbr' field is not equal to value from 'Bin Control' transaction limit"
        );

        String futureDate = DateTime.plusMonthsToCurrentDateWithLastDayOfMonth(debitCard.getBinControl().getCardLifeInMonths());
        Assert.assertEquals(
                Pages.debitCardModalWindow().getExpirationDateMonth(),
                futureDate.split(" ")[0],
                "Wrong 'Expiration Date' month"
        );
        Assert.assertEquals(
                Pages.debitCardModalWindow().getExpirationDateYear(),
                futureDate.split(" ")[1],
                "Wrong 'Expiration Date' year"
        );
    }

    public String getExpirationDate() {
        String year =  DateTime.getLastTwoDigitsOfYear(Pages.debitCardModalWindow().getExpirationDateYear());

        String month = DateTime.getMonthNumberByMonthName(Pages.debitCardModalWindow().getExpirationDateMonth());

        return year + month;
    }

    public String getCardNumber(int i) {
        Pages.clientDetailsPage().clickOnViewAllCardsButton();

        Pages.cardsManagementPage().clickEditButton(i);

        return Pages.debitCardModalWindow().getCardNumber();
    }
}