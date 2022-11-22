package com.demo;

import com.demo.actions.Actions;
import com.demo.core.base.BaseTest;
import com.demo.objects.Book;
import com.demo.testrail.CustomStepResult;
import com.demo.testrail.TestRailAssert;
import com.demo.testrail.TestRailIssue;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;

import java.util.List;

@Epic("First")
@Feature("First")
@Owner("QA")
public class FirstTest extends BaseTest {

    @TestRailIssue(issueID = 22513)
    @Test(description = "First test")
    public void verifyFirst() {
        Actions.mainPageActions().findProductsBySearchField("Head First Java, 2nd Edition");
        Book testBook = Actions.searchPageActions().getFirstBook();
        Actions.searchPageActions().getToMainPage();
        Actions.mainPageActions().findProductsBySearchField("Java");
        List<Book> booksList = Actions.searchPageActions().getListOfBooks();
        boolean isBookListContainsTestBook = false;
        for (Book book : booksList) {
            if (book.equals(testBook)) {
                isBookListContainsTestBook = true;
            }
        }
        TestRailAssert.assertTrue(isBookListContainsTestBook, new CustomStepResult("Book is not in the list!", "Book is in the list!"));
    }
}
