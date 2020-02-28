package com.nymbus.models.generation.settings;

import com.nymbus.models.client.basicinformation.type.ClientType;
import com.nymbus.models.settings.bincontrol.BinControl;
import com.nymbus.models.settings.bincontrol.ReplacementCardCode;
import com.nymbus.util.Random;

import java.util.Collections;

public class BinControlFactory {
    public BinControl getBinControl() {
        BinControl binControl = new BinControl();
        binControl.setBinNumber(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControl.setCardDescription(Random.genString(10));
        binControl.setInitialsForDescription(Random.genString(10));
        binControl.setBINIsActive(false);
        binControl.setCommercialOnlyBIN(false);
        binControl.setReplacementBIN("");
        binControl.setClientTypes(Collections.singletonList(ClientType.DIRECTOR));
        binControl.setCardDesigns(Collections.EMPTY_SET);
        binControl.setIncrementCardNumberBy(String.valueOf(Random.genInt(1_000_000, 9_999_999)));
        binControl.setCardLifeInMonths(Random.genInt(1, 12));
        binControl.setIssueReplacementCardBySameNumber(true);
        binControl.setIncludeOverdraftProtectionInAvailableBalance(false);
        binControl.setATMDailyDollarLimit(1_500.00);
        binControl.setATMTransactionLimit(15);
        binControl.setDBCDailyDollarLimit(1_500.00);
        binControl.setDBCTransactionLimit(15);
        binControl.setReplacementCardFee(150.00);
        binControl.setReplacementCardCode(ReplacementCardCode.ACCOUNT_FEE);

        return binControl;
    }
}
