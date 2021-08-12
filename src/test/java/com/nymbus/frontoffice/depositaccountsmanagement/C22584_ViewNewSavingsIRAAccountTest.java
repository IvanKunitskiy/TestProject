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
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22584_ViewNewSavingsIRAAccountTest extends BaseTest {

    private Account savingsIRAAccount;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up Checking account
        checkingAccount = new Account().setCHKAccountData();

        // Set up IRA account
        savingsIRAAccount = new Account().setSavingsIraAccountData();

        // Login to the system
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Set the product of the user to account
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.IRA, RateType.TIER));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);


        // Create checking
        AccountActions.createAccount().createCHKAccount(checkingAccount);


        // Set corresponding account for Savings
        savingsIRAAccount.setApplyInterestTo("CHK Acct");

        // Create and edit IRA account
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);
        AccountActions.editAccount().editSavingsAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22584, testRunName = TEST_RUN_NAME)
    @Test(description = "C22584, View new savings IRA account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsIRAAccount);

        logInfo("Step 3: Look at the fields, verify that some of them are grouped in such sections:\n" +
                "- Balance and Interest\n" +
                "- Transactions\n" +
                "- Overdraft\n" +
                "- Misc\n" +
                "- Distribution");
        AccountActions.editAccount().verifySavingsIRAAGroupsAreVisible();

        logInfo("Step 4: Look at the fields and verify that such fields are out of sections:\n" +
                "- Current Balance\n" +
                "- Available Balance\n" +
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

        logInfo("Step 5: \t\n" +
                "Look at the fields and verify that such fields are in 'Balance and Interest' section:\n" +
                "- Average Balance\n" +
                "- Interest Rate\n" +
                "- Interest Frequency\n" +
                "- Interest Last paid\n" +
                "- Accrued Interest this statement cycle\n" +
                "- Low Balance This Statement Cycle\n" +
                "- Balance Last Statement\n" +
                "- YTD average balance\n" +
                "- Corresponding Account\n" +
                "- Date Last Debit\n" +
                "- Date Last Deposit\n" +
                "- Annual Percentage Yield\n" +
                "- Interest Paid Year to date\n" +
                "- Date Last Statement\n" +
                "- Previous Statement Date\n" +
                "- Previous Statement Balance\n" +
                "- Interest Paid Last Year\n" +
                "- Monthly low balance\n" +
                "- Monthly number of withdrawals\n" +
                "- Aggregate Balance Year to date\n" +
                "- Aggregate col bal\n" +
                "- Aggr col lst stmt\n" +
                "- YTD aggr col bal");
        AccountActions.editAccount().verifySavingsIRABalanceAndInterestFieldsAreVisible();

        logInfo("Step 6: Click [-] icon next to 'Balance and Interest' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickBalanceAndInterestSectionLink();

        logInfo("Step 7: Look at the fields and verify that such fields are in 'Transactions' section:\n" +
                "- Date Last Activity/Contact\n" +
                "- Number Of Deposits This Statement Cycle\n" +
                "- Number of Debits This Statement Cycle\n" +
                "- Last Debit Amount\n" +
                "- Last Deposit Amount\n" +
                "- Number Reg D items (6)\n" +
                "- Reg D violations last 12 mos\n" +
                "- YTD charges waived\n" +
                "- Statement Cycle\n" +
                "- Service charges YTD");
        AccountActions.editAccount().verifyCommonTransactionsFieldsAreVisible();


        logInfo("Step 8: Click [-] icon next to 'Transactions' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickTransactionsSectionLink();

        logInfo("Step 9: Look at the fields and verify that such fields are in 'Overdraft' section:\n" +
                "- Days consecutive overdraft\n" +
                "- Overdraft Charged Off\n" +
                "- Aggr OD balance\n" +
                "- Aggr col OD bal\n" +
                "- Aggr OD lst stmt\n" +
                "- Aggr col OD lst stmt\n" +
                "- NSF fee, paid this cycle\n" +
                "- NSF fee, rtrn this cycle\n" +
                "- NSF fee, paid YTD\n" +
                "- NSF fee, rtrn YTD\n" +
                "- NSF fee, paid last year\n" +
                "- NSF fee, rtrn last year");
        AccountActions.editAccount().verifySavingsIRAAOverdraftFieldsAreVisible();

        logInfo("Step 10: Click [-] icon next to 'Overdraft' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickOverdraftSectionLink();

        logInfo("Step 11: Look at the fields and verify that such fields are in 'Misc.' section:\n" +
                "- Federal W/H reason\n" +
                "- Federal W/H percent\n" +
                "- Taxes Withheld YTD\n" +
                "- User Defined Field 1\n" +
                "- User Defined Field 2\n" +
                "- User Defined Field 3\n" +
                "- User Defined Field 4\n" +
                "- Print Statement Next Update\n" +
                "- Call Class Code\n" +
                "- 1 day float\n" +
                "- 2 day float\n" +
                "- 3 day float\n" +
                "- 4 day float\n" +
                "- 5 day float\n" +
                "- Number of Debit Cards issued\n" +
                "- Reason Debit Card Charge Waived\n" +
                "- Exempt from Reg CC\n" +
                "- New Account\n" +
                "- Transactional Account\n" +
                "- Total Earnings for Life of Account\n" +
                "- Verify ACH funds");
        AccountActions.editAccount().verifyCommonMiscFieldsAreVisible();

        logInfo("Step 12: Click [-] icon next to 'Misc.' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 13: Look at the fields and verify that such fields are in 'Distribution' section:\n" +
                "- Date Of First Deposit\n" +
                "- IRA Distribution Frequency\n" +
                "- IRA Distribution Code\n" +
                "- IRA Distribution Account Number\n" +
                "- IRA distribution amount\n" +
                "- Amount last IRA distribution\n" +
                "- Date last IRA distribution\n" +
                "- Date next IRA distribution\n" +
                "- RMD Date\n" +
                "- RMD Amount\n" +
                "- IRA distributions YTD\n" +
                "- Date of Birth\n" +
                "- Date Deceased\n" +
                "- Total Contributions for Life of Account\n" +
                "- Waive Service Charges");
        AccountActions.editAccount().verifySavingsIRAADistributionFieldsAreVisible();

        logInfo("Step 14: Click [-] icon next to 'Distribution' section and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickDistributionSectionLink();


        logInfo("Step 15: Look at the fields on the page");
        Assert.assertFalse(Pages.editAccountPage().isMiscSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isTransactionsSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isOverdraftSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isDistributionSectionGroupFieldsOpened());

        logInfo("Step 16: Click [Expand All] button and verify that all fields within all sections are displayed");
        Pages.editAccountPage().clickExpandAllButton();
        Assert.assertTrue(Pages.editAccountPage().isMiscSectionGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isTransactionsSectionGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isOverdraftSectionGroupFieldsOpened());
        Assert.assertTrue(Pages.editAccountPage().isDistributionSectionGroupFieldsOpened());

        logInfo("Step 17: Click [Collapse All] button and verify that all fields within all sections were hidden");
        Pages.editAccountPage().clickCollapseAllButton();
        Assert.assertFalse(Pages.editAccountPage().isMiscSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isBalanceAndInterestSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isTransactionsSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isOverdraftSectionGroupFieldsOpened());
        Assert.assertFalse(Pages.editAccountPage().isDistributionSectionGroupFieldsOpened());

    }
}
