package com.nymbus.newmodels.client.other.debitcard;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.client.other.debitcard.types.CardDesign;
import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DebitCard {
    @NonNull private BinControl binControl;
    @NonNull private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @Getter(AccessLevel.NONE) @NonNull private String nameOnCard;
    private String secondLineEmbossing;
    @NonNull private List<String> accounts; // At least one
    private CardDesign cardDesign;
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

    public String getNameOnCard() {
        return Functions.setNameOnDebitCard(nameOnCard);
    }
}