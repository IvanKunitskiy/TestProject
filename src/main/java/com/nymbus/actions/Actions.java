package com.nymbus.actions;

import com.nymbus.actions.backoffice.printchecks.PrintChecksActions;
import com.nymbus.actions.balanceinquiry.BalanceInquiryActions;
import com.nymbus.actions.cashierdefined.CashierDefinedActions;
import com.nymbus.actions.check.BackOfficeActions;
import com.nymbus.actions.clients.ClientPageActions;
import com.nymbus.actions.clients.ClientsSearchResultsPageActions;
import com.nymbus.actions.clients.maintenance.MaintenanceHistoryPageActions;
import com.nymbus.actions.clients.maintenance.MaintenancePageActions;
import com.nymbus.actions.journal.JournalActions;
import com.nymbus.actions.loans.AddNewLoanActions;
import com.nymbus.actions.loans.LoanPayoffActions;
import com.nymbus.actions.loans.LoanProductOverviewActions;
import com.nymbus.actions.modalwindow.DebitCardModalWindowActions;
import com.nymbus.actions.modalwindow.ParticipationsActions;
import com.nymbus.actions.modalwindow.ReservePremiumProcessingModalActions;
import com.nymbus.actions.settings.*;
import com.nymbus.actions.teller.TellerActions;
import com.nymbus.actions.transaction.NonTellerTransactionActions;
import com.nymbus.actions.transaction.TransactionActions;
import com.nymbus.apirequest.NonTellerTransaction;

public class Actions {
    /**
     * Page actions
     * */
    private static LoginActions loginActions;
    private static UsersActions usersActions;
    private static CashDrawerAction cashDrawerAction;
    private static ClientPageActions clientPageActions;
    private static ClientsSearchResultsPageActions clientsSearchResultsPageActions;
    private static MaintenancePageActions maintenancePageActions;
    private static MaintenanceHistoryPageActions maintenanceHistoryPageActions;
    private static TransactionActions transactionActions;
    private static MainActions mainActions;
    private static JournalActions journalActions;
    private static TellerActions tellerActions;
    private static NonTellerTransactionActions nonTellerTransactionActions;
    private static BalanceInquiryActions balanceInquiryActions;
    private static NonTellerTransaction nonTellerTransaction;
    private static ProductsActions productsActions;
    private static TellerBankBranchOverviewActions tellerBankBranchOverviewActions;
    private static CashierDefinedActions cashierDefinedActions;
    private static PrintChecksActions printChecksActions;
    private static LoanProductOverviewActions loanProductOverviewActions;
    private static AddNewLoanActions addNewLoanActions;
    private static CallClassCodesActions callClassCodesActions;
    private static LoanPayoffActions loanPayoffActions;
    private static ParticipationsActions participationsActions;
    private static LoansActions loansActions;
    private static ReservePremiumProcessingModalActions reservePremiumProcessingModalPageActions;

    /**
     * Modal Window Actions
     * */
    private static DebitCardModalWindowActions debitCardModalWindowActions;
    private static BackOfficeActions backOfficeActions;

    /**
     * This function returns an instance of `LoginActions`
     */
    public static LoginActions loginActions() {
        if (loginActions == null) {
            loginActions = new LoginActions();
        }
        return loginActions;
    }

    /**
     * This function returns an instance of `UsersActions`
     */
    public static UsersActions usersActions() {
        if (usersActions == null) {
            usersActions = new UsersActions();
        }
        return usersActions;
    }

    /**
     * This function returns an instance of `LoansActions`
     */
    public static LoansActions loansActions() {
        if (loansActions == null) {
            loansActions = new LoansActions();
        }
        return loansActions;
    }

    /**
     * This function returns an instance of `CashDrawerAction`
     */
    public static CashDrawerAction cashDrawerAction() {
        if (cashDrawerAction == null) {
            cashDrawerAction = new CashDrawerAction();
        }
        return cashDrawerAction;
    }

    /**
     * This function returns an instance of `ClientPageActions`
     */
    public static ClientPageActions clientPageActions() {
        if (clientPageActions == null) {
            clientPageActions = new ClientPageActions();
        }
        return clientPageActions;
    }

    /**
     * This function returns an instance of `ClientsSearchResultsPageActions`
     */
    public static ClientsSearchResultsPageActions clientsSearchResultsPageActions() {
        if (clientsSearchResultsPageActions == null) {
            clientsSearchResultsPageActions = new ClientsSearchResultsPageActions();
        }
        return clientsSearchResultsPageActions;
    }

    /**
     * This function returns an instance of `MaintenancePageActions`
     */
    public static MaintenancePageActions maintenancePageActions() {
        if (maintenancePageActions == null) {
            maintenancePageActions = new MaintenancePageActions();
        }
        return maintenancePageActions;
    }

    /**
     * This function returns an instance of `DebitCardModalWindowActions`
     */
    public static DebitCardModalWindowActions debitCardModalWindowActions() {
        if (debitCardModalWindowActions == null) {
            debitCardModalWindowActions = new DebitCardModalWindowActions();
        }
        return debitCardModalWindowActions;
    }

