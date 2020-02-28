package com.nymbus.models.client.other.debitcard;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.other.account.Account;
import com.nymbus.models.settings.bincontrol.BinControl;
import lombok.Data;

import java.util.List;

@Data
public class DebitCard {
    @RequiredField private BinControl binControl;
    @RequiredField private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @RequiredField private String nameOfCard;
    private String secondLineEmbossing;
    @RequiredField private List<Account> accounts; // At least one
    private String cardDesign;
    @RequiredField CardStatus cardStatus;
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
