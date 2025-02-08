package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Database.Database;
import com.aehanoidz123.librarymanagement.Entities.BookSuggestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SuggestViewController {
    @FXML
    private Label studentName;
    @FXML
    private TableView<BookSuggestion> suggestTable;
    @FXML
    private TableColumn<BookSuggestion, String> categoryColumn;
    @FXML
    private TableColumn<BookSuggestion, String> bookColumn;

    private String studentname;
    private String studentID;

    public void setStudentname(String studentname) {
        this.studentname = studentname;
        studentName.setText(studentname);
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
        loadSuggestions(this.studentID);
    }

    private void loadSuggestions(String studentID) {
        ObservableList<BookSuggestion> suggestions = FXCollections.observableArrayList();

        // Connect to database
        try (Connection connection = Database.connect()) {
            String query = """
                SELECT b.category, b.title 
                FROM Book b
                JOIN HistoryIssueBook h ON b.category = h.Category
                WHERE h.UserID = ?;
            """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("Category");
                String title = resultSet.getString("Title");
                suggestions.add(new BookSuggestion(category, title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set data to TableView
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("book"));
        suggestTable.setItems(suggestions);
    }

    @FXML
    public void initialize() {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("book"));
    }

}
