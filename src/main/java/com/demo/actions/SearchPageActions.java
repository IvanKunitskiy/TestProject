package com.demo.actions;

import com.demo.objects.Book;
import com.demo.pages.Pages;

import java.util.List;

public class SearchPageActions {
    public Book getFirstBook() {
        return Pages.searchPage().getFirstBookInList();
    }

    public List<Book> getListOfBooks() {
        return Pages.searchPage().getListOfBooks();
    }

    public void getToMainPage() {
        Pages.searchPage().goToMainPage();
    }
}
