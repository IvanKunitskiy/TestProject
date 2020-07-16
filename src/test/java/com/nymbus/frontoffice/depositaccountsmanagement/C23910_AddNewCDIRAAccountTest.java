package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
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
public class C23910_AddNewCDIRAAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CD IRA account
        cdIRAAccount = new Account().setCDIRAAccountData();
        cdIRAAccount.setApplyInterestTo("CHK Acct");
        cdIRAAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdIRAAccount.getTermType())));
        cdIRAAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Set up CHK account (required to point the 'Corresponding Account')
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        cdIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23910, Add New CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewCDIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdIRAAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdIRAAccount);

        logInfo("Step 5: Select any CD IRA product (product with Account Type= Ira, Roth IRA, Coverdell ESA)");
        AccountActions.createAccount().setProduct(cdIRAAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + client.getIndividualType().getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), cdIRAAccount.getAccountHolder(), "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), cdIRAAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTermType(), cdIRAAccount.getTermType(), "'Term Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAutoRenewable(), cdIRAAccount.getAutoRenewable(), "'Auto Renewable' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestFrequency(), cdIRAAccount.getInterestFrequency(), "'Interest Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplyInterestTo(), "Remain in Account", "'Apply interest to' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getInterestType(), "Simple", "'Interest Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTransactionalAccount().toLowerCase(), "no", "'Transactional Account' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getDateOfBirth(), client.getIndividualType().getBirthDate(), "'Date Of Birth' value does not match");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionFrequency(), cdIRAAccount.getIraDistributionFrequency(), "'IRA Distribution Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionCode(), cdIRAAccount.getIraDistributionCode(), "'IRA Distribution Code' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Select Date Opened as any date < Current Date\n" +
                "Select Date next IRA distribution as any date > Current Date\n" +
                "Select Date Of First Deposit as any date");
        logInfo("Step 10: Set switchers:\n" +
                "- Apply Seasonal Address- to NO\n" +
                "- Auto Renewable to NO\n" +
                "- Transactional Account to YES");
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Pay attention to the fields that were filled in during account creation");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), cdIRAAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequencyCode(), cdIRAAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), cdIRAAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), cdIRAAccount.getInterestType(), "'Interest Type' value does not match");
        if (cdIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), cdIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (cdIRAAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionFrequency(), cdIRAAccount.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionCode(), cdIRAAccount.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionAmount(), cdIRAAccount.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), cdIRAAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextIRADistribution(), cdIRAAccount.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOfFirstDeposit(), cdIRAAccount.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");

        logInfo("Step 13: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdIRAAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 14: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), cdIRAAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), cdIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), cdIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequencyCode(), cdIRAAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), cdIRAAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestType(), cdIRAAccount.getInterestType(), "'Interest Type' value does not match");
        if (cdIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), cdIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (cdIRAAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), cdIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionFrequencyInEditMode(), cdIRAAccount.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionCodeInEditMode(), cdIRAAccount.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionAmountInEditMode(), cdIRAAccount.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), cdIRAAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateNextIRADistributionInEditMode(), cdIRAAccount.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOfFirstDeposit(), cdIRAAccount.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");

        logInfo("Step 15: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 16: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product") >= 1,
                "'Product' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") >= 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") >= 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") >= 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") >= 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") >= 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type") >= 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        if (cdIRAAccount.getCallClassCode() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") >= 1,
                    "'Call Class Code' row count is incorrect!");
        }
        if (cdIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") >= 1,
                    "'Corresponding Account' row count is incorrect!");
        }
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Frequency") >= 1,
                "'IRA Distribution Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'IRA Distribution Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To") >= 1,
                "'Apply Interest To' row count is incorrect!");
    }
}

