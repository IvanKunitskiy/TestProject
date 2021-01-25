package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C25312_CommitDebitTransactionWithOfficialCheckAccount extends BaseTest {
    private double transactionAmount = 100.00;


    @BeforeMethod
    public void preCondition() {
        //Check
        if (WebAdminActions.webAdminUsersActions().getUseGLAccountNumberForOfficialChecks(userCredentials.getUserName(), userCredentials.getPassword())) {
            System.out.println("zashlosde");
            throw new SkipException("UseGLAccountNumberForOfficialChecks != 0");
        }

//        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
//        System.out.println("sassssssssssssssssssssssssssssssssssssssssssssssssssss");
//        //Get CHK account number
//        String chkAccountNumber = WebAdminActions.webAdminUsersActions().getInternalCheckingAccountNumber(userCredentials.getUserName(), userCredentials.getPassword());
//
//        //Get balance
//        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
//        BalanceDataForCHKAcc balance = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
//
//        //Check balance
//        if (balance.getCurrentBalance()<transactionAmount){
//            Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
//            Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
//            Actions.transactionActions().goToTellerPage();
//            Actions.transactionActions().doLoginTeller();
//            Actions.transactionActions().createTransaction(depositSavingsTransaction);
//            Actions.transactionActions().clickCommitButton();
//            Pages.tellerPage().closeModal();
//        }

    }

    @Test(description = "C25312, Commit debit transaction with official check account")
    @Severity(SeverityLevel.CRITICAL)
    public void commitDebitTransactionWithOfficialCheckAccount() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: ");




    }

}
