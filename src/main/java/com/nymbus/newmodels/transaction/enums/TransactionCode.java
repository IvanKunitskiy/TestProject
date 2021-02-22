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
    CHECK("128 - Check"),
    GL_CREDIT_865("865 - G/L Credit"),
    CASH_IN_850("850 - Cash In"),
    DEPOSIT_CORR_104("104 - Deposit Corr"),
    NORMAL_DEPOSIT_211("211 - Normal Deposit"),
    DEBIT_MEM0_319("319 - Debit Memo"),
    TAXES_WH_CRED_302("302 - Taxes W/H Cred"),
    NEW_CD_311("311 - New CD"),
    DEBIT_TRANSFER_221("221 - Debit Transfer"),
    CREDIT_TRANSFER_101("101 - Credit Transfr"),
    REDEEMED_CD_315("315 - Redeemed CD"),
    INT_PAID_COMP_307("307 - Int Paid Comp"),
    GL_DEBIT("860 - G/L Debit"),
    NEW_LOAN_411("411 - New Loan"),
    TAKE_FROM_IENC_408("408 - Take From IENC"),
    ADD_TO_IENC_409("409 - Add To IENC"),
    LOAN_PAYMENT_114("114 - Loan Payment"),
    PAYOFF_433("433 - Payoff"),
    PAYMENT_416("416 - Payment");


    private final String transCode;
}