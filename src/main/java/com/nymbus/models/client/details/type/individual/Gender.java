package com.nymbus.models.client.details.type.individual;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    OTHERS("Others"),
    UNKNOWN("Unknown");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
