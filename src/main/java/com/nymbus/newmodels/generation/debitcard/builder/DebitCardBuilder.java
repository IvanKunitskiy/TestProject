package com.nymbus.newmodels.generation.debitcard.builder;

import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.newmodels.settings.bincontrol.BinControl;

import java.util.List;

public class DebitCardBuilder implements CardBuilder {

    private DebitCard debitCard;

    public DebitCardBuilder() {
        this.debitCard = new DebitCard();
    }

    @Override
    public void setCardNumber(String cardNumber) {
        debitCard.setCardNumber(cardNumber);
    }

    @Override
    public void setBinControl(BinControl binControl) {
        debitCard.setBinControl(binControl);
    }

    @Override
    public void setClientNumber(String clientNumber) {
        debitCard.setClientNumber(clientNumber);
    }

    @Override
    public void setNameOnCard(String nameOnCard) {
        debitCard.setNameOnCard(nameOnCard);
    }

    @Override
    public void setSecondLineEmbossing(String secondLineEmbossing) {
        debitCard.setSecondLineEmbossing(secondLineEmbossing);
    }

    @Override
    public void setAccounts(List<String> accounts) {
        debitCard.setAccounts(accounts);
    }

    @Override
    public void setCardDesign(String cardDesign) {
        debitCard.setCardDesign(cardDesign);
    }

    @Override
    public void setCardStatus(CardStatus cardStatus) {
        debitCard.setCardStatus(cardStatus);
    }

    @Override
    public void setPinOffset(int pinOffset) {
        debitCard.setPinOffset(pinOffset);
    }

    @Override
    public void setDateEffective(String dateEffective) { // Check parameter type
        debitCard.setDateEffective(dateEffective);
    }

    @Override
    public void setATMDailyDollarLimit(String atmDailyDollarLimit) {
        debitCard.setATMDailyDollarLimit(atmDailyDollarLimit);
    }

    @Override
    public void setATMTransactionLimit(String atmTransactionLimit) {
        debitCard.setATMTransactionLimit(atmTransactionLimit);
    }

    @Override
    public void setDBCDailyDollarLimit(String dbcDailyDollarLimit) {
        debitCard.setDBCDailyDollarLimit(dbcDailyDollarLimit);
    }

    @Override
    public void setDBCTransactionLimit(String dbcTransactionLimit) {
        debitCard.setDBCTransactionLimit(dbcTransactionLimit);
    }

    @Override
    public void setTranslationTypeAllowed(TranslationTypeAllowed translationTypeAllowed) {
        debitCard.setTranslationTypeAllowed(translationTypeAllowed);
    }

    @Override
    public void setChargeForCardReplacement(boolean chargeForCardReplacement) {
        debitCard.setChargeForCardReplacement(chargeForCardReplacement);
    }

    @Override
    public void setAllowForeignTransactions(boolean allowForeignTransactions) {
        debitCard.setAllowForeignTransactions(allowForeignTransactions);
    }

    public DebitCard getCard() {
        return debitCard;
    }
}
