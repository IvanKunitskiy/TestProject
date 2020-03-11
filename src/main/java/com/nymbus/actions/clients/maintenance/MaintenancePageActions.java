package com.nymbus.actions.clients.maintenance;

import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.pages.Pages;
import com.nymbus.util.DateTime;
import org.testng.Assert;

public class MaintenancePageActions {
    public void verifyDebitCardDetails(DebitCard debitCard) {
        Assert.assertTrue(
                Pages.clientDetailsPage().isCardListDisplayed(),
                "Card list is not displayed"
        );
        Assert.assertTrue(
                Pages.clientDetailsPage().getDebitCardNumber().contains("XXXX XXXX XXXX"),
                "Debit card is not masked"
        );
        Assert.assertFalse(
                Pages.clientDetailsPage().getDebitCardNumber().substring(15).contains("XXXX"),
                "Last 4 number is masked but should not"
        );

        Assert.assertEquals(
                Pages.clientDetailsPage().getNameOfCard(),
                debitCard.getNameOnCard(),
                "Name of card is not equal"
        );
        Assert.assertEquals(
                Pages.clientDetailsPage().getSecondLineEmbossing(),
                debitCard.getSecondLineEmbossing(),
                "Second Line Embossing is not equal"
        );
        Assert.assertEquals(
                Pages.clientDetailsPage().getExpirationDate(),
                DateTime.plusMonthsToCurrentDateWithLastDayOfMonth(48, "MM/dd/yyyy"),
                "Expiration Date is not equal"
        );
        Assert.assertEquals(
                Pages.clientDetailsPage().getCardStatus(),
                debitCard.getCardStatus().getCardStatus(),
                "Card Status is not equal"
        );
    }
}
