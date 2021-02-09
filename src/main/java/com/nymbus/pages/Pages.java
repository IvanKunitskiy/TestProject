package com.nymbus.pages;

import com.nymbus.core.allure.AllureLogger;
import com.nymbus.pages.accounts.*;
import com.nymbus.pages.accounts.transactions.EditAccountTransactionModal;
import com.nymbus.pages.accounts.transactions.TransactionsPage;
import com.nymbus.pages.backoffice.BackOfficePage;
import com.nymbus.pages.backoffice.documentsearch.DocumentSearchNoticesPage;
import com.nymbus.pages.backoffice.documentsearch.DocumentSearchTransactionsPage;
import com.nymbus.pages.backoffice.printchecks.CheckPrintPage;
import com.nymbus.pages.backoffice.printchecks.PrintChecksPage;
import com.nymbus.pages.cashdrawer.CashDrawerBalancePage;
import com.nymbus.pages.cashier.CashierPage;
import com.nymbus.pages.cashier.NoticePage;
import com.nymbus.pages.check.CheckPage;
import com.nymbus.pages.check.FullCheckPage;
import com.nymbus.pages.clients.AddClientPage;
import com.nymbus.pages.clients.ClientDetailsPage;
import com.nymbus.pages.clients.ClientsSearchPage;
import com.nymbus.pages.clients.ClientsSearchResultsPage;
import com.nymbus.pages.clients.documents.AddNewDocumentPage;
import com.nymbus.pages.clients.documents.DocumentOverviewPage;
import com.nymbus.pages.clients.documents.DocumentsPage;
import com.nymbus.pages.clients.maintenance.MaintenanceHistoryPage;
import com.nymbus.pages.clients.maintenance.MaintenancePage;
import com.nymbus.pages.clients.notes.NotesPage;
import com.nymbus.pages.clients.transfers.EditTransferPage;
import com.nymbus.pages.clients.transfers.NewTransferPage;
import com.nymbus.pages.clients.transfers.TransfersPage;
import com.nymbus.pages.clients.transfers.ViewTransferPage;
import com.nymbus.pages.creditcards.CardsManagementPage;
import com.nymbus.pages.journal.JournalDetailsPage;
import com.nymbus.pages.journal.JournalPage;
import com.nymbus.pages.loans.AddNewLoanProductPage;
import com.nymbus.pages.loans.LoanProductOverviewPage;
import com.nymbus.pages.loans.LoanProductPage;
import com.nymbus.pages.loans.LoansPage;
import com.nymbus.pages.modalwindow.*;
import com.nymbus.pages.reportgenerator.ReportGeneratorPage;
import com.nymbus.pages.teller.*;
import com.nymbus.pages.tellertotellertransfer.TellerToTellerPage;

