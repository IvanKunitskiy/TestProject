package com.nymbus.models.client;

import com.nymbus.util.Random;

import java.util.Objects;

public class Address {

    private String type;
    private String country;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Address setDefaultPhysicalData(){
        Address address = new Address();

        address.setType("Physical");
        address.setCountry("United States");
        address.setAddress(Random.genAddress());
        address.setCity("New York");
        address.setState("New York");
        address.setZipCode("1" + Random.genInt(1000, 9999));

        return address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(type, address1.type) &&
                Objects.equals(country, address1.country) &&
                Objects.equals(address, address1.address) &&
                Objects.equals(city, address1.city) &&
                Objects.equals(state, address1.state) &&
                Objects.equals(zipCode, address1.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, country, address, city, state, zipCode);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
