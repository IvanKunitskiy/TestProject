package com.nymbus.models.client.clientdetails.type.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    OTHERS("Others"),
    UNKNOWN("Unknown");

    private final String gender;
}
