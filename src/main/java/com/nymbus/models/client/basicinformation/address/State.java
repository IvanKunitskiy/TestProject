package com.nymbus.models.client.basicinformation.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    ALABAMA("Alabama"),
    ALASKA("Alaska"),
    ARIZONA("Arizona");

    private final String state;
}
