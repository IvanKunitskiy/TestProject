package com.demo.actions;

import com.demo.pages.Pages;

public class MainPageActions  {
    public void findProductsBySearchField(String text) {
        Pages.mainPage().selectBooksOption();
        Pages.mainPage().typeIntoSearchField(text);
    }
}
