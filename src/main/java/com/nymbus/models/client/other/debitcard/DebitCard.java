package com.nymbus.models.client.other.debitcard;

import com.nymbus.models.client.other.account.type.Account;
import com.nymbus.models.settings.bincontrol.BinControl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DebitCard {
    @NonNull private BinControl binControl;
    @NonNull private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @NonNull private String nameOnCard;
    private String secondLineEmbossing;
    @NonNull private List<Account> accounts; // At least one
    private String cardDesign;
    @NonNull CardStatus cardStatus;
    private int pinOffset;
    private String dateEffective;
    private String ATMDailyDollarLimit;
    private String ATMTransactionLimit;
    private String DBCDailyDollarLimit;
    private String DBCTransactionLimit;
    private TranslationTypeAllowed translationTypeAllowed;
    private boolean chargeForCardReplacement;
    private boolean allowForeignTransactions;
}
