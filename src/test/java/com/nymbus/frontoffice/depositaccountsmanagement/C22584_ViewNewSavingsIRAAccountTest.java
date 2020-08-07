package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
public class C22584_ViewNewSavingsIRAAccountTest extends BaseTest {

    private IndividualClient client;
    private Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up IRA account
        savingsIRAAccount = new Account().setIRAAccountData();
        savingsIRAAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create and edit IRA account
        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);
        AccountActions.editAccount().editSavingsAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22584, View new savings IRA account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsIRAAccount);

        logInfo("Step 3: Click [Load More] button");
        AccountActions.accountDetailsActions().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields that were filled in during account creation");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), savingsIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), savingsIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequency(), savingsIRAAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), savingsIRAAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        if (savingsIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), savingsIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (savingsIRAAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), savingsIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionFrequency(), savingsIRAAccount.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionCode(), savingsIRAAccount.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), savingsIRAAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionAmount(), savingsIRAAccount.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), savingsIRAAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextIRADistribution(), savingsIRAAccount.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOfFirstDeposit(), savingsIRAAccount.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");

        logInfo("Step 5: Click [Less] button");
        AccountActions.accountDetailsActions().clickLessButtonAndVerifyMoreIsVisible();
    }
}
