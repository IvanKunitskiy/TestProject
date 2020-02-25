package com.nymbus.models.card;

import com.nymbus.model.CardStatus;
import com.nymbus.model.RequiredField;
import com.nymbus.model.settings.BinControl;

import java.util.Objects;

public class DebitCard {
    @RequiredField private BinControl binControl;
    @RequiredField private boolean isInstantIssue;

    private String cardNumber;
    private String clientNumber;
    @RequiredField private String nameOfCard;
    private String secondLineEmbossing;
    @RequiredField private String accountN1;
    private String cardDesign;
    @RequiredField CardStatus cardStatus;

    public BinControl getBinControl() {
        return binControl;
    }

    public void setBinControl(BinControl binControl) {
        this.binControl = binControl;
    }

    public boolean isInstantIssue() {
        return isInstantIssue;
    }

    public void setInstantIssue(boolean instantIssue) {
        isInstantIssue = instantIssue;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getNameOfCard() {
        return nameOfCard;
    }

    public void setNameOfCard(String nameOfCard) {
        this.nameOfCard = nameOfCard;
    }

    public String getSecondLineEmbossing() {
        return secondLineEmbossing;
    }

    public void setSecondLineEmbossing(String secondLineEmbossing) {
        this.secondLineEmbossing = secondLineEmbossing;
    }

    public String getAccountN1() {
        return accountN1;
    }

    public void setAccountN1(String accountN1) {
        this.accountN1 = accountN1;
    }

    public String getCardDesign() {
        return cardDesign;
    }

    public void setCardDesign(String cardDesign) {
        this.cardDesign = cardDesign;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitCard debitCard = (DebitCard) o;
        return isInstantIssue == debitCard.isInstantIssue &&
                Objects.equals(binControl, debitCard.binControl) &&
                Objects.equals(cardNumber, debitCard.cardNumber) &&
                Objects.equals(clientNumber, debitCard.clientNumber) &&
                Objects.equals(nameOfCard, debitCard.nameOfCard) &&
                Objects.equals(secondLineEmbossing, debitCard.secondLineEmbossing) &&
                Objects.equals(accountN1, debitCard.accountN1) &&
                Objects.equals(cardDesign, debitCard.cardDesign) &&
                cardStatus == debitCard.cardStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(binControl, isInstantIssue, cardNumber, clientNumber, nameOfCard, secondLineEmbossing, accountN1, cardDesign, cardStatus);
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "binControl=" + binControl +
                ", isInstantIssue=" + isInstantIssue +
                ", cardNumber='" + cardNumber + '\'' +
                ", clientNumber='" + clientNumber + '\'' +
                ", nameOfCard='" + nameOfCard + '\'' +
                ", secondLineEmbossing='" + secondLineEmbossing + '\'' +
                ", accountN1='" + accountN1 + '\'' +
                ", cardDesign='" + cardDesign + '\'' +
                ", cardStatus=" + cardStatus +
                '}';
    }
}
