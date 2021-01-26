package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C24990_ClientAccountsCloseSAVAccountWithOpenDebitCard extends BaseTest {
    private DebitCard debitCard;
    private IndividualClient client;
    private Account savingsAccount;
    private Transaction miscDebitGLCreditTransaction;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        savingsAccount = new Account().setSavingsAccountData();
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

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
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(savingsAccount.getAccountNumber());
        miscDebitGLCreditTransaction.getTransactionSource().setTransactionCode("227 - Withdraw&Close");

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create savings account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);

        // Set created account as related to credit card
        debitCard.getAccounts().add(savingsAccount.getAccountNumber());
        Actions.loginActions().doLogOut();

        // Set up transaction with account number
        depositTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");

        // Perform deposit transaction
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.navigationPage().waitForUserMenuVisible();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Pages.tellerPage().setEffectiveDate(depositTransaction.getTransactionDate());
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        //Create Debit Card
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.navigationPage().waitForUserMenuVisible();

        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Pages.clientDetailsPage().clickOnMaintenanceTab();

        Pages.clientDetailsPage().clickOnNewDebitCardButton();

        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        String dateEffective = Pages.debitCardModalWindow().getDateEffective();
        debitCard.setDateEffective(dateEffective);

        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();

        Pages.clientDetailsPage().clickOnViewAllCardsButton();
        Actions.maintenancePageActions().verifyDebitCardDetails(debitCard);
        Actions.maintenancePageActions().setDataToDebitCard(debitCard);
        Actions.loginActions().doLogOut();

    }

    @Severity(CRITICAL)
    @Test(description = "C24990, Client Accounts - Close SAV account with open debit card")
    public void clientAccountsCloseSavAccountWithOpenDebitCard() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page");
        Actions.transactionActions().loginTeller();
        Pages.aSideMenuPage().waitForASideMenu();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select Misc Debit item in the Source and GL/Credit in the Destination");
        logInfo("Step 4: Search for CHK account from the precondition in the Source and select 127 - Withdraw&Close trancode\n"+
                "Select any gl account in the Destination");
        logInfo("Step 5: For 127 - Withdraw&Close transaction set transaction amount = Available Balance on the account\n" +
                "Set the same amount for Destination item, any notes");
        logInfo("Step 6: Click [Commit Transaction] button and click [Verify] on Verify Client");

        Actions.transactionActions().createMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Assert.assertTrue(Pages.tellerPage().errorMessagesIsVisible(), "Error messages not visible");
    }
}
