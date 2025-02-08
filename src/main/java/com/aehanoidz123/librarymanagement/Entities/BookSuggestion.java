package com.aehanoidz123.librarymanagement.Entities;

public class BookSuggestion {
    private final String category;
    private final String book;

    public BookSuggestion(String category, String book) {
        this.category = category;
        this.book = book;
    }

    public String getCategory() {
        return category;
    }

    public String getBook() {
        return book;
    }
}

