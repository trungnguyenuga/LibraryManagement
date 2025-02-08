package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Database.Database;
import com.aehanoidz123.librarymanagement.Entities.Book;
import com.aehanoidz123.librarymanagement.Entities.FeedBack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowCommentController {
    private String isbn;
    @FXML
    private TableView<FeedBack> feedbackTableView;
    @FXML
    private TableColumn<FeedBack, String> userColumn;
    @FXML
    private TableColumn<FeedBack, String> commentColumn;
    @FXML
    private TableColumn<FeedBack, Integer> rateColumn;

    private ObservableList<FeedBack> feedbackList;

    public ShowCommentController() {
        feedbackList = FXCollections.observableArrayList();
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
        loadComment();
    }

    private void loadComment() {
        String query = "SELECT feedback_text, rating FROM BookFB WHERE book_isbn = ?";

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) {
                System.out.println("Failed to establish connection.");
                return;
            }

            stmt.setString(1,isbn);

            try (ResultSet rs = stmt.executeQuery()) {
                feedbackList.clear();

                while (rs.next()) {
                    String comment = rs.getString("feedback_text");
                    int rating = rs.getInt("rating");

                    feedbackList.add(new FeedBack(comment, rating));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        feedbackTableView.setItems(feedbackList);
    }

    @FXML
    public void initialize() {
        userColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().asObject());
    }



}
