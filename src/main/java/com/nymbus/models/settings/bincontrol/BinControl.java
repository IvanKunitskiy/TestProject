package com.nymbus.models.settings.bincontrol;

import com.nymbus.models.client.basicinformation.type.ClientType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class BinControl {
    @NonNull private String binNumber;
    @NonNull private String cardDescription;
    @NonNull private String initialsForDescription;
    @NonNull private boolean isBINIsActive;
    @NonNull private boolean isCommercialOnlyBIN;
    private String replacementBIN;
    @NonNull private List<ClientType> clientTypes; // At least one
    private Set<String> cardDesigns;
    @NonNull private String incrementCardNumberBy;
    @NonNull private int cardLifeInMonths;
    @NonNull private boolean issueReplacementCardBySameNumber;
    @NonNull private boolean includeOverdraftProtectionInAvailableBalance;
    @NonNull private double ATMDailyDollarLimit;
    @NonNull private int ATMTransactionLimit;
    @NonNull private double DBCDailyDollarLimit;
    @NonNull private int DBCTransactionLimit;
    @NonNull private double replacementCardFee;
    @NonNull private ReplacementCardCode replacementCardCode;
}