public class Pages extends AllureLogger {
    /**
     * Pages
     */
    private static LoginPage loginPage;
    private static NavigationPage navigationPage;
    private static ASideMenuPage aSideMenuPage;
    private static AddClientPage addClientPage;
    private static ClientDetailsPage clientDetailsPage;
    private static TellerPage tellerPage;
    private static CashierPage cashierPage;
    private static LoansPage loansPage;
    private static ReportGeneratorPage reportGeneratorPage;
    private static TellerToTellerPage tellerToTellerPage;
    private static Settings settings;
    private static ClientsSearchPage clientsSearchPage;
    private static ClientsSearchResultsPage clientsSearchResultsPage;
    private static AddAccountPage addAccountPage;
    private static AccountDetailsPage accountDetailsPage;
    private static EditAccountPage editAccountPage;
    private static AccountNavigationPage accountNavigationPage;
    private static AccountMaintenancePage accountMaintenancePage;
    private static MaintenancePage maintenancePage;
    private static MaintenanceHistoryPage maintenanceHistoryPage;
    private static TellerModalPage tellerModalPage;
    private static AccountTransactionPage accountTransactionPage;
    private static AccountInstructionsPage accountInstructionsPage;
    private static AccountDetailsModalPage accountDetailsModalPage;
    private static NotesPage notesPage;
    private static Alerts alerts;
    private static DocumentsPage documentsPage;
    private static AddNewDocumentPage addNewDocumentPage;
    private static DocumentOverviewPage documentOverviewPage;
    private static AccountStatementPage accountStatementPage;
    private static TransactionsPage transactionsPage;
    private static JournalPage journalPage;
    private static JournalDetailsPage journalDetailsPage;
    private static CashInModalWindowPage cashInModalWindowPage;
    private static VerifyConductorModalPage verifyConductorModalPage;
    private static TransfersPage transfersPage;
    private static NewTransferPage newTransferPage;
    private static ViewTransferPage viewTransferPage;
    private static EditTransferPage editTransferPage;
    private static CashInModalPage cashInModalPage;
    private static VerifyConductorPage verifyConductorPage;
    private static TransactionCompletedPage transactionCompleted;
    private static AccountsPage accountsPage;
    private static CallStatementModalPage callStatementModalPage;
    private static BalanceInquiryModalPage balanceInquiryModalPage;
    private static SupervisorModalPage supervisorModalPage;
    private static CardsManagementPage cardsManagementPage;
    private static AttentionModalPage attentionModalPage;
    private static EditAccountTransactionModal editAccountTransactionModal;
    private static CashDrawerBalancePage cashDrawerBalancePage;
    private static ConfirmModalPage confirmModalPage;
    private static NoticePage noticePage;
    private static BackOfficePage backOfficePage;
    private static DocumentSearchNoticesPage documentSearchNoticesPage;
    private static DocumentSearchTransactionsPage documentSearchTransactionsPage;
    private static CheckPage checkPage;
    private static FullCheckPage fullCheckPage;
    private static PrintChecksPage printChecksPage;
    private static CheckPrintPage checkPrintPage;
    private static AccountCommercialAnalysisPage accountCommercialAnalysisPage;
    private static CashierDefinedActionsPage cashierDefinedActionsPage;
    private static AlertMessageModalPage alertMessageModalPage;
    private static LoanProductPage loanProductPage;
    private static AddNewLoanProductPage addNewLoanProductPage;
    private static LoanProductOverviewPage loanProductOverviewPage;
    private static AccountPaymentInfoPage accountPaymentInfoPage;
    private static InterestRateChangeModalPage interestRateChangeModalPage;
    private static AmountDueInquiryModalPage amountDueInquiryModalPage;
    private static LoanPayoffChargesModalPage loanPayoffChargesModalPage;
    private static LoanPayoffPrepaymentPenaltyModalPage loanPayoffPrepaymentPenaltyModalPage;

    /**
     * Modal Windows
     */
    private static DebitCardModalWindow debitCardModalWindow;

    /**
     * This function return an instance of `LoginPage`
     */
    public static LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    /**
     * This function return an instance of `NavigationPage`
     */
    public static NavigationPage navigationPage() {
        if (navigationPage == null) {
            navigationPage = new NavigationPage();
        }
        return navigationPage;
    }

    /**
     * This function return an instance of `ASideMenuPage`
     */
    public static ASideMenuPage aSideMenuPage() {
        if (aSideMenuPage == null) {
            aSideMenuPage = new ASideMenuPage();
        }
        return aSideMenuPage;
    }

    /**
     * This function return an instance of `AddClientPage`
     */
    public static AddClientPage addClientPage() {
        if (addClientPage == null) {
            addClientPage = new AddClientPage();
        }
        return addClientPage;
    }

    /**
     * This function return an instance of `ClientsSearchPage`
     */
    public static ClientsSearchPage clientsSearchPage() {
        if (clientsSearchPage == null) {
            clientsSearchPage = new ClientsSearchPage();
        }
        return clientsSearchPage;
    }

    /**
     * This function return an instance of `ClientDetailsPage`
     */
    public static ClientDetailsPage clientDetailsPage() {
        if (clientDetailsPage == null) {
            clientDetailsPage = new ClientDetailsPage();
        }
        return clientDetailsPage;
    }

    /**
     * This function return an instance of `TellerPage`
     */
    public static TellerPage tellerPage() {
        if (tellerPage == null) {
            tellerPage = new TellerPage();
        }
        return tellerPage;
    }

    /**
     * This function return an instance of `CashierPage`
     */
    public static CashierPage cashierPage() {
        if (cashierPage == null) {
            cashierPage = new CashierPage();
        }
        return cashierPage;
    }

