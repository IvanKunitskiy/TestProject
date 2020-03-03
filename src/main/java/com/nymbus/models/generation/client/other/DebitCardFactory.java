package com.nymbus.models.generation.client.other;

import com.nymbus.models.client.other.debitcard.CardStatus;
import com.nymbus.models.client.other.debitcard.DebitCard;
import com.nymbus.models.client.other.debitcard.TranslationTypeAllowed;
import com.nymbus.util.Random;

import java.util.Collections;

public class DebitCardFactory {
    public DebitCard getDebitCard() {
        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber(Random.genString(16));
        debitCard.setClientNumber(Random.genString(10));
        debitCard.setNameOnCard(Random.genString(10));
        debitCard.setSecondLineEmbossing(Random.genString(10));
        debitCard.setAccounts(Collections.emptyList());
        debitCard.setCardDesign(Random.genString(10));
        debitCard.setCardStatus(CardStatus.ACTIVE);
        debitCard.setPinOffset(Random.genString(10));
        debitCard.setDateEffective(Random.genString(10));
        debitCard.setATMDailyDollarLimit(1_500.00);
        debitCard.setATMTransactionLimit(15);
        debitCard.setDBCDailyDollarLimit(1_500.00);
        debitCard.setDBCTransactionLimit(15);
        debitCard.setTranslationTypeAllowed(TranslationTypeAllowed.PIN_TRANSACTIONS_ONLY);
        debitCard.setChangeForCardReplacement(false);
        debitCard.setAllowForeignTransactions(false);

        return debitCard;
    }
}
