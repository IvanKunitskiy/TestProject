package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionCode {
    ATM_WITHDRAWAL_124("124 - ATM Withdrawal"),
    ATM_WITHDRAWAL_224("224 - ATM Withdrawal"),
    ATM_DEBIT_MEMO_219("219 - Debit Memo"),
    ATM_DEBIT_MEMO_119("119 - Debit Memo"),
    ATM_DEBIT_PURCHASE_123("123 - Debit Purchase"),
    ATM_DEBIT_PURCHASE_223("223 - Debit Purchase"),
    ATM_CREDIT_MEMO_103("103 - Credit Memo"),
    ATM_CREDIT_MEMO_203("203 - Credit Memo"),
    ATM_DEPOSIT_209("209 - Deposit"),
    WITHDRAW_AND_CLOSE("127 - Withdraw&Close"),
    WITHDRAW_AND_CLOSE_SAVINGS("227 - Withdraw&Close"),
    INT_DEPOSIT("107 - Int Deposit"),
    WITHDRAWAL_216("216 - Withdrawal"),
    WITHDRAWAL_116("116 - Withdrawal"),
    ATM_DEPOSIT_108("108 - ATM Deposit"),
    ATM_DEPOSIT_109("109 - Deposit"),
    ATM_DEPOSIT_208("208 - ATM Deposit"),
    INT_DEPOSIT_SAVINGS("207 - Int Deposit"),
    CD_REDEMPTION("315 - Redeemed CD"),
    INT_CD_REDEMPTION("307 - Int Paid Comp"),
    ATM_USAGE_129("129 - ATM Usage"),
    ATM_USAGE_129_FEE("129 - ATM Usage Fee"),
    ATM_USAGE_229_FEE("229 - ATM Usage Fee"),
    DEBIT_PURCHASE_123("123 - Debit Purchase"),
    CUR_Yr_Contribution_330("330 - Cur Yr Contrib"),
    CUR_Yr_Contribution_2330("2330 - Cur Yr Contrib"),
    GL_CREDIT_865("865 - G/L Credit");

    private final String transCode;
}