    /**
     * This function return an instance of `LoansPage`
     */
    public static LoansPage loansPage() {
        if (loansPage == null) {
            loansPage = new LoansPage();
        }
        return loansPage;
    }

    /**
     * This function return an instance of `ReportGeneratorPage`
     */
    public static ReportGeneratorPage reportGeneratorPage() {
        if (reportGeneratorPage == null) {
            reportGeneratorPage = new ReportGeneratorPage();
        }
        return reportGeneratorPage;
    }

    /**
     * This function return an instance of `TellerToTellerPage`
     */
    public static TellerToTellerPage tellerToTellerPage() {
        if (tellerToTellerPage == null) {
            tellerToTellerPage = new TellerToTellerPage();
        }
        return tellerToTellerPage;
    }

    /**
     * This function return an instance of `Settings`
     */
    public static Settings settings() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    /**
     * This function return an instance of `ClientsSearchResultsPage`
     */
    public static ClientsSearchResultsPage clientsSearchResultsPage() {
        if (clientsSearchResultsPage == null) {
            clientsSearchResultsPage = new ClientsSearchResultsPage();
        }
        return clientsSearchResultsPage;
    }

    /**
     * This function return an instance of `AddAccountPage`
     */
    public static AddAccountPage addAccountPage() {
        if (addAccountPage == null) {
            addAccountPage = new AddAccountPage();
        }
        return addAccountPage;
    }

    /**
     * This function return an instance of `AccountDetailsPage`
     */
    public static AccountDetailsPage accountDetailsPage() {
        if (accountDetailsPage == null) {
            accountDetailsPage = new AccountDetailsPage();
        }
        return accountDetailsPage;
    }

    /**
     * This function return an instance of `EditAccountPage`
     */
    public static EditAccountPage editAccountPage() {
        if (editAccountPage == null) {
            editAccountPage = new EditAccountPage();
        }
        return editAccountPage;
    }

    /**
     * This function return an instance of `AccountMaintenancePage`
     */
    public static AccountMaintenancePage accountMaintenancePage() {
        if (accountMaintenancePage == null) {
            accountMaintenancePage = new AccountMaintenancePage();
        }
        return accountMaintenancePage;
    }

    /**
     * This function return an instance of `AccountNavigationPage`
     */
    public static AccountNavigationPage accountNavigationPage() {
        if (accountNavigationPage == null) {
            accountNavigationPage = new AccountNavigationPage();
        }
        return accountNavigationPage;
    }

    /**
     * This function return an instance of `DebitCardModalWindow`
     */
    public static DebitCardModalWindow debitCardModalWindow() {
        if (debitCardModalWindow == null) {
            debitCardModalWindow = new DebitCardModalWindow();
        }
        return debitCardModalWindow;
    }

    /**
     * This function return an instance of `MaintenancePage`
     */
    public static MaintenancePage maintenancePage() {
        if (maintenancePage == null) {
            maintenancePage = new MaintenancePage();
        }
        return maintenancePage;
    }

    /**
     * This function return an instance of `MaintenanceHistoryPage`
     */
    public static MaintenanceHistoryPage maintenanceHistoryPage() {
        if (maintenanceHistoryPage == null) {
            maintenanceHistoryPage = new MaintenanceHistoryPage();
        }
        return maintenanceHistoryPage;
    }

    /**
     * This function return an instance of `TellerModalPage`
     */
    public static TellerModalPage tellerModalPage() {
        if (tellerModalPage == null) {
            tellerModalPage = new TellerModalPage();
        }
        return tellerModalPage;
    }

    /**
     * This function return an instance of `AccountTransactionPage`
     */
    public static AccountTransactionPage accountTransactionPage() {
        if (accountTransactionPage == null) {
            accountTransactionPage = new AccountTransactionPage();
        }
        return accountTransactionPage;
    }

    /**
     * This function return an instance of `AccountInstructionsPage`
     */
    public static AccountInstructionsPage accountInstructionsPage() {
        if (accountInstructionsPage == null) {
            accountInstructionsPage = new AccountInstructionsPage();
        }
        return accountInstructionsPage;
    }

    /**
     * This function return an instance of `AccountDetailsModalPage`
     */
    public static AccountDetailsModalPage accountDetailsModalPage() {
        if (accountDetailsModalPage == null) {
            accountDetailsModalPage = new AccountDetailsModalPage();
        }
        return accountDetailsModalPage;
    }

