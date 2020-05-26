package com.nymbus.newmodels.generation.bincontrol.builder;

import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.settings.bincontrol.ReplacementCardCode;

import java.util.List;
import java.util.Set;

public class BinControlBuilder implements IBinControlBuilder {

    private BinControl binControl;

    public BinControlBuilder() {
        binControl = new BinControl();
    }

    @Override
    public void setBinNumber(String binNumber) {
        binControl.setBinNumber(binNumber);
    }

    @Override
    public void setCardDescription(String cardDescription) {
        binControl.setCardDescription(cardDescription);
    }

    @Override
    public void setInitialsForDescription(String initialsForDescription) {
        binControl.setInitialsForDescription(initialsForDescription);
    }

    @Override
    public void setBINIsActive(Boolean binIsActive) {
        binControl.setBINIsActive(binIsActive);
    }

    @Override
    public void setCommercialOnlyBIN(Boolean commercialOnlyBIN) {
        binControl.setCommercialOnlyBIN(commercialOnlyBIN);
    }

    @Override
    public void setReplacementBIN(String replacementBIN) {
        binControl.setReplacementBIN(replacementBIN);
    }

    @Override
    public void setClientTypes(List<ClientType> clientTypes) {
        binControl.setClientTypes(clientTypes);
    }

    @Override
    public void setCardDesigns(Set<String> cardDesigns) {
        binControl.setCardDesigns(cardDesigns);
    }

    @Override
    public void setIncrementCardNumberBy(String cardNumberBy) {
        binControl.setIncrementCardNumberBy(cardNumberBy);
    }

    @Override
    public void setCardLifeInMonths(int cardLifeInMonths) {
        binControl.setCardLifeInMonths(cardLifeInMonths);
    }

    @Override
    public void setIssueReplacementCardBySameNumber(Boolean issueReplacementCardBySameNumber) {
        binControl.setIssueReplacementCardBySameNumber(issueReplacementCardBySameNumber);
    }

    @Override
    public void setIncludeOverdraftProtectionInAvailableBalance(Boolean includeOverdraftProtectionInAvailableBalance) {
        binControl.setIncludeOverdraftProtectionInAvailableBalance(includeOverdraftProtectionInAvailableBalance);
    }

    @Override
    public void setATMDailyDollarLimit(String atmDailyDollarLimit) {
        binControl.setATMDailyDollarLimit(atmDailyDollarLimit);
    }

    @Override
    public void setATMTransactionLimit(String atmTransactionLimit) {
        binControl.setATMTransactionLimit(atmTransactionLimit);
    }

    @Override
    public void setDBCDailyDollarLimit(String dbcDailyDollarLimit) {
        binControl.setDBCDailyDollarLimit(dbcDailyDollarLimit);
    }

    @Override
    public void setDBCTransactionLimit(String dbcTransactionLimit) {
        binControl.setDBCTransactionLimit(dbcTransactionLimit);
    }

    @Override
    public void setReplacementCardFee(String replacementCardFee) {
        binControl.setReplacementCardFee(replacementCardFee);
    }

    @Override
    public void setReplacementCardCode(ReplacementCardCode replacementCardCode) {
        binControl.setReplacementCardCode(replacementCardCode);
    }

    public BinControl getBinControl() {
        return binControl;
    }
}
