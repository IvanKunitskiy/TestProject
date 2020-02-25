package com.nymbus.model.settings;

import com.nymbus.model.RequiredField;
import com.nymbus.model.client.basicinformation.ClientType;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getInitialsForDescription() {
        return initialsForDescription;
    }

    public void setInitialsForDescription(String initialsForDescription) {
        this.initialsForDescription = initialsForDescription;
    }

    public boolean isBINActive() {
        return isBINActive;
    }

    public void setBINActive(boolean BINActive) {
        isBINActive = BINActive;
    }

    public boolean isCommercialOnlyBIN() {
        return isCommercialOnlyBIN;
    }

    public void setCommercialOnlyBIN(boolean commercialOnlyBIN) {
        isCommercialOnlyBIN = commercialOnlyBIN;
    }

    public List<ClientType> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<ClientType> clientTypes) {
        this.clientTypes = clientTypes;
    }

    public Set<String> getCardDesigns() {
        return cardDesigns;
    }

    public void setCardDesigns(Set<String> cardDesigns) {
        this.cardDesigns = cardDesigns;
    }

    public int getIncrementCardNumberBy() {
        return incrementCardNumberBy;
    }

    public void setIncrementCardNumberBy(int incrementCardNumberBy) {
        this.incrementCardNumberBy = incrementCardNumberBy;
    }

    public int getCardLifeInMonths() {
        return cardLifeInMonths;
    }

    public void setCardLifeInMonths(int cardLifeInMonths) {
        this.cardLifeInMonths = cardLifeInMonths;
    }

    public boolean isIssueReplacementCardBySameNumber() {
        return issueReplacementCardBySameNumber;
    }

    public void setIssueReplacementCardBySameNumber(boolean issueReplacementCardBySameNumber) {
        this.issueReplacementCardBySameNumber = issueReplacementCardBySameNumber;
    }

    public boolean isIncludeOverdraftProtectionInAvailableBalance() {
        return includeOverdraftProtectionInAvailableBalance;
    }

    public void setIncludeOverdraftProtectionInAvailableBalance(boolean includeOverdraftProtectionInAvailableBalance) {
        this.includeOverdraftProtectionInAvailableBalance = includeOverdraftProtectionInAvailableBalance;
    }

    public double getATMDailyDollarLimit() {
        return ATMDailyDollarLimit;
    }

    public void setATMDailyDollarLimit(double ATMDailyDollarLimit) {
        this.ATMDailyDollarLimit = ATMDailyDollarLimit;
    }

    public int getATMTransactionLimit() {
        return ATMTransactionLimit;
    }

    public void setATMTransactionLimit(int ATMTransactionLimit) {
        this.ATMTransactionLimit = ATMTransactionLimit;
    }

    public double getDBCDailyDollarLimit() {
        return DBCDailyDollarLimit;
    }

    public void setDBCDailyDollarLimit(double DBCDailyDollarLimit) {
        this.DBCDailyDollarLimit = DBCDailyDollarLimit;
    }

    public int getDBCTransactionLimit() {
        return DBCTransactionLimit;
    }

    public void setDBCTransactionLimit(int DBCTransactionLimit) {
        this.DBCTransactionLimit = DBCTransactionLimit;
    }

    public double getReplacementCardFee() {
        return replacementCardFee;
    }

    public void setReplacementCardFee(double replacementCardFee) {
        this.replacementCardFee = replacementCardFee;
    }

    public String getReplacementCardCode() {
        return replacementCardCode;
    }

    public void setReplacementCardCode(String replacementCardCode) {
        this.replacementCardCode = replacementCardCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinControl that = (BinControl) o;
        return isBINActive == that.isBINActive &&
                isCommercialOnlyBIN == that.isCommercialOnlyBIN &&
                incrementCardNumberBy == that.incrementCardNumberBy &&
                cardLifeInMonths == that.cardLifeInMonths &&
                issueReplacementCardBySameNumber == that.issueReplacementCardBySameNumber &&
                includeOverdraftProtectionInAvailableBalance == that.includeOverdraftProtectionInAvailableBalance &&
                ATMDailyDollarLimit == that.ATMDailyDollarLimit &&
                ATMTransactionLimit == that.ATMTransactionLimit &&
                DBCDailyDollarLimit == that.DBCDailyDollarLimit &&
                DBCTransactionLimit == that.DBCTransactionLimit &&
                replacementCardFee == that.replacementCardFee &&
                Objects.equals(binNumber, that.binNumber) &&
                Objects.equals(cardDescription, that.cardDescription) &&
                Objects.equals(initialsForDescription, that.initialsForDescription) &&
                Objects.equals(clientTypes, that.clientTypes) &&
                Objects.equals(cardDesigns, that.cardDesigns) &&
                Objects.equals(replacementCardCode, that.replacementCardCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(binNumber, cardDescription, initialsForDescription, isBINActive, isCommercialOnlyBIN, clientTypes, cardDesigns, incrementCardNumberBy, cardLifeInMonths, issueReplacementCardBySameNumber, includeOverdraftProtectionInAvailableBalance, ATMDailyDollarLimit, ATMTransactionLimit, DBCDailyDollarLimit, DBCTransactionLimit, replacementCardFee, replacementCardCode);
    }

    @Override
    public String toString() {
        return "BinControl{" +
                "binNumber='" + binNumber + '\'' +
                ", cardDescription='" + cardDescription + '\'' +
                ", initialsForDescription='" + initialsForDescription + '\'' +
                ", isBINActive=" + isBINActive +
                ", isCommercialOnlyBIN=" + isCommercialOnlyBIN +
                ", clientTypes=" + clientTypes +
                ", cardDesigns=" + cardDesigns +
                ", incrementCardNumberBy=" + incrementCardNumberBy +
                ", cardLifeInMonths=" + cardLifeInMonths +
                ", issueReplacementCardBySameNumber=" + issueReplacementCardBySameNumber +
                ", includeOverdraftProtectionInAvailableBalance=" + includeOverdraftProtectionInAvailableBalance +
                ", ATMDailyDollarLimit=" + ATMDailyDollarLimit +
                ", ATMTransactionLimit=" + ATMTransactionLimit +
                ", DBCDailyDollarLimit=" + DBCDailyDollarLimit +
                ", DBCTransactionLimit=" + DBCTransactionLimit +
                ", replacementCardFee=" + replacementCardFee +
                ", replacementCardCode='" + replacementCardCode + '\'' +
                '}';
    }
}
