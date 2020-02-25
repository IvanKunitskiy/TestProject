package com.nymbus.model.client.details.phone;

import com.nymbus.model.RequiredField;

import java.util.Objects;

public class Phone {
    @RequiredField
    PhoneType phoneType;
    @RequiredField String country;
    @RequiredField String phoneNumber;

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return phoneType == phone.phoneType &&
                Objects.equals(country, phone.country) &&
                Objects.equals(phoneNumber, phone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneType, country, phoneNumber);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneType=" + phoneType +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
