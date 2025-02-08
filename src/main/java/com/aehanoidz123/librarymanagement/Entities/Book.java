package com.aehanoidz123.librarymanagement.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Book {
    private String id;
    private String title;
    private String author;
    private String ISBN;
    private String category;
    private String src;
    private int quantity;
    private int remaining;
    private String availability;
    private String ggimagesrc;
    private double rate;

    public Book(String title, String author, String category, String ISBN, String ggimagesrc) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        this.ggimagesrc = ggimagesrc;
        this.quantity = 100;
        this.remaining = 100;
        this.availability = "Available";
        this.id = "N/A";
        this.rate = 0.0;


        if(this.ISBN == null) {
            this.ISBN = ISBNGenerator.generateISBN(title, author);
        }
    }

}
