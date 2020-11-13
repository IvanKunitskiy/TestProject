package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25238_PrintTellerReceiptWithBalanceForDebitItemsTest extends BaseTest {

    private Transaction chkTransaction;
    private Transaction savingsTransaction;
    private Transaction cdTransaction;
    private int printBalanceOnReceiptValue;

    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionSource withdrawalSource = SourceFactory.getWithdrawalSource();
    private final TransactionSource checkSource = SourceFactory.getCheckSource();
    private final TransactionDestination glCreditDestination = DestinationFactory.getGLCreditDestination();

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up client accounts
        Account checkingAccount = new Account().setCHKAccountData();
        Account savingsAccount = new Account().setSavingsAccountData();
        Account cdAccount = new Account().setCdAccountData();

        // Set up transaction for increasing the CHK account balance
        chkTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());

        // Set up transaction for increasing the CHK account balance
        savingsTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        savingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Set up transaction for increasing the CHK account balance
        cdTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        cdTransaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());

        // Set up Misc Debit source
        miscDebitSource.setAccountNumber(cdAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.DEBIT_MEM0_319.getTransCode());
        miscDebitSource.setAmount(25.00);
        double miscDebitAmount = miscDebitSource.getAmount();

        // Set up Withdrawal source
        withdrawalSource.setAccountNumber(savingsAccount.getAccountNumber());
        withdrawalSource.setTransactionCode(TransactionCode.WITHDRAWAL_216.getTransCode());
        withdrawalSource.setAmount(25.00);
        double withdrawalAmount = miscDebitSource.getAmount();

        // Set up Check source
        checkSource.setAccountNumber(checkingAccount.getAccountNumber());
        checkSource.setTransactionCode(TransactionCode.CHECK.getTransCode());
        checkSource.setAmount(25.00);
        double checkAmount = miscDebitSource.getAmount();

        // Set up transaction destination
        double glCreditAmount = miscDebitAmount + withdrawalAmount + checkAmount;
        glCreditDestination.setAmount(glCreditAmount);

        // Get 'Print Balance On Receipt' value
        printBalanceOnReceiptValue = WebAdminActions.webAdminUsersActions().getPrintBalanceOnReceiptValue();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the client A and B account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        checkingAccount.setBankBranch(bankBranch);
        savingsAccount.setBankBranch(bankBranch);
        cdAccount.setBankBranch(bankBranch);

        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        AccountActions.createAccount().createCDAccount(cdAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        // Perform transactions to assign the money to accounts
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(savingsTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(cdTransaction);
        Actions.loginActions().doLogOutProgrammatically();

    }

    @Test(description = "C25238, Print teller receipt with balance for Debit Items (except 128-Check)")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithBalanceForDebitItems() {

    }
}