    /**
     * This function returns an instance of `MaintenanceHistoryPageActions`
     */
    public static MaintenanceHistoryPageActions maintenanceHistoryPageActions() {
        if (maintenanceHistoryPageActions == null) {
            maintenanceHistoryPageActions = new MaintenanceHistoryPageActions();
        }
        return maintenanceHistoryPageActions;
    }

    /**
     * This function returns an instance of `TransactionActions`
     */
    public static TransactionActions transactionActions() {
        if (transactionActions == null) {
            transactionActions = new TransactionActions();
        }
        return transactionActions;
    }

    /**
     * This function returns an instance of `MainActions`
     */
    public static MainActions mainActions() {
        if (mainActions == null) {
            mainActions = new MainActions();
        }
        return mainActions;
    }

    /**
     * This function returns an instance of `JournalActions`
     */
    public static JournalActions journalActions() {
        if (journalActions == null) {
            journalActions = new JournalActions();
        }
        return journalActions;
    }

    /**
     * This function returns an instance of `TellerActions`
     */
    public static TellerActions tellerActions() {
        if (tellerActions == null) {
            tellerActions = new TellerActions();
        }
        return tellerActions;
    }

    /**
     * This function returns an instance of `NonTellerTransactionActions`
     */
    public static NonTellerTransactionActions nonTellerTransactionActions() {
        if (nonTellerTransactionActions == null) {
            nonTellerTransactionActions = new NonTellerTransactionActions();
        }
        return nonTellerTransactionActions;
    }

    /**
     * This function returns an instance of `BalanceInquiryActions`
     */
    public static BalanceInquiryActions balanceInquiryActions() {
        if (balanceInquiryActions == null) {
            balanceInquiryActions = new BalanceInquiryActions();
        }
        return balanceInquiryActions;
    }

    public static NonTellerTransaction nonTellerTransaction() {
        if (nonTellerTransaction == null) {
            nonTellerTransaction = new NonTellerTransaction();
        }
        return nonTellerTransaction;
    }

    /**
     * This function returns an instance of `ProductsActions`
     */
    public static ProductsActions productsActions() {
        if (productsActions == null) {
            productsActions = new ProductsActions();
        }
        return productsActions;
    }

    /**
     * This function returns an instance of `TellerBankBranchOverviewActions`
     */
    public static TellerBankBranchOverviewActions tellerBankBranchOverviewActions() {
        if (tellerBankBranchOverviewActions == null) {
            tellerBankBranchOverviewActions = new TellerBankBranchOverviewActions();
        }
        return tellerBankBranchOverviewActions;
    }

    /**
     * This function returns an instance of `CashierDefinedActionsPage`
     */
    public static CashierDefinedActions cashierDefinedActions() {
        if (cashierDefinedActions == null) {
            cashierDefinedActions = new CashierDefinedActions();
        }
        return cashierDefinedActions;
    }

    public static BackOfficeActions backOfficeActions() {
        if (backOfficeActions == null){
            backOfficeActions = new BackOfficeActions();
        }
        return backOfficeActions;
    }

    /**
     * This function returns an instance of `PrintChecksActions`
     */
    public static PrintChecksActions printChecksActions() {
        if (printChecksActions == null) {
            printChecksActions = new PrintChecksActions();
        }
        return printChecksActions;
    }

    /**
     * This function returns an instance of `LoanProductOverviewActions`
     */
    public static LoanProductOverviewActions loanProductOverviewActions() {
        if (loanProductOverviewActions == null) {
            loanProductOverviewActions = new LoanProductOverviewActions();
        }
        return loanProductOverviewActions;
    }

    /**
     * This function returns an instance of `AddNewLoanActions`
     */
    public static AddNewLoanActions addNewLoanActions() {
        if (addNewLoanActions == null) {
            addNewLoanActions = new AddNewLoanActions();
        }
        return addNewLoanActions;
    }

    /**
     * This function returns an instance of `CallClassCodesActions`
     */
    public static CallClassCodesActions callClassCodesActions() {
        if (callClassCodesActions == null) {
            callClassCodesActions = new CallClassCodesActions();
        }
        return callClassCodesActions;
    }

    /**
     * This function returns an instance of `LoanPayoffActions`
     */
    public static LoanPayoffActions loanPayoffActions() {
        if (loanPayoffActions == null) {
            loanPayoffActions = new LoanPayoffActions();
        }
        return loanPayoffActions;
    }

    /**
     * This function returns an instance of `ParticipationsActions`
     */
    public static ParticipationsActions participationsActions() {
        if (participationsActions == null) {
            participationsActions = new ParticipationsActions();
        }
        return participationsActions;
    }

    /**
     * This function returns an instance of `ReservePremiumProcessingModalActions`
     */
    public static ReservePremiumProcessingModalActions reservePremiumProcessingModalPageActions() {
        if (reservePremiumProcessingModalPageActions == null) {
            reservePremiumProcessingModalPageActions = new ReservePremiumProcessingModalActions();
        }
        return reservePremiumProcessingModalPageActions;
    }
}