package com.nymbus.models.settings;

import com.nymbus.models.RequiredField;
import com.nymbus.models.client.basicinformation.type.ClientType;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BinControl {
    @RequiredField private String binNumber;
    @RequiredField private String cardDescription;
    @RequiredField private String initialsForDescription;
    @RequiredField private boolean isBINActive;
    @RequiredField private boolean isCommercialOnlyBIN;
    @RequiredField private List<ClientType> clientTypes; // At least one
    private Set<String> cardDesigns;
    @RequiredField private int incrementCardNumberBy;
    @RequiredField private int cardLifeInMonths;
    @RequiredField private boolean issueReplacementCardBySameNumber;
    @RequiredField private boolean includeOverdraftProtectionInAvailableBalance;
    @RequiredField private double ATMDailyDollarLimit;
    @RequiredField private int ATMTransactionLimit;
    @RequiredField private double DBCDailyDollarLimit;
    @RequiredField private int DBCTransactionLimit;
    @RequiredField private double replacementCardFee;
    @RequiredField private String replacementCardCode;
}
