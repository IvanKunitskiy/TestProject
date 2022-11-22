package com.demo.objects;

import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final double price;
    private final boolean isBestSeller;

    public Book(String title, String author, double price, boolean isBestSeller) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBestSeller = isBestSeller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Double.compare(book.price, price) == 0
                && isBestSeller == book.isBestSeller
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, price, isBestSeller);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", isBestSeller=" + isBestSeller +
                '}';
    }
}
