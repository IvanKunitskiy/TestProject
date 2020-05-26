package com.nymbus.newmodels.generation.bincontrol.builder;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.settings.bincontrol.ReplacementCardCode;

import java.util.List;
import java.util.Set;

public interface IBinControlBuilder {
    void setBinNumber(String binNumber);
    void setCardDescription(String cardDescription);
    void setInitialsForDescription(String initialsForDescription);
    void setBINIsActive(Boolean binIsActive);
    void setCommercialOnlyBIN(Boolean commercialOnlyBIN);
    void setReplacementBIN(String replacementBIN);
    void setClientTypes(List<ClientType> clientTypes); // At least one);
    void setCardDesigns(Set<String> cardDesigns);
    void setIncrementCardNumberBy(String cardNumberBy);
    void setCardLifeInMonths(int cardLifeInMonths);
    void setIssueReplacementCardBySameNumber(Boolean issueReplacementCardBySameNumber);
    void setIncludeOverdraftProtectionInAvailableBalance(Boolean includeOverdraftProtectionInAvailableBalance);
    void setATMDailyDollarLimit(String atmDailyDollarLimit);
    void setATMTransactionLimit(String atmTransactionLimit);
    void setDBCDailyDollarLimit(String dbcDailyDollarLimit);
    void setDBCTransactionLimit(String dbcTransactionLimit);
    void setReplacementCardFee(String replacementCardFee);
    void setReplacementCardCode(ReplacementCardCode replacementCardCode);
}
