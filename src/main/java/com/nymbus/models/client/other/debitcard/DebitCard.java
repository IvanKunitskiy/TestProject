package com.nymbus.models.client.other.debitcard;

import com.nymbus.models.client.other.account.type.Account;
import com.nymbus.models.settings.bincontrol.BinControl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class DebitCard {
    @NonNull private BinControl binControl;
    @NonNull private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @NonNull private String nameOfCard;
    private String secondLineEmbossing;
    @NonNull private List<Account> accounts; // At least one
    private String cardDesign;
    @NonNull CardStatus cardStatus;
    private String pinOffset;
    private String dateEffective;
    private double ATMDailyDollarLimit;
    private int ATMTransactionLimit;
    private double DBCDailyDollarLimit;
    private int DBCTransactionLimit;
    private TranslationTypeAllowed translationTypeAllowed;
    private boolean changeForCardReplacement;
    private boolean allowForeignTransactions;
}
