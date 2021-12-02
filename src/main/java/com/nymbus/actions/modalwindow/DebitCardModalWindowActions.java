package com.nymbus.actions.modalwindow;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class DebitCardModalWindowActions {
    public void selectBinControl(DebitCard debitCard) {
        Pages.debitCardModalWindow().clickOnBinNumberInputField();
        List<String> binValues =  Pages.debitCardModalWindow().getBinList();
        Assert.assertTrue(binValues.size() > 0, "There are no 'Bin' options available");
        debitCard.getBinControl().setBinNumber(binValues.get(new Random().nextInt(binValues.size())).trim());
        Pages.debitCardModalWindow().clickOnBinNumberDropdownValue(debitCard.getBinControl().getBinNumber());
        Assert.assertFalse(Pages.debitCardModalWindow().getDescriptionInputFieldValue().isEmpty());
    }

    public void fillDebitCard(DebitCard debitCard) {
        selectBinControl(debitCard);
        Pages.debitCardModalWindow().clickOnNextButton();

        Pages.debitCardModalWindow().typeToCardNumberInputField(debitCard.getCardNumber());
        Pages.debitCardModalWindow().typeToNameOnCardInputField(debitCard.getNameOnCard());
        Pages.debitCardModalWindow().typeToSecondLineEmbossingInputField(debitCard.getSecondLineEmbossing());
        selectAccount(debitCard.getAccounts());
        setCardDesign(debitCard.getCardDesign().getCardDesign());
        Pages.debitCardModalWindow().selectCardStatus(debitCard.getCardStatus());
        // Pages.debitCardModalWindow().clickOnYesButton();
        Pages.debitCardModalWindow().typeToPinOffsetInputField(debitCard.getPinOffset());
        Pages.debitCardModalWindow().typeToATMDailyLimitNumberAmtInputField(debitCard.getATMDailyDollarLimit());          // This values will fill automatically
        Pages.debitCardModalWindow().typeToATMDailyLimitNumberNbrInputField(debitCard.getATMTransactionLimit());            // Values described on Bin Control settings
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberAmtInputField(debitCard.getDBCDailyDollarLimit());
        Pages.debitCardModalWindow().typeToDebitPurchaseDailyLimitNumberNbrInputField(debitCard.getDBCTransactionLimit());
        Pages.debitCardModalWindow().selectTransactionTypeAllowedSelect(debitCard.getTranslationTypeAllowed());
        Pages.debitCardModalWindow().setChargeForCardReplacementToggle(debitCard.isChargeForCardReplacement());
        Pages.debitCardModalWindow().setAllowForeignTransactionsToggle(debitCard.isAllowForeignTransactions());
    }

    public void fillDebitCard(DebitCard debitCard, boolean isNameTypeWithJs) {
        selectBinControl(debitCard);
        Pages.debitCardModalWindow().clickOnNextButton();
        Pages.debitCardModalWindow().typeToCardNumberInputField(debitCard.getCardNumber());
        if (isNameTypeWithJs) {
            Pages.debitCardModalWindow().typeToNameOnCardInputFieldWithJs(debitCard.getNameOnCard());
        } else {
            Pages.debitCardModalWindow().typeToNameOnCardInputField(debitCard.getNameOnCard());
        }
        Pages.debitCardModalWindow().typeToSecondLineEmbossingInputField(debitCard.getSecondLineEmbossing());
        selectAccount(debitCard.getAccounts());
        setCardDesign(debitCard.getCardDesign().getCardDesign());
        Pages.debitCardModalWindow().selectCardStatus(debitCard.getCardStatus());
        //Pages.debitCardModalWindow().clickOnYesButton();
        Pages.debitCardModalWindow().typeToPinOffsetInputField(debitCard.getPinOffset());
        Pages.debitCardModalWindow().selectTransactionTypeAllowedSelect(debitCard.getTranslationTypeAllowed());
        Pages.debitCardModalWindow().setChargeForCardReplacementToggle(debitCard.isChargeForCardReplacement());
        Pages.debitCardModalWindow().setAllowForeignTransactionsToggle(debitCard.isAllowForeignTransactions());
    }

    private void setCardDesign(String design) {
        String environment = System.getProperty("domain", "dev6");
        if (environment.equals("dev6") || environment.equals("dev12")) {
            Pages.debitCardModalWindow().selectCardDesign(design);
        }
    }

    private void selectAccount(List<String> accounts) {
        int offset = Pages.debitCardModalWindow().getAccountRowsCount();
        int accountsCount = accounts.size();
        for (int i = 0; i < accountsCount; ++i) {
            Pages.debitCardModalWindow().selectAccountFromDropDownByIndex(accounts.get(i), i);
            if (accountsCount > offset && (i < (accountsCount - offset)))  {
                Pages.debitCardModalWindow().clickOnAddAccountButton();
            }
        }
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

        String dateEffective = Pages.debitCardModalWindow().getDateEffective();
        TestRailAssert.assertTrue(dateEffective.equals(debitCard.getDateEffective()), new CustomStepResult("Effective date is true",
                "Effective date is wrong"));

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

    public void setExpirationDateAndCardNumber(NonTellerTransactionData nonTellerTransactionData, int index) {
        Pages.clientDetailsPage().clickOnViewAllCardsButton();
        Pages.cardsManagementPage().clickEditButton(index);
        nonTellerTransactionData.setCardNumber(Pages.debitCardModalWindow().getCardNumber());
        nonTellerTransactionData.setExpirationDate(getExpirationDate());
        Pages.debitCardModalWindow().clickOnCancelButton();
    }

    public String getCardNumber(int i) {
        Pages.clientDetailsPage().clickOnViewAllCardsButton();

        Pages.cardsManagementPage().clickEditButton(i);

        String cardNumber = Pages.debitCardModalWindow().getCardNumber();

        Pages.debitCardModalWindow().clickOnCancelButton();

        return cardNumber;
    }

    public void goToCardHistory(int index) {
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnViewAllCardsButton();
        Pages.cardsManagementPage().clickViewHistoryButton(index);
    }

    public void setDebitCardWithBinControl(DebitCard debitCard, BinControl binControl) {
        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());
    }

    public double getTransactionAmount(int index) {
        String amount = Pages.cardsManagementPage().getTransactionAmount(index);

        return Double.parseDouble(amount);
    }

    public double getTransactionFeeAmount(int index) {
        String amount = Pages.cardsManagementPage().getTransactionFeeAmount(index);

        return Double.parseDouble(amount);
    }
}