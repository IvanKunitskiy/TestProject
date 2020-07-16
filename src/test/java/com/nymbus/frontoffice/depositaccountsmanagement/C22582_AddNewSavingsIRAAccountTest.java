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
public class C22582_AddNewSavingsIRAAccountTest extends BaseTest {

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

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client and logout
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22582, Add New Savings IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(savingsIRAAccount);

        logInfo("Step 4: Select 'Product Type' = 'Savings Account'");
        AccountActions.createAccount().setProductType(savingsIRAAccount);

        logInfo("Step 5: Select any Savings IRA product (product with Account Type= Ira, Roth IRA, Coverdell ESA)");
        AccountActions.createAccount().setProduct(savingsIRAAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + client.getIndividualType().getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), savingsIRAAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOfBirth(), client.getIndividualType().getBirthDate(), "'Date of Birth' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionFrequency(), savingsIRAAccount.getIraDistributionFrequency(), "'IRA Distribution Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getIRADistributionCode(), savingsIRAAccount.getIraDistributionCode(), "'IRA Distribution Code' is prefilled with wrong value");

        logInfo("Step 7: Select any values in drop-down fields");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Select Date Opened as any date < Current Date and Select Date next IRA distribution as any date > Current Date" +
                "Select Date next IRA distribution as any date > Current Date" +
                "Select Date Of First Deposit as any date");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(savingsIRAAccount);

        logInfo("Step 10: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 11: Pay attention to the fields that were filled in during account creation");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
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

        logInfo("Step 12: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), savingsIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), savingsIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequency(), savingsIRAAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), savingsIRAAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        if (savingsIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), savingsIRAAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (savingsIRAAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), savingsIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionFrequencyInEditMode(), savingsIRAAccount.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionCodeInEditMode(), savingsIRAAccount.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), savingsIRAAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getIraDistributionAmountInEditMode(), savingsIRAAccount.getIraDistributionAmount(), "'IRA distribution amount' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), savingsIRAAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateNextIRADistributionInEditMode(), savingsIRAAccount.getDateNextIRADistribution(), "'Date next IRA distribution' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOfFirstDeposit(), savingsIRAAccount.getDateOfFirstDeposit(), "'Date Of First Deposit' value does not match");

        logInfo("Step 13: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 14: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
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
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Frequency") >= 1,
                "'Interest Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") >= 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Statement Cycle") >= 1,
                "'Statement Cycle' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA distribution amount") >= 1,
                "'IRA distribution amount' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date next IRA distribution") >= 1,
                "'Date next IRA distribution' row count is incorrect!");
        if (savingsIRAAccount.getCallClassCode() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code") >= 1,
                    "'Call Class Code' row count is incorrect!");
        }
        if (savingsIRAAccount.getCorrespondingAccount() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") >= 1,
                    "'Corresponding Account' row count is incorrect!");
        }
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Frequency") >= 1,
                "'IRA Distribution Frequency' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'IRA Distribution Code' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("IRA Distribution Code") >= 1,
                "'Date Of First Deposit' row count is incorrect!");
    }
}
