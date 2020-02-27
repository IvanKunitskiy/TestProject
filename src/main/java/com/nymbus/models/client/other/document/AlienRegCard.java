package com.nymbus.models.client.other.document;

import com.nymbus.models.RequiredField;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AlienRegCard extends Document {
    @RequiredField private String idNumber;
    private String issuedBy;
    @RequiredField private String country;
    private String issueDate;
    @RequiredField private String expirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AlienRegCard that = (AlienRegCard) o;
        return Objects.equals(idNumber, that.idNumber) &&
                Objects.equals(issuedBy, that.issuedBy) &&
                Objects.equals(country, that.country) &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNumber, issuedBy, country, issueDate, expirationDate);
    }

    @Override
    public String toString() {
        return "AlienRegCard{" +
                "idNumber='" + idNumber + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", country='" + country + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                "} " + super.toString();
    }
}
