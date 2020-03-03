package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.models.generation.client.other.DebitCardFactory;
import com.nymbus.models.settings.bincontrol.BinControl;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Dmytro")
public class C22552_CreateNewDebitCard extends BaseTest {
    private BinControl binControl;
    private DebitCard debitCard;

    @BeforeMethod
    public void preCondition() {
        DebitCardFactory debitCardFactory = new DebitCardFactory();

        binControl = new BinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        debitCard = debitCardFactory.getDebitCard();
        debitCard.setBinControl(binControl);

        navigateToUrl(Constants.URL);
    }

    @Severity(CRITICAL)
    @Test(description = "C22552, Clients Profile: Create new debit debitcard")
    public void createNewDebitCard() throws InterruptedException {
        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        LOG.info("Step 2: Search for any individualClient and open his profile on the Maintenance tab");
        Pages.clientsPage().typeToClientsSearchInputField("ndjws");

        Assert.assertEquals(Pages.clientsPage().getLookupResultsCount(), 1);
        Assert.assertFalse(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        Pages.clientsPage().clickOnViewAccountButtonByValue("ndjws");

        LOG.info("Step 3: Open client profile on the Maintenance tab");
        Pages.clientDetailsPage().clickOnMaintenanceTab();

        LOG.info("Step 4: Click [New Debit Card] button in Cards Management tile");
        Pages.debitCardModalWindow().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().addNewDebitCard(debitCard);

        Thread.sleep(3_000);
    }
}
