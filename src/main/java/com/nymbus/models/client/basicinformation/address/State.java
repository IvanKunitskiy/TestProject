package com.nymbus.models.client.basicinformation.address;

public enum State {
    ALABAMA("Alabama"),
    ALASKA("Alaska"),
    ARIZONA("Arizona");

    private final String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
