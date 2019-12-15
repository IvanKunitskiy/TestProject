package com.nymbus.locator;

public class ID extends Locator implements IXpath {
    public ID(String value) {
        super(LocatorTypes.ID, value);
    }

    @Override
    public String getXpath(Object... args) {
        return "//*[@id='" + toString() + "']";
    }
}
