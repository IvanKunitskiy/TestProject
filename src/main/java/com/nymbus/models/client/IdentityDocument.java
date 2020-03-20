package com.nymbus.models.client;

import com.nymbus.core.utils.Generator;

import java.util.Objects;

public class IdentityDocument {
    private String type;
    private String number;
    private String issuedBy;
    private String country;
    private String issueDate;
    private String expirationDate;

    public IdentityDocument setDefaultIdentityDocumentData() {
        IdentityDocument identityDocument = new IdentityDocument();

        identityDocument.setType("Passport");
        identityDocument.setNumber("C" + Generator.genInt(11111111, 99999999));
        identityDocument.setCountry("United States");
        identityDocument.setExpirationDate("11/11/2030");

        return identityDocument;
    }

    public IdentityDocument setDefaultStateDriversLicenseData() {
        IdentityDocument identityDocument = new IdentityDocument();

        identityDocument.setType("State Drivers License");
        identityDocument.setNumber("C" + Generator.genInt(11111111, 99999999));
        identityDocument.setCountry("United States");
        identityDocument.setExpirationDate("11/11/2030");

        return identityDocument;
    }

    @Override
    public String toString() {
        return "IdentityDocument{" +
                "basicinformation='" + type + '\'' +
                ", number='" + number + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", country='" + country + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentityDocument that = (IdentityDocument) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(number, that.number) &&
                Objects.equals(issuedBy, that.issuedBy) &&
                Objects.equals(country, that.country) &&
                Objects.equals(issueDate, that.issueDate) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, issuedBy, country, issueDate, expirationDate);
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
