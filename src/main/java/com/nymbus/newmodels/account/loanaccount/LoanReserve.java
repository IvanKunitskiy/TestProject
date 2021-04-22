package com.nymbus.newmodels.account.loanaccount;

import lombok.Data;

import java.util.Objects;

@Data
public class LoanReserve {
    private String reservePremiumAmortizationCode;
    private String reservePremiumAmortizationType;
    private String reservePremiumType;
    private String balanceDefinition;
    private String defaultGlOlffset;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanReserve)) return false;
        LoanReserve that = (LoanReserve) o;
        return getReservePremiumAmortizationCode().equals(that.getReservePremiumAmortizationCode()) &&
                Objects.equals(getReservePremiumAmortizationType(), that.getReservePremiumAmortizationType()) &&
                Objects.equals(getReservePremiumType(), that.getReservePremiumType()) &&
                getBalanceDefinition().equals(that.getBalanceDefinition()) &&
                Objects.equals(getDefaultGlOlffset(), that.getDefaultGlOlffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReservePremiumAmortizationCode(), getReservePremiumAmortizationType(), getReservePremiumType(), getBalanceDefinition(), getDefaultGlOlffset());
    }

    @Override
    public String toString() {
        return "LoanReserve{" +
                "reservePremiumAmortizationCode='" + reservePremiumAmortizationCode + '\'' +
                ", reservePremiumAmortizationType='" + reservePremiumAmortizationType + '\'' +
                ", reservePremiumType='" + reservePremiumType + '\'' +
                ", balanceDefinition='" + balanceDefinition + '\'' +
                ", defaultGlOlffset='" + defaultGlOlffset + '\'' +
                '}';
    }
}
