package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Dmytro")
public class C22552_CreateNewDebitCard extends BaseTest {
    private DebitCard debitCard;
    private IndividualClient client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        checkingAccount = new Account().setCHKAccountData();

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        debitCard = debitCardBuilder.getCard();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();

        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        debitCard.setBinControl(binControl);
        debitCard.setATMDailyDollarLimit(binControl.getATMDailyDollarLimit());
        debitCard.setATMTransactionLimit(binControl.getATMTransactionLimit());
        debitCard.setDBCDailyDollarLimit(binControl.getDBCDailyDollarLimit());
        debitCard.setDBCTransactionLimit(binControl.getDBCTransactionLimit());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a clint
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkingAccount);

        // Set created CHK account as related to credit card
        debitCard.getAccounts().add(checkingAccount.getAccountNumber());
        Actions.loginActions().doLogOut();
    }

    @Severity(CRITICAL)
    @Test(description = "C22552, Clients Profile: Create new debit debitcard")
    public void createNewDebitCard() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();

        logInfo("Step 2: Search for any individualClient and open his profile on the Maintenance tab");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        logInfo("Step 3: Open client profile on the Maintenance tab");
        Pages.clientDetailsPage().clickOnMaintenanceTab();

        logInfo("Step 4: Click [New Debit Card] button in Cards Management tile");
        Pages.clientDetailsPage().clickOnNewDebitCardButton();

        logInfo("Step 5: Select Bin Number from the precondition and make sure its description is populated automatically");
        logInfo("Step 6: Click [Next] button");
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        String dateEffective = Pages.debitCardModalWindow().getDateEffective();
        debitCard.setDateEffective(dateEffective);

        logInfo("Step 7: Look at the ATM Daily Limit Number (Amount and Number), Debit Purchase Daily Limit Number (Amount and Number)");
        logInfo("Step 8: Look at the Expiration Date field");
        Actions.debitCardModalWindowActions().verifyDebitCardFilledValues(debitCard);

        logInfo("Step 9: Select account from preconditions in 'Account #1' field, specify all the displayed fields with valid data and click [Save and Finish] button");
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();

        logInfo("Step 10: Click [View all cards] button in Cards Management tile and verify the displayed info");
        Pages.clientDetailsPage().clickOnViewAllCardsButton();
        Actions.maintenancePageActions().verifyDebitCardDetails(debitCard);
        Actions.maintenancePageActions().setDataToDebitCard(debitCard);

        logInfo("Step 11: Go to the Accounts tab and search for the account that was assigned to the card. Open account on Maintenance-> Maintenance History page");
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through Account's Maintenance History and verify that there are records about the newly created Debit Card");
        Actions.maintenanceHistoryPageActions().verifyMaintenanceHistoryFields(debitCard, checkingAccount);
    }
}
