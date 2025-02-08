package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Entities.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemController {
    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    public void setData(Book book) {
        imageView.setImage(new Image(book.getSrc()));
        nameLabel.setText(book.getTitle());
    }

    public void setItem(Book book) {
        String imagePath = book.getSrc();
        Image image;
        if (Objects.equals(imagePath, "images/null.null")) {
            image = new Image(book.getGgimagesrc());
        } else {
            image = new Image(new File(imagePath).toURI().toString());
        }
        imageView.setImage(image);
        nameLabel.setText(book.getTitle());
    }
}
