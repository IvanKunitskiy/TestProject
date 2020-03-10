package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.other.account.type.CHKAccount;
import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.models.generation.client.other.DebitCardFactory;
import com.nymbus.models.generation.settings.BinControlFactory;
import com.nymbus.models.settings.bincontrol.BinControl;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import com.nymbus.util.DateTime;
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
    private CHKAccount chkAccount;

    @BeforeMethod
    public void preCondition() {
        // TODO: Refactor to using builder
        DebitCardFactory debitCardFactory = new DebitCardFactory();
        BinControlFactory binControlFactory = new BinControlFactory();

        binControl = binControlFactory.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        debitCard = debitCardFactory.getDebitCard();
        debitCard.setBinControl(binControl);

        chkAccount = new CHKAccount();
        chkAccount.setAccountNumber("12400584822");

        debitCard.getAccounts().add(chkAccount);

        navigateToUrl(Constants.URL);
    }

    @Severity(CRITICAL)
    @Test(description = "C22552, Clients Profile: Create new debit debitcard")
    public void createNewDebitCard() throws InterruptedException {
        Thread.sleep(5_000);

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
        Pages.clientDetailsPage().clickOnNewDebitCardButton();

        LOG.info("Step 5: Select Bin Number from the precondition and make sure its description is populated automatically");
        LOG.info("Step 6: Click [Next] button");
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);

        LOG.info("Step 7: Look at the ATM Daily Limit Number (Amount and Number), Debit Purchase Daily Limit Number (Amount and Number)");
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

        LOG.info("Step 8: Look at the Expiration Date field");
        String futureDate = DateTime.plusMonthsToCurrentDate(binControl.getCardLifeInMonths());
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

        LOG.info("Step 9: Select account from preconditions in 'Account #1' field, specify all the displayed fields with valid data and click [Save and Finish] button");
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();

        LOG.info("Step 10: Click [View all cards] button in Cards Management tile and verify the displayed info");
        Pages.clientDetailsPage().clickOnViewAllCardsButton();

        LOG.info("Step 11: Go to the Accounts tab and search for the account that was assigned to the card. Open account on Maintenance-> Maintenance History page");
    }
}
