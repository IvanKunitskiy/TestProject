package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.accountinstructions.CreditHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.CreditHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22680_CommitTransactionWithCreditHoldTest extends BaseTest {

    private Account checkingAccount;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private TransactionData transactionData;
    private Transaction transaction;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account (required as Corresponding Account)
        checkingAccount = new Account().setCHKAccountData();

        // Set up transaction for increasing the CHK account balance
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());
        double transactionAmount = transaction.getTransactionSource().getAmount();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new CreditHoldInstructionBuilder());
        CreditHoldInstruction creditHoldInstruction = instructionConstructor.constructInstruction(CreditHoldInstruction.class);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the missing CHK account data
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(checkingAccount);

        // Create instruction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Pages.accountNavigationPage().clickInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createCreditHoldInstruction(creditHoldInstruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);

        // Retrieve the current and available balance from account
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.loginActions().doLogOut();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+",
                transactionAmount,
                transactionAmount);
    }

    @Test(description = "C22680, Commit transaction with credit hold")
    @Severity(SeverityLevel.CRITICAL)
    public void commitTransactionWithCreditHold() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select such items:\n" +
                "Source- GL Debit\n" +
                "Destination - Misc Credit");
        logInfo("Step 4:Select account from preconditions in destination line item," +
                "set any amount and any trancode (e.g. 109-Deposit/ 209-Deposit)");
        logInfo("Step 5: Specify fields for Source line item with correct values " +
                "(select any GL account, specify Notes and amount same as in Credit item)");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Specify credentials of the user from preconditions in the popup and submit it");
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        chkAccBalanceData.addAvailableBalance(transaction.getTransactionDestination().getAmount());
        chkAccBalanceData.addCurrentBalance(transaction.getTransactionDestination().getAmount());
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 8: Go to account with debit hold instruction used in transaction and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                chkAccBalanceData.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                chkAccBalanceData.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 9: Open account on the Transactions tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
    }
}
