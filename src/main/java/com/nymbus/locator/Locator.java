package com.nymbus.locator;

import org.openqa.selenium.By;

public class Locator {
    private final LocatorTypes type;
    private final String value;

    public Locator(LocatorTypes type, String locatorPattern) {
        this.type = type;
        this.value = locatorPattern;
    }

    public By getLocator(Object... args) {
        String locator = String.format(value, args);
        switch (type) {
            case XPATH:
                return By.xpath(locator);
            case CSS:
                return By.cssSelector(locator);
            case ID:
                return By.id(locator);
            case NAME:
                return By.name(locator);
            case CLASSNAME:
                return By.className(locator);
            case TAG:
                return By.tagName(locator);
            case LINK_TEXT:
                return By.linkText(locator);
            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(locator);
        }
        throw new IllegalLocatorException(String.format("Locator type \"%s\" is unknown!", type));
    }

    public LocatorTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }

    public String toString(Object... args) {
        return String.format(value, args);
    }
}
