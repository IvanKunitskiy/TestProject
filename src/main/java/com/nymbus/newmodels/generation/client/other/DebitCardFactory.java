package com.nymbus.newmodels.generation.client.other;

import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.util.Random;

import java.util.ArrayList;

public class DebitCardFactory {
    public DebitCard getDebitCard() {
        DebitCard debitCard = new DebitCard();
        debitCard.setCardNumber(""); // Will be generated automatically by the platform
        debitCard.setClientNumber(Random.genString(10));
        debitCard.setNameOnCard(Random.genString(10));
        debitCard.setSecondLineEmbossing(Random.genString(10));
        debitCard.setAccounts(new ArrayList<>());
        debitCard.setCardDesign(Random.genString(10));
        debitCard.setCardStatus(CardStatus.ACTIVE);
        debitCard.setPinOffset(Random.genInt(1000, 9999));
        debitCard.setDateEffective(Random.genString(10)); // TODO: Need to fix
        debitCard.setATMDailyDollarLimit("1,500.00");
        debitCard.setATMTransactionLimit("15");
        debitCard.setDBCDailyDollarLimit("1,500.00");
        debitCard.setDBCTransactionLimit("15");
        debitCard.setTranslationTypeAllowed(TranslationTypeAllowed.PIN_TRANSACTIONS_ONLY);
        debitCard.setChargeForCardReplacement(false);
        debitCard.setAllowForeignTransactions(false);

        return debitCard;
    }
}
