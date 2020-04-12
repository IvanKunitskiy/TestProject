package com.nymbus.actions;

import com.codeborne.selenide.Selenide;

public class MainActions {

    public void switchToTab(int index) {
        Selenide.switchTo().window(index);
    }
}