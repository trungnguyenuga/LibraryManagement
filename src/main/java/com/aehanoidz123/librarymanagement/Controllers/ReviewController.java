package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewController {
    private String isbn;
    private PreparedStatement statement;
    private Connection connection;
    private String Category;
    private String UserID;
    @FXML
    private MenuButton ratingbutton;
    @FXML
    private MenuItem terribleItm, dislikeItm, normalItm, likeItm, loveItm;
    @FXML
    private TextField commentTXT;
    @FXML
    private Button submitBtn;

    @FXML
    public void initialize() {
        terribleItm.setOnAction(this::handleRateAction);
        dislikeItm.setOnAction(this::handleRateAction);
        normalItm.setOnAction(this::handleRateAction);
        likeItm.setOnAction(this::handleRateAction);
        loveItm.setOnAction(this::handleRateAction);
        submitBtn.setOnAction(this::getFeedBack);
    }

    public void handleRateAction(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        ratingbutton.setText(selectedItem.getText());
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void getFeedBack(ActionEvent event) {
        String rating = ratingbutton.getText();
        String comment = commentTXT.getText();
        int ratingValue = 0;
        switch (rating) {
            case "Terrible":
                ratingValue = 1;
                break;
            case "Dislike":
                ratingValue = 2;
                break;
            case "Normal":
                ratingValue = 3;
                break;
            case "Like It":
                ratingValue = 4;
                break;
            case "Love It":
                ratingValue = 5;
                break;
        }
        try {
            connection = Database.connect();
            String sql = "INSERT INTO BookFB (feedback_text, rating, book_isbn) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, comment);
            statement.setInt(2, ratingValue);
            statement.setString(3, isbn);
            statement.executeUpdate();

            String sql2 = "INSERT INTO HistoryIssueBook (UserID, Category) VALUES (?, ?)";
            PreparedStatement statement1 = connection.prepareStatement(sql2);
            statement1.setString(1, this.UserID);
            statement1.setString(2, this.Category);
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.close();
    }
}
