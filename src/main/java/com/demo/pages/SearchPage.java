package com.demo.pages;

import com.codeborne.selenide.SelenideElement;
import com.demo.core.base.PageTools;
import com.demo.objects.Book;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends PageTools {
    private final By amazonLogo = By.xpath("//a[@id='nav-logo-sprites']");
    private final By bookElements = By.xpath("//div[contains(@class, 's-search-results')]/div[@data-component-type='s-search-result']//h2/following-sibling::*");
    private final String title = "//div[contains(@class, 's-search-results')]/div[@data-component-type='s-search-result'][%d]//h2//span";
    private final String author = "//div[contains(@class, 's-search-results')]/div[@data-component-type='s-search-result'][%d]//span[text()='by ']/following-sibling::*[1]";
    private final String price = "(//div[contains(@class, 's-search-results')]/div[@data-component-type='s-search-result'][%d]//div[contains(@class, 'price') or contains(@class, \"a-spacing-mini\")]/div/a/span[@class='a-price']/span[1])[1] ";
    private final String bestSellerLabel = "//div[contains(@class, 's-search-results')]/div[@data-component-type='s-search-result'][%d]//span[contains(@aria-labelledby,'best-seller-label')]";

    private String getTitleOrAuthor(String element, int i) {
        return getElementText(By.xpath(String.format(element, i)));
    }

    private double getPrice(String element, int i) {
        List<SelenideElement> bookPrice = getElementsWithZeroOption(By.xpath(String.format(element, i)));
        double price = 0;
        if (bookPrice.size() == 1) {
            for (WebElement el : bookPrice) {
                String elementString = el.getText();
                if (elementString.length() > 2){
                    price = Double.parseDouble(elementString.replace("$", ""));
                }
            }
        } else {
            price = 0;
        }
        return price;
    }

    private boolean getBestSellerValue(String element, int i) {
        List<SelenideElement> bestSeller = getElementsWithZeroOption(By.xpath(String.format(element, i)));
        boolean isBestSeller = false;
        if (bestSeller.size() == 1) {
            isBestSeller = true;
        }
        return isBestSeller;
    }

    @Step("Get first book")
    public Book getFirstBookInList() {
        String bookTitle = getTitleOrAuthor(title, 1);
        String bookAuthor = getTitleOrAuthor(author, 1);
        double bookPrice = getPrice(price, 1);
        boolean isBestSeller = getBestSellerValue(bestSellerLabel, 1);
        return new Book(bookTitle, bookAuthor, bookPrice, isBestSeller);
    }

    @Step("Get list of books")
    public List<Book> getListOfBooks() {
        List<SelenideElement> elementsList = getElements(bookElements);
        List<Book> bookList = new ArrayList<>();
        for (int i = 1; i <= elementsList.size(); i++) {
            String bookTitle = getTitleOrAuthor(title, i);
            String bookAuthor = getTitleOrAuthor(author, i);
            double bookPrice = getPrice(price, i);
            boolean isBestSeller = getBestSellerValue(bestSellerLabel, i);

            bookList.add(new Book(bookTitle, bookAuthor, bookPrice, isBestSeller));
        }
        return bookList;
    }

    @Step("Go back")
    public void goToMainPage() {
        click(amazonLogo);
    }
}
