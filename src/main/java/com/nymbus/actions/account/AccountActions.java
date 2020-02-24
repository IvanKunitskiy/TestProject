package com.nymbus.actions.account;

public class AccountActions {

    /**
     * Actions
     */
    private static CreateAccount createAccount;

    /**
     * This function return an instance of `CreateAccount`
     */
    public static CreateAccount createAccount() {
        if (createAccount == null) {
            createAccount = new CreateAccount();
        }
        return createAccount;
    }
}
