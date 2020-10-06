package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22598_CloseAccountWithNoMonetaryTransactionTest extends BaseTest {

    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch and product of the user to account
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22598, Close Account with no monetary transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void closeAccountWithNoMonetaryTransaction() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition (e.g. CHK account) and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);

        logInfo("Step 3: Pay attention to the [Close Account] button");
        Assert.assertTrue(Pages.accountDetailsPage().isCloseAccountButtonVisible(), "'Close Account' button is not visible");

        logInfo("Step 4: Click [Close Account] button");
        Pages.accountDetailsPage().clickCloseAccountButton();
        Assert.assertTrue(Pages.accountDetailsPage().isAccountClosedNotificationVisible(), "'Account closed' notification is not visible");
        Assert.assertTrue(Pages.accountDetailsPage().isReOpenButtonVisible(), "'Re-Open' button is not visible");

        logInfo("Step 5: Go to Transactions tab and pay attention to the transaction list");
        Pages.accountNavigationPage().clickTransactionsTab();
        Assert.assertTrue(Pages.transactionsPage().isWithdrawAndCloseTransactionsVisible(), "Withdraw & Close transaction is not displayed on history page");

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page and check that there are records about the changing status to 'Closed' and filling in Date Closed field");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().verifyClosedAccountWithNoMonetaryTransactionRecords();
    }
}
