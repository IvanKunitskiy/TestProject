package com.nymbus.models.client;

import com.nymbus.util.Random;

import java.util.Objects;

public class IdentityDocument {
    private String type;
    private String number;
    private String issuedBy;
    private String country;
    private String expirationDate;

    public IdentityDocument setDefaultIdentityDocumentData(){
        IdentityDocument identityDocument = new IdentityDocument();

        identityDocument.setType("Passport");
        identityDocument.setNumber("C" + Random.genInt(11111111, 99999999));
        identityDocument.setCountry("United States");
        identityDocument.setExpirationDate("01/01/2030");

        return identityDocument;
    }

    @Override
    public String toString() {
        return "IdentityDocument{" +
                "type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", country='" + country + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentityDocument that = (IdentityDocument) o;
        return type.equals(that.type) &&
                number.equals(that.number) &&
                Objects.equals(issuedBy, that.issuedBy) &&
                country.equals(that.country) &&
                expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, issuedBy, country, expirationDate);
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
