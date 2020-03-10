package com.nymbus.actions.account;

public class AccountActions {

    /**
     * Actions
     */
    private static CreateAccount createAccount;
    private static EditAccount editAccount;

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
}