    /**
     * This function return an instance of `AccountStatementPage`
     */
    public static AccountStatementPage accountStatementPage() {
        if (accountStatementPage == null) {
            accountStatementPage = new AccountStatementPage();
        }
        return accountStatementPage;
    }

    /**
     * This function return an instance of `NotesPage`
     */
    public static NotesPage notesPage() {
        if (notesPage == null) {
            notesPage = new NotesPage();
        }
        return notesPage;
    }

    /**
     * This function return an instance of `Alerts`
     */
    public static Alerts alerts() {
        if (alerts == null) {
            alerts = new Alerts();
        }
        return alerts;
    }

    /**
     * This function return an instance of `DocumentsPage`
     */
    public static DocumentsPage documentsPage() {
        if (documentsPage == null) {
            documentsPage = new DocumentsPage();
        }
        return documentsPage;
    }

    /**
     * This function return an instance of `AddNewDocumentPage`
     */
    public static AddNewDocumentPage addNewDocumentPage() {
        if (addNewDocumentPage == null) {
            addNewDocumentPage = new AddNewDocumentPage();
        }
        return addNewDocumentPage;
    }

    /**
     * This function return an instance of `DocumentOverviewPage`
     */
    public static DocumentOverviewPage documentOverviewPage() {
        if (documentOverviewPage == null) {
            documentOverviewPage = new DocumentOverviewPage();
        }
        return documentOverviewPage;
    }

    /**
     * This function return an instance of `Transactions Page`
     */
    public static TransactionsPage transactionsPage() {
        if (transactionsPage == null) {
            transactionsPage = new TransactionsPage();
        }
        return transactionsPage;
    }

    /**
     * This function return an instance of `Transfers Page`
     */
    public static TransfersPage transfersPage() {
        if (transfersPage == null) {
            transfersPage = new TransfersPage();
        }
        return transfersPage;
    }

    /**
     * This function return an instance of `New Transfer Page`
     */
    public static NewTransferPage newTransferPage() {
        if (newTransferPage == null) {
            newTransferPage = new NewTransferPage();
        }
        return newTransferPage;
    }

    /**
     * This function return an instance of `View Transfer Page`
     */
    public static ViewTransferPage viewTransferPage() {
        if (viewTransferPage == null) {
            viewTransferPage = new ViewTransferPage();
        }
        return viewTransferPage;
    }

    /**
     * This function return an instance of `Edit Transfer Page`
     */
    public static EditTransferPage editTransferPage() {
        if (editTransferPage == null) {
            editTransferPage = new EditTransferPage();
        }
        return editTransferPage;
    }

    /**
     * This function return an instance of `Cash In Modal Page`
     */
    public static CashInModalPage cashInModalPage() {
        if (cashInModalPage == null) {
            cashInModalPage = new CashInModalPage();
        }
        return cashInModalPage;
    }

    /**
     * This function return an instance of `Cash Verify Conductor Page`
     */
    public static VerifyConductorPage verifyConductor() {
        if (verifyConductorPage == null) {
            verifyConductorPage = new VerifyConductorPage();
        }
        return verifyConductorPage;
    }

    /**
     * This function return an instance of `Transaction Completed Page`
     */
    public static TransactionCompletedPage transactionCompleted() {
        if (transactionCompleted == null) {
            transactionCompleted = new TransactionCompletedPage();
        }
        return transactionCompleted;
    }

    /**
     * This function return an instance of `JournalPage Page`
     */
    public static JournalPage journalPage() {
        if (journalPage == null) {
            journalPage = new JournalPage();
        }
        return journalPage;
    }

    /**
     * This function return an instance of `JournalDetails Page`
     */
    public static JournalDetailsPage journalDetailsPage() {
        if (journalDetailsPage == null) {
            journalDetailsPage = new JournalDetailsPage();
        }
        return journalDetailsPage;
    }

    /**
     * This function return an instance of `CashIn Modal Window Page`
     */
    public static CashInModalWindowPage cashInModalWindowPage() {
        if (cashInModalWindowPage == null) {
            cashInModalWindowPage = new CashInModalWindowPage();
        }
        return cashInModalWindowPage;
    }

