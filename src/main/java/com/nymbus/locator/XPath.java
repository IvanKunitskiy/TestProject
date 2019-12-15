package com.nymbus.locator;

public class XPath extends Locator implements IXpath {

    public XPath(String value) {
        super(LocatorTypes.XPATH, value);
    }

    @Override
    public String getXpath(Object... args) {
        return toString(args);
    }
}