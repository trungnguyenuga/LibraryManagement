package com.aehanoidz123.librarymanagement.Entities;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FeedBack {
    private StringProperty name;
    private StringProperty comment;
    private IntegerProperty rate;

    public FeedBack(String comment, int rate) {
        this.name = new SimpleStringProperty("Anonymous User");
        this.comment = new SimpleStringProperty(comment);
        this.rate = new SimpleIntegerProperty(rate);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public IntegerProperty rateProperty() {
        return rate;
    }

    public String getName() {
        return name.get();
    }

    public String getComment() {
        return comment.get();
    }

    public int getRate() {
        return rate.get();
    }
}
