package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Database.Database;
import com.aehanoidz123.librarymanagement.LibraryManagementApplication;
import com.aehanoidz123.librarymanagement.Services.DataService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Button loginButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ImageView loginImageView;

    private Connection connect = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressIndicator.setVisible(false);
        String imagePath = "images/login.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        loginImageView.setImage(image);
    }

    public void enterkey(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            loginButton.fire();
        }
    }

    public void loginAction(ActionEvent event) {
        progressIndicator.setVisible(true);
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                try {
                    connect = Database.connect();
                    String sql = "SELECT * FROM User WHERE username = ? and password = ?";
                    statement = connect.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    return resultSet.next();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (statement != null) {
                            statement.close();
                        }
                        if (connect != null) {
                            connect.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        task.setOnSucceeded(event1 -> {
            progressIndicator.setVisible(false);
            boolean loginSuccessful = task.getValue();
            if (loginSuccessful) {
                DataService.username = usernameInput.getText();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Welcome");
                alert.setHeaderText("Welcome " + username);
                alert.showAndWait();
                try {
                    FXMLLoader loader = new FXMLLoader(LibraryManagementApplication.class.getResource("dashboard-view.fxml"));
                    Scene mainScene = new Scene(loader.load());
                    mainScene.getStylesheets().add(LibraryManagementApplication.class.getResource("css/mainStyle.css").toExternalForm());
                    Stage stage = new Stage();
                    stage.setScene(mainScene);
                    stage.setTitle("Library Management");
                    Image image = new Image(String.valueOf(LibraryManagementApplication.class.getResource("icons/book_icon.png")));
                    stage.getIcons().add(image);
                    stage.show();

                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    currentStage.setOnCloseRequest(event2 -> {
                        Platform.exit();
                        System.exit(0);
                    });
                    currentStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid credentials");
                alert.setHeaderText("Invalid username or password");
                alert.showAndWait();
            }
        });

        new Thread(task).start();
    }
}
