package com.aehanoidz123.librarymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryManagementApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(LibraryManagementApplication.class.getResource("login-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Font.loadFont(LibraryManagementApplication.class.getResource("fonts/Roboto-Regular.ttf").toExternalForm(), 14);
        loginScene.getStylesheets().add(LibraryManagementApplication.class.getResource("css/loginStyle.css").toExternalForm());
        stage.setTitle("Library Management");
        Image image = new Image(String.valueOf(LibraryManagementApplication.class.getResource("icons/book_icon.png")));
        stage.getIcons().add(image);
        stage.setScene(loginScene);
        stage.show();
    }
}