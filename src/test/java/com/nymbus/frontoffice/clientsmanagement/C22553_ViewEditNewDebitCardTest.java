package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.account.ProductType;
import com.nymbus.newmodels.client.other.account.type.CHKAccount;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.other.DebitCardFactory;
import com.nymbus.newmodels.generation.settings.BinControlFactory;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.settings.product.Product;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22553_ViewEditNewDebitCardTest extends BaseTest {

//    private User user;
    private IndividualClient client;
    private DebitCard debitCard;
    private BinControl binControl;
    private CHKAccount chkAccount;
    private String clientID;

    @BeforeMethod
    public void preCondition() {
        // TODO: Rewrite preconditions after normalizing new models. User, debit card and chk account should be created in precondition
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up debit card and account
        DebitCardFactory debitCardFactory = new DebitCardFactory();
        BinControlFactory binControlFactory = new BinControlFactory();

        binControl = binControlFactory.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        debitCard = debitCardFactory.getDebitCard();
        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());

        chkAccount = new CHKAccount();
        chkAccount.setAddNewOption("Account");
        chkAccount.setProductType(ProductType.CHK_ACCOUNT);
        chkAccount.setProduct(Product.BASIC_BUSINESS_CHECKING);
        chkAccount.setAccountNumber(String.valueOf(Generator.genLong(10000000000L, 922337203685L)));
        chkAccount.setAccountTitle(Generator.genString(10));
        chkAccount.setOptInOutDate("01/01/2020");
        chkAccount.setDateOpened("02/27/2020");
        chkAccount.setInterestRate(Generator.getRandomFormattedDecimalStringValue("###.####"));
        chkAccount.setEarningCreditRate(Generator.getRandomStringNumber(3));

        debitCard.getAccounts().add(chkAccount.getAccountNumber());

        // Log in and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();

        //Create debit card and logout
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22553, View / edit new debit card")
    public void viewEditNewDebitCard() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);

        logInfo("Step 3: Open Clients profile on Maintenance Tab");
        Pages.accountNavigationPage().clickMaintenanceTab();

        logInfo("Step 4: Click [View All Cards] button in Cards management section");
        Pages.maintenancePage().clickOnViewAllCardsButton();

        logInfo("Step 5: Look through the records and click [Edit] button next to the card from the preconditions");
        Pages.maintenancePage().clickEditButtonInListByNameOnCard(debitCard.getNameOnCard());
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowVisibility();

        logInfo("Step 6: Change some values , e.g. Pin Offset / ATM Daily Limit Number / Add additional account etc");
        debitCard.setPinOffset(6666); // new value assigned for editing
        Pages.debitCardModalWindow().typeToPinOffsetInputField(debitCard.getPinOffset());

        logInfo("Step 7: Click [Save] button");
        Pages.debitCardModalWindow().clickOnSaveButton();

        logInfo("Step 8: Click [Edit] button again");
        Pages.maintenancePage().clickEditButtonInListByNameOnCard(debitCard.getNameOnCard());
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowVisibility();
        Assert.assertEquals(Pages.debitCardModalWindow().getPinOffset(), debitCard.getPinOffset(), "Pin offset value not saved after editing");

        logInfo("Step 9: Closed 'Edit Debit Card' popup and open Client's profile on Accounts tab");
        Pages.debitCardModalWindow().clickTheCloseModalIcon();
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 10: Search for the account that was assigned to the card and open account on Maintenance-> Maintenance History page");
        Pages.clientDetailsPage().openAccountByNumber(chkAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through Account's Maintenance History and verify that there are records about the newly created Debit Card");
        // TODO: Implement verification at Maintenance History page
    }
}
