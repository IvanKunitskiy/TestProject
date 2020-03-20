package com.nymbus.actions.clients.maintenance;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.other.account.type.Account;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.other.MaintenanceHistoryField;
import com.nymbus.pages.Pages;
import com.nymbus.util.Mapper;
import org.testng.Assert;

import java.util.Date;

public class MaintenanceHistoryPageActions {
    public void verifyMaintenanceHistoryFields(DebitCard debitCard, Account account) {
        Pages.maintenanceHistoryPage().clickOnViewMoreButton();
        Pages.maintenanceHistoryPage().clickOnViewMoreButton();
        Pages.maintenanceHistoryPage().clickOnViewMoreButton();
        Pages.maintenanceHistoryPage().clickOnViewMoreButton();

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
        /*Assert.assertEquals( // TODO: Weird thing... Instead of number displays Card Description
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.BIN_NUMBER),
                debitCard.getBinControl().getBinNumber(),
                "Bin Number is not equal"
        );*/
        /*Assert.assertEquals( // TODO: Need to set before...
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_NUMBER),
                debitCard.getCardNumber(),
                "Card Number is not equal"
        );*/
        /*Assert.assertEquals( // TODO: Need to ask what is this
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_TRANSMITTED_TO_PROCESSORS),
                debitCard.getBinControl().getBinNumber(),
                "Card Transmitted To Processors is not equal"
        );*/
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CARD_TYPE),
                "Debit Card",
                "Card Type is not equal"
        );
        /*Assert.assertEquals( // TODO: Need to ask what is this
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CHARGE_FOR_CARD_REPLACEMENT),
                debitCard.getBinControl().getChargeForCardReplacement(),
                "Charge for Card Replacement is not equal"
        );*/
        Assert.assertEquals(
                 Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_CREATED),
                DateTime.parseDate(new Date(), "MM/dd/yyyy"),
                "Date Created is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_TRANSMITTED_TO_PROCESSORS),
                DateTime.parseDate(new Date(), "MM/dd/yyyy"),
                "Date Transmitted To Processors is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.DATE_EFFECTIVE),
                DateTime.parseDate(new Date(), "MM/dd/yyyy"),
                "Date Effective is not equal"
        );
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.EXPIRATION_DATE),
                DateTime.plusMonthsToCurrentDateWithLastDayOfMonth(debitCard.getBinControl().getCardLifeInMonths(), "MM/dd/yyyy"),
                "Expiration Date is not equal"
        );
        /*Assert.assertEquals( // TODO: Need to ask what is this
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.INSTANT_ISSUE),
                debitCard.getInstantIssue(),
                "Instant Issue is not equal"
        );*/
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.NAME_ON_CARD),
                debitCard.getNameOnCard(),
                "Name on Card is not equal"
        );
        /*Assert.assertEquals( // TODO: Weird thing... Instead of client number displays fill name
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.CLIENT_NUMBER),
                ((CHKAccount) account).getAccountNumber(),
                "Client Number is not equal"
        );*/
        Assert.assertEquals(
                Pages.maintenanceHistoryPage().getNewValueByFieldName(MaintenanceHistoryField.PIN_OFFSET),
                debitCard.getPinOffset(),
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
}
