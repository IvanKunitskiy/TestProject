package com.nymbus.models.client.other.document;

import com.nymbus.models.client.basicinformation.address.Country;
import com.nymbus.models.client.basicinformation.address.State;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CompanyID extends Document {
    private String idNumber;
    private State issuedBy;
    private Country country;
    private String issueDate;
    private String expirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompanyID companyID = (CompanyID) o;
        return Objects.equals(idNumber, companyID.idNumber) &&
                Objects.equals(issuedBy, companyID.issuedBy) &&
                Objects.equals(country, companyID.country) &&
                Objects.equals(issueDate, companyID.issueDate) &&
                Objects.equals(expirationDate, companyID.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idNumber, issuedBy, country, issueDate, expirationDate);
    }

    @Override
    public String toString() {
        return "CompanyID{" +
                "idNumber='" + idNumber + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", country='" + country + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                "} " + super.toString();
    }
}
