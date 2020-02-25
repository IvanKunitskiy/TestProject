package com.nymbus.model.client.other.document;

import java.util.Objects;

public class CompanyID extends Document {
    private String idNumber;
    private String issuedBy;
    private String country;
    private String issueDate;
    private String expirationDate;

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
}
