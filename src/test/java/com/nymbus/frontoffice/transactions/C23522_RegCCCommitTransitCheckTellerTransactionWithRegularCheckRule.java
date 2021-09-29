package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.TransitCheckDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C23522_RegCCCommitTransitCheckTellerTransactionWithRegularCheckRule extends BaseTest {

    private Transaction transaction;
    private BalanceDataForCHKAcc balanceData;
    private TransactionData transactionData;
    private Account checkAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        transaction = new TransactionConstructor(new TransitCheckDepositCHKAccBuilder()).constructTransaction();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        //Check Reg CC
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickManageRegCC();
        if (!SettingsPage.regCCPage().isRegCCActive()){
            throw new SkipException("Reg CC is inactive!");
        }

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        transaction.getTransactionSource().setAccountNumber(Generator.genAccountNumber());
        transaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        balanceData.setCurrentBalance(transaction.getTransactionSource().getAmount());
        balanceData.setAvailableBalance(transaction.getTransactionSource().getAmount());

        transactionData = new TransactionData(transaction.getTransactionDate(), transaction.getTransactionDate(), "+", balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 23522, testRunName = TEST_RUN_NAME)
    @Test(description = "C23522, Reg CC - Commit Transit check Teller transaction with regular check rule")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransferFromSavToDDaTransaction() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().openProofDateLoginModalWindow();
        Actions.transactionActions().doLoginProofDate();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Fill in source item:\n" +
                "Transit check\n" +
                "Routing Number - any valid routing != routing number of our bank (specified in FR-ABARoutingNumber bcsetting)\n" +
                "Account number - Any\n" +
                "Check number - Any\n" +
                "Amount - Any > $200 (e.g. $4000)");
        logInfo("Step 4: Select destination:\n" +
                "deposit\n" +
                "CHK account number from preconditions\n" +
                "The same amount as for the source item");
        logInfo("Step 5: Click [Commit Transaction] button and submit verify screen");
        Actions.transactionActions().createTransitCheckDepositTransaction(transaction);
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerModalPage().clickCloseButton();

        logInfo("Step 6: Search for the CHK account used in the Destination item and open it on Transactions tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkAccount.getAccountNumber());
        Pages.accountDetailsPage().clickTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        TestRailAssert.assertTrue(actualTransactionData.equals(transactionData), new CustomStepResult("Transaction data match!",
                "Transaction data doesn't match!"));

        logInfo("Step 7: Open Account on the Instructions tab and verify the generated holds");
        Pages.accountDetailsPage().clickInstructionsTab();
        Assert.assertEquals(Pages.accountInstructionsPage().getCreatedInstructionsCount(), 1,
                "Instruction was created");
        Pages.accountInstructionsPage().clickInstructionInListByIndex(1);
        double holdInstructionAmount = AccountActions.retrievingAccountData().getInstructionAmount();
        TestRailAssert.assertTrue(holdInstructionAmount == transaction.getTransactionSource().getAmount(),
                new CustomStepResult("Hold instruction amount is equal to request transaction amount!",
                        "Hold instruction amount is not equal to request transaction amount!"));

        logInfo("Step 8: Verify Account's current balance and available balance");
        Pages.accountDetailsPage().clickDetailsTab();
        balanceData.setAvailableBalance(0);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        TestRailAssert.assertTrue(actualBalanceData.equals(balanceData), new CustomStepResult("Balance data does match!",
                String.format("Balance data doesn't match! Expected %s, Actual %s", balanceData, actualBalanceData)));

    }
}