    /**
     * This function return an instance of `Verify Conductor Modal Page`
     */
    public static VerifyConductorModalPage verifyConductorModalPage() {
        if (verifyConductorModalPage == null) {
            verifyConductorModalPage = new VerifyConductorModalPage();
        }
        return verifyConductorModalPage;
    }

    /**
     * This function return an instance of `AccountsPage Page`
     */
    public static AccountsPage accountsPage() {
        if (accountsPage == null) {
            accountsPage = new AccountsPage();
        }
        return accountsPage;
    }


    /**
     * This function return an instance of `CallStatementModalPage`
     */
    public static CallStatementModalPage callStatementModalPage() {
        if (callStatementModalPage == null) {
            callStatementModalPage = new CallStatementModalPage();
        }
        return callStatementModalPage;
    }

    /**
     * This function return an instance of `BalanceInquiryModalPage`
     */
    public static BalanceInquiryModalPage balanceInquiryModalPage() {
        if (balanceInquiryModalPage == null) {
            balanceInquiryModalPage = new BalanceInquiryModalPage();
        }
        return balanceInquiryModalPage;
    }

    /**
     * This function return an instance of `Supervisor modal page`
     */
    public static SupervisorModalPage supervisorModalPage() {
        if (supervisorModalPage == null) {
            supervisorModalPage = new SupervisorModalPage();
        }
        return supervisorModalPage;
    }

    /**
     * This function return an instance of `CardsManagementPage`
     */
    public static CardsManagementPage cardsManagementPage() {
        if (cardsManagementPage == null) {
            cardsManagementPage = new CardsManagementPage();
        }
        return cardsManagementPage;
    }

    /**
     * This function return an instance of `AttentionModalPage`
     */
    public static AttentionModalPage attentionModalPage() {
        if (attentionModalPage == null) {
            attentionModalPage = new AttentionModalPage();
        }
        return attentionModalPage;
    }

    /**
     * This function return an instance of `EditAccountTransactionModal`
     */
    public static EditAccountTransactionModal editAccountTransactionModal() {
        if (editAccountTransactionModal == null) {
            editAccountTransactionModal = new EditAccountTransactionModal();
        }
        return editAccountTransactionModal;
    }

    /**
     * This function return an instance of `CashDrawerBalancePage`
     */
    public static CashDrawerBalancePage cashDrawerBalancePage() {
        if (cashDrawerBalancePage == null) {
            cashDrawerBalancePage = new CashDrawerBalancePage();
        }
        return cashDrawerBalancePage;
    }

    /**
     * This function return an instance of `ConfirmModalPage`
     */
    public static ConfirmModalPage confirmModalPage() {
        if (confirmModalPage == null){
         confirmModalPage = new ConfirmModalPage();
        }
        return confirmModalPage;
    }

    /**
     * This function return an instance of `BackOfficePage`
     */
    public static BackOfficePage backOfficePage() {
        if (backOfficePage == null){
            backOfficePage = new BackOfficePage();
        }
        return backOfficePage;
    }

    /**
     * This function return an instance of `DocumentSearchNoticesPage`
     */
    public static DocumentSearchNoticesPage documentSearchNoticesPage() {
        if (documentSearchNoticesPage == null){
            documentSearchNoticesPage = new DocumentSearchNoticesPage();
        }
        return documentSearchNoticesPage;
    }

    /**
     * This function return an instance of `DocumentTransactionsPage`
     */
    public static DocumentSearchTransactionsPage documentSearchTransactionsPage() {
        if (documentSearchTransactionsPage == null){
            documentSearchTransactionsPage = new DocumentSearchTransactionsPage();
        }
        return documentSearchTransactionsPage;
    }

    /**
     * This function return an instance of `NoticePage`
     */
    public static NoticePage noticePage(){
        if (noticePage == null){
            noticePage = new NoticePage();
        }
        return noticePage;
    }

    /**
     * This function return instance of 'CheckPage'
     */
    public static CheckPage checkPage(){
        if(checkPage == null){
            checkPage = new CheckPage();
        }
        return checkPage;
    }

