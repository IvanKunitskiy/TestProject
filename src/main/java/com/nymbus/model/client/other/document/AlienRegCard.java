package com.nymbus.model.client.other.document;

import com.nymbus.model.RequiredField;

import java.util.Objects;

public class AlienRegCard extends Document {
    @RequiredField private String idNumber;
    private String issuedBy;
    @RequiredField private String country;
    private String issueDate;
    @RequiredField private String expirationDate;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

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
                '}';
    }
}
