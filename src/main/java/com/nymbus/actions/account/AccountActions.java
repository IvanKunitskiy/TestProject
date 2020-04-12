package com.nymbus.actions.account;

public class AccountActions {

    /**
     * Actions
     */
    private static CreateAccount createAccount;
    private static EditAccount editAccount;
    private static RetrievingAccountData retrievingAccountData;
    private static CreateInstruction createInstruction;

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
}