    /**
     * This function return instance of 'CheckPage'
     */
    public static FullCheckPage fullCheckPage(){
        if(fullCheckPage == null){
            fullCheckPage = new FullCheckPage();
        }
        return fullCheckPage;
    }


    /**
     * This function return an instance of `AccountCommercialAnalysisPage`
     */
    public static AccountCommercialAnalysisPage accountCommercialAnalysisPage(){
        if (accountCommercialAnalysisPage == null){
            accountCommercialAnalysisPage = new AccountCommercialAnalysisPage();
        }
        return accountCommercialAnalysisPage;
    }

    /**
     * This function return an instance of `PrintChecksPage`
     */
    public static PrintChecksPage printChecksPage(){
        if (printChecksPage == null){
            printChecksPage = new PrintChecksPage();
        }
        return printChecksPage;
    }

    /**
     * This function return an instance of `CheckPrintPage`
     */
    public static CheckPrintPage checkPrintPage(){
        if (checkPrintPage == null){
            checkPrintPage = new CheckPrintPage();
        }
        return checkPrintPage;
    }


    /**
     * This function return an instance of `ConfirmModalPage`
     */
    public static CashierDefinedActionsPage cashierDefinedActionsPage() {
        if (cashierDefinedActionsPage == null){
            cashierDefinedActionsPage = new CashierDefinedActionsPage();
        }
        return cashierDefinedActionsPage;
    }

    /**
     * This function return an instance of `AlertMessageModalPage`
     */
    public static AlertMessageModalPage alertMessageModalPage() {
        if (alertMessageModalPage == null){
            alertMessageModalPage = new AlertMessageModalPage();
        }
        return alertMessageModalPage;
    }
    /**
     * This function return an instance of `LoanProductPage`
     */
    public static LoanProductPage loanProductPage(){
        if (loanProductPage == null){
            loanProductPage = new LoanProductPage();
        }
        return loanProductPage;
    }

    /**
     * This function return an instance of `AddNewLoanProductPage`
     */
    public static AddNewLoanProductPage addNewLoanProductPage(){
        if (addNewLoanProductPage == null){
            addNewLoanProductPage = new AddNewLoanProductPage();
        }
        return addNewLoanProductPage;
    }

    /**
     * This function return an instance of `LoanProductOverviewPage`
     */
    public static LoanProductOverviewPage loanProductOverviewPage(){
        if (loanProductOverviewPage == null){
            loanProductOverviewPage = new LoanProductOverviewPage();
        }
        return loanProductOverviewPage;
    }

    /**
     * This function return an instance of `AccountPaymentInfoPage`
     */
    public static AccountPaymentInfoPage accountPaymentInfoPage(){
        if (accountPaymentInfoPage == null){
            accountPaymentInfoPage = new AccountPaymentInfoPage();
        }
        return accountPaymentInfoPage;
    }

    /**
     * This function return an instance of `InterestRateChangeModalPage`
     */
    public static InterestRateChangeModalPage interestRateChangeModalPage(){
        if (interestRateChangeModalPage == null){
            interestRateChangeModalPage = new InterestRateChangeModalPage();
        }
        return interestRateChangeModalPage;
    }

    /**
     * This function return an instance of `AmountDueInquiryModalPage`
     */
    public static AmountDueInquiryModalPage amountDueInquiryModalPage(){
        if (amountDueInquiryModalPage == null){
            amountDueInquiryModalPage = new AmountDueInquiryModalPage();
        }
        return amountDueInquiryModalPage;
    }

    /**
     * This function return an instance of `LoanPayoffChargesModalPage`
     */
    public static LoanPayoffChargesModalPage loanPayoffChargesModalPage(){
        if (loanPayoffChargesModalPage == null){
            loanPayoffChargesModalPage = new LoanPayoffChargesModalPage();
        }
        return loanPayoffChargesModalPage;
    }

    /**
     * This function return an instance of `LoanPayoffPrepaymentPenaltyModalPage`
     */
    public static LoanPayoffPrepaymentPenaltyModalPage loanPayoffPrepaymentPenaltyModalPage(){
        if (loanPayoffPrepaymentPenaltyModalPage == null){
            loanPayoffPrepaymentPenaltyModalPage = new LoanPayoffPrepaymentPenaltyModalPage();
        }
        return loanPayoffPrepaymentPenaltyModalPage;
    }
}