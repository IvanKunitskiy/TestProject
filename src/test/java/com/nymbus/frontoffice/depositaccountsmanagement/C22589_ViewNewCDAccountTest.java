package com.nymbus.frontoffice.depositaccountsmanagement;

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
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22589_ViewNewCDAccountTest extends BaseTest {

    private Account cdAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdAccount = new Account().setCdAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        cdAccount.setBankBranch(Actions.usersActions().getBankBranch());
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        // Create a client with CD account
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCDAccount(cdAccount);

        // Edit account and logout
        AccountActions.editAccount().editCDAccount(cdAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22589, testRunName = TEST_RUN_NAME)
    @Test(description = "C22589, View New CD Account Test")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewCDAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);

        logInfo("Step 3: Look at the fields, verify that some of them are grouped in such sections:\n" +
                "- Balance and Interest\n" +
                "- Term\n" +
                "- Distribution and Misc");
        AccountActions.editAccount().verifyCDFieldGroupsAreVisible();

        logInfo("Step 4: Look at the fields and verify that such fields are out of sections:\n" +
                "- Current Balance\n" +
                "- Product Type\n" +
                "- Product\n" +
                "- Account Number\n" +
                "- Account Title\n" +
                "- Account Type\n" +
                "- Account Holders and Signers\n" +
                "- Account Status\n" +
                "- Originating Officer\n" +
                "- Current Officer\n" +
                "- Bank Branch\n" +
                "- Mail Code\n" +
                "- Apply Seasonal Address\n" +
                "- Special Mailing Instructions\n" +
                "- Date Opened\n" +
                "- Date Closed");
        AccountActions.editAccount().verifyGeneralFieldsAreVisible();


        logInfo("Step 5: Look at the fields and verify that such fields are in 'Balance and Interest' section:\n" +
                "- Interest Rate\n" +
                "- Annual Percentage Yield\n" +
                "- Rate Index\n" +
                "- Accrued Interest\n" +
                "- Daily Interest Accrual\n" +
                "- Interest Type\n" +
                "- Interest Frequency\n" +
                "- Apply Interest To\n" +
                "- Corresponding Account\n" +
                "- Amount Last Interest Paid\n" +
                "- Date Last Interest Paid\n" +
                "- Date next interest\n" +
                "- Next Interest Payment Amount\n" +
                "- Interest Paid Year to date");
        AccountActions.editAccount().verifyCDBalanceAndInterestFieldsAreVisible();


        logInfo("Step 6: Click [-] icon next to 'Balance and Interest' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickBalanceAndInterestSectionLink();

        logInfo("Step 7: Look at the fields and verify that such fields are in 'Term' section:\n" +
                "- Original Balance\n" +
                "- Term Type\n" +
                "- Term In Months Or Days\n" +
                "- Auto Renewable\n" +
                "- Maturity Date\n" +
                "- Call Class Code\n" +
                "- Penalty Amount Year-to-date\n" +
                "- Balance At Renewal\n" +
                "- Date of renewal\n" +
                "- Interest rate at renewal\n" +
                "- Renewal amount");
        AccountActions.editAccount().verifyCommonTermFieldsAreVisible();


        logInfo("Step 8: Click [-] icon next to 'Term' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickTermSectionLink();


        logInfo("Step 9: Look at the fields and verify that such fields are in 'Distribution and Misc' section:\n" +
                "- Federal W/H reason\n" +
                "- Federal W/H percent\n" +
                "- Taxes Withheld YTD\n" +
                "- User Defined Field 1\n" +
                "- User Defined Field 2\n" +
                "- User Defined Field 3\n" +
                "- User Defined Field 4\n" +
                "- Balance at end of year\n" +
                "- Accrued interest at end of year\n" +
                "- Interest Paid Last Year\n" +
                "- Print interest notice override\n" +
                "- Transactional Account\n" +
                "- Total Earnings for Life of Account");
        AccountActions.editAccount().verifyCdDistributionAndMiscFieldsAreVisible();


        logInfo("Step 10: Click [-] icon next to 'Distribution and Misc' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickDistributionAndMiscSectionLink();

        logInfo("Step 11: Look at the fields on the page");
        Assert.assertFalse(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isTermGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isDistributionAndMiscGroupFieldsOpened());

        logInfo("Step 12: Click [Expand All] button and verify that all fields within all sections are displayed");
        Pages.editAccountPage().clickExpandAllButton();
        Assert.assertTrue(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isTermGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isDistributionAndMiscGroupFieldsOpened());

        logInfo("Step 13: Click [Collapse All] button and verify that all fields within all sections were hidden");
        Pages.editAccountPage().clickCollapseAllButton();
        Assert.assertFalse(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isTermGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isDistributionAndMiscGroupFieldsOpened());
    }
}
