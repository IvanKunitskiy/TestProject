package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

public class C24991_CommitCashTransactionWithAccountOwnerTest extends BaseTest {
    private IndividualClient client;
    private Account chkAccount;
    private Transaction transaction;
    private String clientRootId;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        chkAccount = new Account().setCHKAccountData();

        // Set up  transaction
        transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C24991, Verify: Commit Cash transaction with account owner")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCashTransactionWithAccountOwner() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 3: Select Cash In in the Source");
        logInfo("Step 4: Set some amount in the opened Denominations popup and click [Ok] button");
        logInfo("Step 5: Select Misc Credit (or Deposit) in the Destination");
        logInfo("Step 6: Fill in an account number field in Destination using number of the account from precondition. \n" +
                "Select some credit transaction in Transaction Code drop-down (e.g. 109 for CHK / 209 for Savings)");
        Actions.transactionActions().createTransaction(transaction);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Set Destination amount equal to Source amount. \n" +
                "Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Actions.transactionActions().verifyPreSelectedClientFields(client);

        logInfo("Step 8: Сlick [Verify] button in “Verify Conductor” popup");
        Pages.verifyConductorModalPage().clickVerifyButton();
        String imgSrc = Actions.transactionActions().getImageSrcFromPopup();
        String[] lines = Actions.balanceInquiryActions().getReceiptLines(imgSrc);

        logInfo("Recognized transaction receipt: \n" +
                Arrays.toString(lines));

        String expectedClientName = "Client: " + client.getNameForDebitCard();
        String clientIdStartLine = "Client #";
        Assert.assertTrue(Arrays.asList(lines).contains(expectedClientName), "Client name is incorrect!");
        Assert.assertTrue(Actions.balanceInquiryActions().isLineEndsWithChars(lines, clientIdStartLine, client.getIndividualType().getClientID()),
                "Client ID is incorrect!");
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 9: Go to the WebAdmin and log in");
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionUrl(chkAccount.getAccountNumber());
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(1);
        WebAdminActions.webAdminTransactionActions().goToTransactionHeaderUrl(transactionHeader);
        Assert.assertEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getCustomerIdValue(1), clientRootId,
                "Customer Id doesn't match!");
    }

    @AfterMethod(description = "Delete the downloaded image.")
    public void postCondition() {
        logInfo("Deleting the downloaded image...");
        Functions.cleanDirectory(System.getProperty("user.dir") + File.separator + Constants.RECEIPTS + File.separator);
    }
}