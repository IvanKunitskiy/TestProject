package com.nymbus.model.client.basicinformation.address;

import com.nymbus.model.RequiredField;

import java.util.Objects;

public class Address {
    @RequiredField private AddressType addressType;
    @RequiredField private String country;
    @RequiredField private String address;
    private String addressLine2;
    @RequiredField private String city;
    @RequiredField private String state;
    @RequiredField private String zipCode;
    private String yearsInThisAddress;

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getYearsInThisAddress() {
        return yearsInThisAddress;
    }

    public void setYearsInThisAddress(String yearsInThisAddress) {
        this.yearsInThisAddress = yearsInThisAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return addressType == address1.addressType &&
                Objects.equals(country, address1.country) &&
                Objects.equals(address, address1.address) &&
                Objects.equals(addressLine2, address1.addressLine2) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(state, address1.state) &&
                Objects.equals(zipCode, address1.zipCode) &&
                Objects.equals(yearsInThisAddress, address1.yearsInThisAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressType, country, address, addressLine2, city, state, zipCode, yearsInThisAddress);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressType=" + addressType +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", yearsInThisAddress='" + yearsInThisAddress + '\'' +
                '}';
    }
}
