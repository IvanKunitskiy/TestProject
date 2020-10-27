package com.nymbus.newmodels.generation.bincontrol;

import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.settings.bincontrol.ReplacementCardCode;
import com.nymbus.util.Random;

import java.util.Collections;
import java.util.HashSet;

public class BinControlConstructor {
    public void constructBinControl(BinControlBuilder binControlBuilder) {
       setBinData(binControlBuilder);
    }

    private void setBinData(BinControlBuilder binControlBuilder) {
        switch (Constants.getEnvironment()) {
            case "dev6":
            case "dev12":
            default:
               setDev6EnvironmentData(binControlBuilder);
               break;
            case "dev21":
            case "dev4":
               setDev21EnvironmentData(binControlBuilder);
               break;
        }
    }

    private void setDev6EnvironmentData(BinControlBuilder binControlBuilder) {
        binControlBuilder.setBinNumber(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControlBuilder.setCardDescription(Random.genString(10));
        binControlBuilder.setInitialsForDescription(Random.genString(10));
        binControlBuilder.setBINIsActive(false);
        binControlBuilder.setCommercialOnlyBIN(false);
        binControlBuilder.setReplacementBIN("");
        binControlBuilder.setClientTypes(Collections.singletonList(ClientType.DIRECTOR));
        binControlBuilder.setCardDesigns(new HashSet<>());
        binControlBuilder.setIncrementCardNumberBy(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControlBuilder.setCardLifeInMonths(48);
        binControlBuilder.setIssueReplacementCardBySameNumber(true);
        binControlBuilder.setIncludeOverdraftProtectionInAvailableBalance(false);
        binControlBuilder.setATMDailyDollarLimit("500.00");
        binControlBuilder.setATMTransactionLimit("45");
        binControlBuilder.setDBCDailyDollarLimit("2,500.00");
        binControlBuilder.setDBCTransactionLimit("45");
        binControlBuilder.setReplacementCardFee("150.00");
        binControlBuilder.setReplacementCardCode(ReplacementCardCode.ACCOUNT_FEE);
    }

    private void setDev21EnvironmentData(BinControlBuilder binControlBuilder) {
        binControlBuilder.setBinNumber(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControlBuilder.setCardDescription(Random.genString(10));
        binControlBuilder.setInitialsForDescription(Random.genString(10));
        binControlBuilder.setBINIsActive(true);
        binControlBuilder.setCommercialOnlyBIN(false);
        binControlBuilder.setReplacementBIN("");
        binControlBuilder.setClientTypes(Collections.singletonList(ClientType.DIRECTOR));
        binControlBuilder.setCardDesigns(new HashSet<>());
        binControlBuilder.setIncrementCardNumberBy(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControlBuilder.setCardLifeInMonths(48);
        binControlBuilder.setIssueReplacementCardBySameNumber(true);
        binControlBuilder.setIncludeOverdraftProtectionInAvailableBalance(false);
        binControlBuilder.setATMDailyDollarLimit("400.00");
        binControlBuilder.setATMTransactionLimit("45");
        binControlBuilder.setDBCDailyDollarLimit("750.00");
        binControlBuilder.setDBCTransactionLimit("45");
        binControlBuilder.setReplacementCardFee("6.30");
        binControlBuilder.setReplacementCardCode(ReplacementCardCode.ATM_CARD_REPLACEMENT);
    }
}