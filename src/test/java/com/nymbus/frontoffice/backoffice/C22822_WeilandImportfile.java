package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.IOUtils;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.ActivityHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class C22822_WeilandImportfile extends BaseTest {

    private String chkAccountNumber;
    private String chkAccountNumber2;
    private String chkAccountNumber3;
    private String chkAccountNumber4;
    private String chkAccountNumber5;
    private ActivityHoldInstruction instruction;
    private String systemDate;
    private double amount = 1.99;
    private String overdraftLimit = "$ 500.00";

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        Account chkAccount2 = new Account().setCHKAccountData();
        Account chkAccount3 = new Account().setCHKAccountData();
        Account chkAccount4 = new Account().setCHKAccountData();
        Account chkAccount5 = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();
        chkAccountNumber2 = chkAccount2.getAccountNumber();
        chkAccountNumber3 = chkAccount3.getAccountNumber();
        chkAccountNumber4 = chkAccount4.getAccountNumber();
        chkAccountNumber5 = chkAccount5.getAccountNumber();
        systemDate = WebAdminActions.loginActions().getSystemDate();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new ActivityHoldInstructionBuilder());
        instruction = instructionConstructor.constructInstruction(ActivityHoldInstruction.class);

        // Log in
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount2.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount3.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount4.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount5.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));

        //Create file
        List<String> accounts = Arrays.asList(chkAccountNumber, chkAccountNumber2, chkAccountNumber3,
                chkAccountNumber4, chkAccountNumber5);
        IOUtils io = new IOUtils();
        io.createWeilandFile(accounts,amount);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK accounts
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount2);
        AccountActions.editAccount().editOverdraftStatusAndLimit(overdraftLimit);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount3);
        Pages.accountDetailsPage().clickCloseAccountButton();
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount4);
        Pages.accountNavigationPage().clickInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createActivityHoldInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount5);

        // Set up transaction with account number
        // Perform deposit transactions
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount4.getAccountNumber());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
    }


    @Test(description = "C22816, Weiland: Import file")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandImportFile() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to BackOffice page");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();

        logInfo("Open \"File Import\" widget drop-down - select \"Weilad Charge File\" - click \"Import\"");
        Pages.backOfficePage().chooseFileImportType("Weiland");
        Pages.backOfficePage().clickImport();

        logInfo("Step 3: Drag&Drop Weiland file from preconditions and click [Import] button");
        Pages.backOfficePage().importFile("weiland.IF3");




    }
}
