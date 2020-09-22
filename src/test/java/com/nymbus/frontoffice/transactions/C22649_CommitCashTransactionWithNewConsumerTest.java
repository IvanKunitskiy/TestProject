package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.CashDrawerData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class C22649_CommitCashTransactionWithNewConsumerTest extends BaseTest {
    private Transaction transaction;
    private CashDrawerData cashDrawerData;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private IndividualClientBuilder individualClientBuilder;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account chkAccount = new Account().setCHKAccountData();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        transaction = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAmount(100);
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");

        Actions.transactionActions().loginTeller();
        Actions.cashDrawerAction().goToCashDrawerPage();
        cashDrawerData = Actions.cashDrawerAction().getCashDrawerData();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22649, Verify: Commit Cash transaction with new Consumer")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCashTransactionWithNewConsumer() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        String postingDate = Pages.tellerModalPage().getProofDateValue();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select any cash item + any one opposite line item, e.g.: \n" +
                "- Source: Cash-In (and select any amount) \n" +
                "- Destination: Deposit");
        logInfo("Step 4: Select account from the preconditions in the destination and select any trancode (e.g. 109 - Deposit)");
        Actions.transactionActions().createTransaction(transaction);
        String effectiveDate = Pages.tellerPage().getEffectiveDate();

        logInfo("Step 5: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().waitForModalWindowVisibility();

        logInfo("Step 6: Click [Add new Client] button on the Verify screen");
        logInfo("Step 7: Fill in all fields for the new client");
        IndividualClient newClientForTransactionPurpose = individualClientBuilder.buildClient();
        ClientsActions.individualClientActions().createClientOnVerifyConductorModalPage(newClientForTransactionPurpose);

        logInfo("Step 8: Click [Save] button");
        Pages.verifyConductorModalPage().clickSaveButton();
        ClientsActions.individualClientActions().waitForOFACModalWindowVerificationONVerifyConductorModal();
        Pages.verifyConductorModalPage().waitForReadOnlyClientFields();

        logInfo("Step 9: Click [Verify] button");
        Pages.verifyConductorModalPage().clickVerifyButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 10: Go to Clients screen and search for created consumer");
        logInfo("Step 11: Open his profile and verify the displayed fields");
        Actions.clientPageActions().searchAndOpenClientByName(newClientForTransactionPurpose.getLastNameAndFirstName());
        SoftAssert asert = new SoftAssert();
        asert.assertEquals(Pages.clientDetailsPage().getFirstName(),
                newClientForTransactionPurpose.getIndividualType().getFirstName(), "Client 'First Name' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getLastName(),
                newClientForTransactionPurpose.getIndividualType().getLastName(), "Client 'Last Name' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getTaxID(),
                newClientForTransactionPurpose.getIndividualType().getTaxID(), "Client 'Tax ID' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getBirthDate(),
                newClientForTransactionPurpose.getIndividualType().getBirthDate(), "Client 'Birth Date' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getPhone(),
                newClientForTransactionPurpose.getIndividualClientDetails().getPhones().get(0).getPhoneNumber(),
                "Client 'Phone number' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCountry(),
                newClientForTransactionPurpose.getIndividualType().getAddresses().get(0).getCountry().getCountry(),
                "Client 'Country' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddress(),
                newClientForTransactionPurpose.getIndividualType().getAddresses().get(0).getAddress(),
                "Client 'Address' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressCity(),
                newClientForTransactionPurpose.getIndividualType().getAddresses().get(0).getCity(),
                "Client 'City' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressZipCode(),
                newClientForTransactionPurpose.getIndividualType().getAddresses().get(0).getZipCode(),
                "Client 'ZipCode' isn't as expected.");
        asert.assertEquals(Pages.clientDetailsPage().getAddressState(),
                newClientForTransactionPurpose.getIndividualType().getAddresses().get(0).getState().getState(),
                "Client 'State' isn't as expected.");
        asert.assertAll();

        logInfo("Step 12: Go to cash drawer and verify its: \n" +
                "- denominations \n" +
                "- total cash in");
        cashDrawerData.addHundredsAmount(transaction.getTransactionSource().getAmount());
        cashDrawerData.addCashIn(transaction.getTransactionSource().getAmount());
        cashDrawerData.addCountedCash(transaction.getTransactionSource().getAmount());
        Actions.transactionActions().loginTeller();
        Actions.cashDrawerAction().goToCashDrawerPage();
        CashDrawerData actualData =  Actions.cashDrawerAction().getCashDrawerData();
        Assert.assertEquals(actualData, cashDrawerData, "Cash drawer data doesn't match!");

        chkAccBalanceData.addAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 13: Go to account and Open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenClientByName(transaction.getTransactionDestination().getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 14: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        logInfo("Step 15: Open account on Details and refresh the page");
        AccountActions.editAccount().goToDetailsTab();

        logInfo("Step 16: Verify such fields: \n" +
                "- current balance \n" +
                "- available balance");
        BalanceDataForCHKAcc actualBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceDataForSavingAcc, chkAccBalanceData,"Checking account balances is not correct!");

        TransactionData chkAccTransactionData = new TransactionData(postingDate,
                effectiveDate,
                "+",
                chkAccBalanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
    }
}