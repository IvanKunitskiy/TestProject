package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22678_CommitTransactionOnAClosedAccount extends BaseTest {
    private Transaction transaction;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

        // Close account
        AccountActions.editAccount().closeAccount();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22678, testRunName = TEST_RUN_NAME)
    @Test(description = "C22678, Commit transaction on a closed account")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCommittingTransactionOnCloseAcc() {

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select any source / destination fund types so that at least one is related to regular account");
        logInfo("Step 4: Select closed account from preconditions in any of the added line items which are related to regular account");
        logInfo("Step 5: Specify fields for opposite line item with correct values");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Actions.transactionActions().waitForLoadSpinnerInvisibility();

        Assert.assertTrue(Pages.tellerPage().isMessageWithTextPresent("This account is closed."),
                "Error message isn't displayed!");
    }
}