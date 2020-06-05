package com.nymbus.actions.account;

public class AccountActions {

    /**
     * Actions
     */
    private static CreateAccount createAccount;
    private static EditAccount editAccount;
    private static RetrievingAccountData retrievingAccountData;
    private static CreateInstruction createInstruction;
    private static CallStatement callStatement;
    private static AccountTransactionActions accountTransactionActions;
    private static EditInstructionActions editInstructionActions;
    private static AccountMaintenanceActions accountMaintenanceActions;
    private static VerifyingAccountDataActions verifyingAccountDataActions;

    /**
     * This function return an instance of `CreateAccount`
     */
    public static CreateAccount createAccount() {
        if (createAccount == null) {
            createAccount = new CreateAccount();
        }
        return createAccount;
    }

    /**
     * This function return an instance of `EditAccount`
     */
    public static EditAccount editAccount() {
        if (editAccount == null) {
            editAccount = new EditAccount();
        }
        return editAccount;
    }

    /**
     * This function return an instance of `RetrievingAccountData`
     */
    public static RetrievingAccountData retrievingAccountData() {
        if (retrievingAccountData == null) {
            retrievingAccountData = new RetrievingAccountData();
        }
        return retrievingAccountData;
    }

    /**
     * This function return an instance of `CreateInstruction`
     */
    public static CreateInstruction createInstruction() {
        if (createInstruction == null) {
            createInstruction = new CreateInstruction();
        }
        return createInstruction;
    }

    /**
     * This function return an instance of `CreateInstruction`
     */
    public static CallStatement callStatement() {
        if (callStatement == null) {
            callStatement = new CallStatement();
        }
        return callStatement;
    }

    /**
     * This function return an instance of `AccountTransactionActions`
     */
    public static AccountTransactionActions accountTransactionActions() {
        if (accountTransactionActions == null) {
            accountTransactionActions = new AccountTransactionActions();
        }
        return accountTransactionActions;
    }

    /**
     * This function return an instance of `EditInstruction`
     */
    public static EditInstructionActions editInstructionActions() {
        if (editInstructionActions == null) {
            editInstructionActions = new EditInstructionActions();
        }
        return editInstructionActions;
    }

    /**
     * This function return an instance of `AccountMaintenanceActions`
     */
    public static AccountMaintenanceActions accountMaintenanceActions() {
        if (accountMaintenanceActions == null) {
            accountMaintenanceActions = new AccountMaintenanceActions();
        }
        return accountMaintenanceActions;
    }

    /**
     * This function return an instance of `VerifyingAccountDataActions`
     */
    public static VerifyingAccountDataActions verifyingAccountDataActions() {
        if (verifyingAccountDataActions == null) {
            verifyingAccountDataActions = new VerifyingAccountDataActions();
        }
        return verifyingAccountDataActions;
    }
}