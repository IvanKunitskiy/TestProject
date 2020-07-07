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
public class C22587_AddNewRegularCDAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdAccount;
    private Account checkingAccount;
    private String clientID;

    @BeforeMethod
    public void preConditions() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdAccount = new Account().setCDAccountData();
        cdAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        cdAccount.setTermType("3");
        cdAccount.setApplyInterestTo("CHK Acct");
        cdAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdAccount.getTermType())));
        cdAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Set up CHK account (required to point the 'Corresponding Account')
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22587, Add New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewRegularCDAccountTest() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdAccount);

        logInfo("Step 5: Select any product not IRA (e.g. 3 month Regular Certificate)");
        AccountActions.createAccount().setProduct(cdAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getIndividualType().getClientType().getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getIndividualType().getFirstName() + " " + client.getIndividualType().getLastName() + " (" + clientID + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), cdAccount.getAccountHolder(), "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getIndividualType().getClientType().getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getIndividualType().getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getIndividualClientDetails().getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), cdAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTermType(), cdAccount.getTermType(), "'Term Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestFrequency(), cdAccount.getInterestFrequency(), "'Interest Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getIndividualClientDetails().getMailCode().getMailCode(), "'Mail code' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplyInterestTo(), cdAccount.getApplyInterestTo(), "'Apply interes to' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAutoRenewable(), cdAccount.getAutoRenewable(), "'Auto Renewable' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTransactionalAccount().toLowerCase(), "no", "'Transactional Account' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getApplySeasonalAddress().toLowerCase(), "yes", "'Apply Seasonal Address' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Set 'Transactional Account' switcher to YES):");
        logInfo("Step 10: Select Date Opened as any date < Current Date");
        AccountActions.createAccount().selectValuesInFieldsRequiredForCDAccount(cdAccount);

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Check the fields that were filled in during account creation");
        if (Pages.accountDetailsPage().isMoreButtonVisible()) {
            Pages.accountDetailsPage().clickMoreButton();
        }
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), cdAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), cdAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequencyCode(), cdAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), cdAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' value does not match");
        if (cdAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), cdAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (cdAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");
        }
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");

        logInfo("Step 13: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 14: Check the Date next interest field value (verify that it's calculated based on Date Opened + Interest Frequency)");
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextInterest(), cdAccount.getDateNextInterest(), "'Maturity Date' value does not match");

        logInfo("Step 15: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), cdAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), cdAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequencyCode(), cdAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), cdAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' value does not match");
        if (cdAccount.getCorrespondingAccount() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), cdAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        }
        if (cdAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");
        }

        logInfo("Step 16: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 17: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Product"), 1,
                "'Product' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype"), 1,
                "'accounttype' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title"), 1,
                "'Account Title' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer"), 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch"), 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Type"), 1,
                "'Interest Type' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Apply Interest To"), 1,
                "'Apply Interest To' row count is incorrect!");
        if (cdAccount.getCallClassCode() != null) {
            Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Call Class Code"), 1,
                    "'Call Class Code' row count is incorrect!");
        }
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened"), 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest Rate"), 1,
                "'Interest Rate' row count is incorrect!");
        if (cdAccount.getCorrespondingAccount() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") >= 1,
                    "'Corresponding Account' row count is incorrect!");
        }
    }
}
