package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22685_SearchForClientOnTellerHeader extends BaseTest {
    private Account checkAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        Actions.loginActions().doLogOutProgrammatically();
    }

    @Test(description = "C22685, Search for client on Teller header")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNSFTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Search for a client from preconditions (e.g. using first name + last name) via quick " +
                "search or clicking [Search] button");
        Pages.clientsSearchPage().typeToClientsSearchInputField(checkAccount.getAccountNumber());
        Pages.clientsSearchPage().clickOnViewAccountByValue(checkAccount.getAccountNumber());
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 4: Select Misc Debit/ Misc Credit line item");
        Pages.tellerPage().clickMiscDebitButton();

        logInfo("Step 5: Expand Account Number field of the added line item and look at the available accounts");
        Pages.tellerPage().clickAccountNumberDiv(1);
        Pages.tellerPage().typeAccountNumber(1,checkAccount.getAccountNumber());

        SelenideTools.sleep(20);
        boolean visible = Pages.tellerPage().elementVisibleOnAutocompleteDropDownItem(checkAccount.getAccountNumber());
        Assert.assertTrue(visible, "Element don't visible!");
    }
}
