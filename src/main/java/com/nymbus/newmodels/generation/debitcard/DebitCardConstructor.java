package com.nymbus.newmodels.generation.debitcard;

import com.nymbus.newmodels.client.other.debitcard.types.CardDesign;
import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.util.Random;

import java.util.ArrayList;

public class DebitCardConstructor {
    public void constructDebitCard(DebitCardBuilder debitCardBuilder) {
        debitCardBuilder.setCardNumber("");
        debitCardBuilder.setClientNumber(Random.genString(10));
        debitCardBuilder.setNameOnCard(Random.genString(10));
        debitCardBuilder.setSecondLineEmbossing(Random.genString(10));
        debitCardBuilder.setAccounts(new ArrayList<>());
        debitCardBuilder.setCardDesign(CardDesign.CRD);
        debitCardBuilder.setCardStatus(CardStatus.ACTIVE);
        debitCardBuilder.setPinOffset(Random.genInt(1000, 9999));
        debitCardBuilder.setDateEffective("");
        debitCardBuilder.setATMDailyDollarLimit("1,500.00");
        debitCardBuilder.setATMTransactionLimit("15");
        debitCardBuilder.setDBCDailyDollarLimit("1,500.00");
        debitCardBuilder.setDBCTransactionLimit("15");
        debitCardBuilder.setTranslationTypeAllowed(TranslationTypeAllowed.PIN_TRANSACTIONS_ONLY);
        debitCardBuilder.setChargeForCardReplacement(false);
        debitCardBuilder.setAllowForeignTransactions(false);
    }
}
