package com.nymbus.actions.clients.maintenance;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Mapper;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.other.MaintenanceHistoryField;
import com.nymbus.pages.Pages;
import org.testng.Assert;

import java.util.Date;

public class MaintenanceHistoryPageActions {
    public void verifyMaintenanceHistoryFields(DebitCard debitCard, Account account) {
        expandAllRows();

        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.ALLOW_FOREIGN_TRANSACTIONS),
                Mapper.mapAllowForeignTransactions(debitCard.isAllowForeignTransactions()),
                "Allow Foreign Transactions is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.TRANSACTION_TYPE_ALLOWED),
                debitCard.getTranslationTypeAllowed().getTranslationTypeAllowed(),
                "Transaction Type Allowed is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.ATM_DAILY_LIMIT_AMOUNT),
                debitCard.getATMDailyDollarLimit(),
                "ATM Daily Limit Amount is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.ATM_DAILY_LIMIT_NUMBER),
                debitCard.getATMTransactionLimit(),
                "ATM Daily Limit Number is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.BIN_NUMBER),
                debitCard.getBinControl().getBinNumber(),
                "Bin Number is not equal"
        );
        String cardNumber = debitCard.getCardNumber();
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_NUMBER),
                cardNumber.substring(cardNumber.length() - 4),
                "Card Number is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_TRANSMITTED_TO_PROCESSORS),
                "1", // Value should be always "1" for a new card. Shows amount of issued cards
                "Card Transmitted To Processors is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_TYPE),
                "Debit Card",
                "Card Type is not equal"
        );
        String chargeForCard = debitCard.isChargeForCardReplacement() ? "1" : "0";
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CHARGE_FOR_CARD_REPLACEMENT),
                chargeForCard,
                "Charge for Card Replacement is not equal"
        );
        Assert.assertEquals(
                 Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_CREATED),
                 DateTime.getLocalDateWithPattern("MM/dd/yyyy"),
                "Date Created is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_TRANSMITTED_TO_PROCESSORS),
                DateTime.getLocalDateWithPattern("MM/dd/yyyy"),
                "Date Transmitted To Processors is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_EFFECTIVE),
                debitCard.getDateEffective(),
                "Date Effective is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.EXPIRATION_DATE),
                DateTime.getLocalDatePlusMonthsWithPatternAndLastDay(debitCard.getDateEffective(), debitCard.getBinControl().getCardLifeInMonths(), "MM/dd/yyyy"),
                "Expiration Date is not equal"
        );
        String cardStatus = debitCard.getCardStatus().getCardStatus();
        String instantIssue =  cardStatus.equals("Active") ? "1" : "0"; // 1 - if the card status is 'Active' and 0 - for 'Waiting to become active'
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.INSTANT_ISSUE),
                instantIssue,
                "Instant Issue is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.NAME_ON_CARD),
                debitCard.getNameOnCard(),
                "Name on Card is not equal"
        );
        String[] clientNumber = debitCard.getClientNumber().split(" ");
        String debitCardClientNumber = clientNumber[0] + " " + clientNumber[1];
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CLIENT_NUMBER),
                debitCardClientNumber,
                "Client Number is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.PIN_OFFSET),
                String.valueOf(debitCard.getPinOffset()),
                "Pin Offset is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DEBIT_PURCHASE_DAILY_LIMIT_AMOUNT),
                debitCard.getDBCDailyDollarLimit(),
                "Debit Purchase Daily Limit Amount is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DEBIT_PURCHASE_DAILY_LIMIT_NUMBER),
                debitCard.getDBCTransactionLimit(),
                "Debit Purchase Daily Limit Number is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_STATUS),
                debitCard.getCardStatus().getCardStatus(),
                "Card Status is not equal"
        );
    }

    public void expandAllRows() {
        do {
            Pages.accountMaintenancePage().clickViewMoreButton();
            SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        }
        while (Pages.accountMaintenancePage().isMoreButtonVisible());
    }
}
