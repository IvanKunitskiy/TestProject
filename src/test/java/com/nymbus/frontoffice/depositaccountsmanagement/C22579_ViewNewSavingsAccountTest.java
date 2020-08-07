package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
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
public class C22579_ViewNewSavingsAccountTest extends BaseTest {

    private IndividualClient client;
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up savings account
        savingsAccount = new Account().setSavingsAccountData();
        savingsAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with savings account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22579, View New Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);

        logInfo("Step 3: Click [Load More] button");
        AccountActions.accountDetailsActions().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields on the page");
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), savingsAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountNumberValue(), savingsAccount.getAccountNumber(), "'Account Number' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), savingsAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), savingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), savingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), savingsAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), savingsAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), savingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        if (savingsAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), savingsAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        if (savingsAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), savingsAccount.getCorrespondingAccount(), "'Call Class' value does not match");
        }

        logInfo("Step 5: Click [Less] button");
        AccountActions.accountDetailsActions().clickLessButtonAndVerifyMoreIsVisible();
    }
}