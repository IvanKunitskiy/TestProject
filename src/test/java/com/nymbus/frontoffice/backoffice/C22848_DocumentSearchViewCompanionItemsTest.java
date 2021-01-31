package com.nymbus.frontoffice.backoffice;

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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Notices")
@Owner("Petro")
public class C22848_DocumentSearchViewCompanionItemsTest extends BaseTest {

    private final double transactionAmount_1 = 90.00;
    private final double transactionAmount_2 = 80.00;
    private String chkAccountNumber;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

        // Set up transactions
        Transaction transaction_1 = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction_1.getTransactionSource().setAmount(transactionAmount_1);
        transaction_1.getTransactionDestination().setAmount(transactionAmount_1);
        transaction_1.getTransactionDestination().setAccountNumber(chkAccountNumber);
        transaction_1.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        Transaction transaction_2 = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction_2.getTransactionSource().setAmount(transactionAmount_2);
        transaction_2.getTransactionDestination().setAmount(transactionAmount_2);
        transaction_2.getTransactionDestination().setAccountNumber(chkAccountNumber);
        transaction_2.getTransactionDestination().setTransactionCode(TransactionCode.CREDIT_TRANSFER_101.getTransCode());

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount);

        // Perform transaction_1
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction_1);

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction_2
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction_2);

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Notices";

    @TestRailIssue(issueID = 22848, testRunName = TEST_RUN_NAME)
    @Test(description = "C22848, Document Search: View companion items")
    @Severity(SeverityLevel.CRITICAL)
    public void documentSearchViewCompanionItems() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Backoffice > Document Search > Transactions tab");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickDocumentSearchSeeMoreLink();
        Pages.documentSearchTransactionsPage().waitForTabContent();

        logInfo("Step 3: Search for transaction item from preconditions using some criteria " +
                " so that only one line item is found during the search \n" +
                "(e.g. specify account number related to one transaction item only, specific amount)");
        Pages.documentSearchTransactionsPage().setAccount(chkAccountNumber);
        Pages.documentSearchTransactionsPage().setAmount(String.format("%.2f", transactionAmount_1));
        Pages.documentSearchTransactionsPage().clickSearchButton();
        Pages.documentSearchTransactionsPage().waitForSearchButtonClickable();
        Assert.assertEquals(Pages.documentSearchTransactionsPage().getTransactionsCount(), 1,
                "Invalid transactions count");

        logInfo("Step 4: Select the resulted line item and click [Show Companion Items] button");
        Pages.documentSearchTransactionsPage().selectLineByIndex(1);
        Pages.documentSearchTransactionsPage().clickShowCompanionItemsButton();
        Pages.documentSearchTransactionsPage().waitForCompanionItemsPanel();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().getCompanionItemsCount() >= 1,
                "Companion items not found");

        logInfo("Step 5: Search for transaction from preconditions using some criteria so that several transaction items are found during the search");
        Pages.documentSearchTransactionsPage().clickClearAllButton();
        Pages.documentSearchTransactionsPage().setAccount(chkAccountNumber);
        Pages.documentSearchTransactionsPage().clickSearchButton();
        Pages.documentSearchTransactionsPage().waitForSearchButtonClickable();
        Assert.assertEquals(Pages.documentSearchTransactionsPage().getTransactionsCount(), 2,
                "Invalid transactions count");

        logInfo("Step 6: Select one of the resulted line items and click [Show Companion Items] button");
        Pages.documentSearchTransactionsPage().selectLineByIndex(1);
        Pages.documentSearchTransactionsPage().clickShowCompanionItemsButton();
        Pages.documentSearchTransactionsPage().waitForCompanionItemsPanel();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().getCompanionItemsCount() >= 1,
                "Companion items not found");
    }
}
