package com.nymbus.models.client.basicinformation.address;

public enum Country {
    UNITED_STATES("United States"),
    UKRAINE("Ukraine"),
    RUSSIA("Russia");

    private final String country;

    Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
