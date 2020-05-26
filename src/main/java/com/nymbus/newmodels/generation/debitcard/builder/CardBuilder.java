package com.nymbus.newmodels.generation.debitcard.builder;

import com.nymbus.newmodels.client.other.debitcard.types.CardStatus;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.newmodels.settings.bincontrol.BinControl;

import java.util.List;

public interface CardBuilder {
    void setCardNumber(String cardNumber);
    void setBinControl(BinControl binControl);
    void setClientNumber(String clientNumber);
    void setNameOnCard(String nameOnCard);
    void setSecondLineEmbossing(String secondLineEmbossing);
    void setAccounts(List<String> accounts);
    void setCardDesign(String cardDesign);
    void setCardStatus(CardStatus cardStatus);
    void setPinOffset(int pinOffset);
    void setDateEffective(String dateEffective);
    void setATMDailyDollarLimit(String atmDailyDollarLimit);
    void setATMTransactionLimit(String atmTransactionLimit);
    void setDBCDailyDollarLimit(String dbcDailyDollarLimit);
    void setDBCTransactionLimit(String dbcTransactionLimit);
    void setTranslationTypeAllowed(TranslationTypeAllowed translationTypeAllowed);
    void setChargeForCardReplacement(boolean chargeForCardReplacement);
    void setAllowForeignTransactions(boolean allowForeignTransactions);
}
