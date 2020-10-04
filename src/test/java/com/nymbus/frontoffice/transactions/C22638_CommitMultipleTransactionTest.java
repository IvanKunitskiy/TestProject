package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.MultipleTransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitCashDepositMultipleTransactionBuilder;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.*;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22638_CommitMultipleTransactionTest extends BaseTest {
    private MultipleTransaction multipleTransaction;
    private Account chkAccount;
    private Account saveAccount;
    private Account cdAccount;
    private BalanceData savingAccBalanceData;
    private BalanceDataForCDAcc cdAccBalanceData;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private CashDrawerData cashDrawerData;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        chkAccount = new Account().setCHKAccountData();
        saveAccount = new Account().setSavingsAccountData();
        cdAccount = new Account().setCdAccountData();

        // Set up  transactions
        Transaction transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();
        multipleTransaction = new MultipleTransactionConstructor(new MiscDebitCashDepositMultipleTransactionBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Create CD account
        AccountActions.createAccount().createCDAccountForTransactionPurpose(cdAccount);
        cdAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Create Savings account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(saveAccount);
        savingAccBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Set up multiple transaction with account numbers
        multipleTransaction.getSources().get(1).setAccountNumber(chkAccount.getAccountNumber());

        multipleTransaction.getDestinations().get(1).setAccountNumber(saveAccount.getAccountNumber());

        multipleTransaction.getDestinations().get(2).setAccountNumber(cdAccount.getAccountNumber());

        // perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createCashInMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();
        chkAccBalanceData.addAmount(transaction.getTransactionDestination().getAmount());

        // Logout and login for update teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.cashDrawerAction().goToCashDrawerPage();
        Actions.transactionActions().doLoginTeller();
        cashDrawerData = Actions.cashDrawerAction().getCashDrawerData();

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22638,  Commit transaction with multiple sources and destinations")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionWithMultipleSourcesAndDestinations() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().openProofDateLoginModalWindow();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select Cash In in the Source");
        logInfo("Step 4: Select any amount for not zero denomination and click [OK]");
        logInfo("Step 5: Select one more fund type in the Source, e.g. Misc Debit");
        logInfo("Step 6: Select Cash Out in the Destination");
        logInfo("Step 7: Select any Cash Denomination except one that was selected for Cash In item and amount != Cash In amount");
        logInfo("Step 8: Select two more items in Destination");
        logInfo("Step 9: Fill in fields for the Source/Destination items: \n" +
                "- Misc Debit --> Select CHK account from the precondition, transaction code= 116, amount < account's available balance \n" +
                "- Misc Credit -->Select CD account from the preconditions, transaction code = 303 \n" +
                "- Deposit --> Select Savings account from the preconditions, transaction code = 209");
        logInfo("Step 10: For Misc Credit and Deposit items select transaction amount that \n" +
                "will result in balanced Source and Destination items");
        Actions.transactionActions().createTransactionWithMultipleSources(multipleTransaction);
        String effectiveDate = Pages.tellerPage().getEffectiveDate();
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 11: Click [Commit Transaction] button and confirm Verify popup \n" +
                "(which should be displayed if cash items were selected for transaction)");
        chkAccBalanceData.reduceAmount(multipleTransaction.getSources().get(1).getAmount());
        TransactionData chkAccTransactionData = new TransactionData(postingDate,
                effectiveDate,
                "-",
                chkAccBalanceData.getCurrentBalance(),
                multipleTransaction.getSources().get(1).getAmount());

        logInfo("Step 12: Search for CHK account that was used in Source and verify its: \n" +
                "- current balance \n" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(chkAccount.getAccountNumber());
        BalanceDataForCHKAcc actualBalanceDataForCHKAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceDataForCHKAcc, chkAccBalanceData, "CHKAccount balances is not correct!");

        logInfo("Step 13: Open account on Transactions history and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        cdAccBalanceData.addAmount(multipleTransaction.getDestinations().get(2).getAmount());
        TransactionData cdAccTransactionData = new TransactionData(postingDate,
                effectiveDate,
                "+",
                cdAccBalanceData.getCurrentBalance(),
                multipleTransaction.getDestinations().get(2).getAmount());

        logInfo("Step 14: Search for CD account that was used in Misc Credit item and verify its current balance");
        Actions.clientPageActions().searchAndOpenClientByName(cdAccount.getAccountNumber());
        BalanceDataForCDAcc actualBalanceDataForCdAcc = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();
        Assert.assertEquals(actualBalanceDataForCdAcc.getCurrentBalance(), cdAccBalanceData.getCurrentBalance(),
                    "CDAccount current balances is not correct!");

        logInfo("Step 15: Open account on Transactions tab and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, cdAccTransactionData, "Transaction data doesn't match!");

        savingAccBalanceData.addAmount(multipleTransaction.getDestinations().get(1).getAmount());

        logInfo("Step 16: Search for Savings account that was used in Deposit item and Open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenClientByName(saveAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 17: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        logInfo("Step 18: Open account on Details and refresh the page");
        AccountActions.editAccount().goToDetailsTab();

        logInfo("Step 19: Verify such fields: \n" +
                "- current balance \n" +
                "- available balance");
        BalanceData actualBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceDataForSavingAcc, savingAccBalanceData,"Saving account balances is not correct!");

        TransactionData savingAccTransactionData = new TransactionData(postingDate,
                 effectiveDate,
                "+",
                cdAccBalanceData.getCurrentBalance(),
                multipleTransaction.getDestinations().get(1).getAmount());

        logInfo("Step 20: Open account on Transactions tab and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 21: Go to cash drawer and verify its: \n" +
                "- denominations \n" +
                "- total cash in \n" +
                "- total cash out");
        // prepare calculations
        double cashIn = multipleTransaction.getSources().get(0).getAmount();
        double cashOut = multipleTransaction.getDestinations().get(0).getAmount();
        double countedCash = cashIn - cashOut;

        // prepare expected cash drawer data
        cashDrawerData.addCashIn(cashIn);
        cashDrawerData.addCashOut(cashOut);
        cashDrawerData.addCountedCash(countedCash);
        cashDrawerData.addHundredsAmount(cashIn);
        cashDrawerData.reduceFiftiesAmount(cashOut);

        Actions.transactionActions().loginTeller();
        Actions.cashDrawerAction().goToCashDrawerPage();
        CashDrawerData actualData =  Actions.cashDrawerAction().getCashDrawerData();
        Assert.assertEquals(actualData, cashDrawerData, "Cash drawer data doesn't match!");
    }
}