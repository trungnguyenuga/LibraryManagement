package com.aehanoidz123.librarymanagement.Controllers;

import com.aehanoidz123.librarymanagement.Entities.StudentInfo;
import com.aehanoidz123.librarymanagement.Entities.WebcamDetails;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class WebcamController implements Initializable {
    @FXML
    private ComboBox<WebcamDetails> chooseWebcams;

    @FXML
    private ImageView imageHolder;

    @FXML
    private Button startWebcamBtn;

    @FXML
    private Button stopWebcamBtn;

    @FXML
    private Label studentIdLabel;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label studentPhoneLabel;

    @FXML
    private Label studentEmailLabel;

    @FXML
    private AnchorPane studentInfoPane;

    private Webcam webcam = null;
    private BufferedImage image;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private boolean isRunning = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentInfoPane.setVisible(false);
        ObservableList<WebcamDetails> webcams = FXCollections.observableArrayList();
        int cnt = 0;
        for (Webcam x : Webcam.getWebcams()) {
            WebcamDetails webcamDetails = new WebcamDetails();
            webcamDetails.setWebcamName(x.getName());
            webcamDetails.setWebcamIdx(cnt);
            webcams.add(webcamDetails);
            cnt++;
        }
        chooseWebcams.setItems(webcams);
        chooseWebcams.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Webcam index: " + newValue.getWebcamIdx() + ",Webcam name: " + newValue.getWebcamName());
                initWebcam(newValue.getWebcamIdx());
            }
        });
    }

    private void initWebcam(int webcamIdx) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (webcam == null) {
                    webcam = Webcam.getWebcams().get(webcamIdx);
                    webcam.open();
                } else {
                    webcam.close();
                    webcam = Webcam.getWebcams().get(webcamIdx);
                    webcam.open();
                }
                startWebcamStream();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        startWebcamBtn.setDisable(true);
    }

    private void startWebcamStream() {
        isRunning = true;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (isRunning) {
                    if ((image = webcam.getImage()) != null) {
                        Platform.runLater(() -> {
                            final Image mainImage = SwingFXUtils.toFXImage(image, null);
                            imageProperty.set(mainImage);
                            Result result = null;
                            LuminanceSource source = new BufferedImageLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                            try {
                                result = new MultiFormatReader().decode(bitmap);
                            } catch (NotFoundException e) {
                            }
                            if (result != null) {
                                String s = result.getText();
                                ArrayList<String> list = new ArrayList<>();
                                int len = s.length();
                                int i = 0;
                                while (i < len) {
                                    if (s.charAt(i) == ':') {
                                        int j = i + 2;
                                        StringBuilder t = new StringBuilder();
                                        while (s.charAt(j) != 34) {
                                            t.append(s.charAt(j));
                                            j++;
                                        }
                                        list.add(t.toString());
                                        i = j;
                                    } else {
                                        i++;
                                    }
                                }
                                if (list.size() == 4) {
                                    stopWebcam();
                                    studentInfoPane.setVisible(true);
                                    String studentId = list.get(0);
                                    String studentEmail = list.get(1);
                                    String studentName = list.get(2);
                                    String studentPhone = list.get(3);
                                    studentIdLabel.setText(studentId);
                                    studentEmailLabel.setText(studentEmail);
                                    studentNameLabel.setText(studentName);
                                    studentPhoneLabel.setText(studentPhone);
                                }
                            } else {
                                System.out.println("Finding...");
                            }
                        });
                        image.flush();
                    }
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        imageHolder.imageProperty().bind(imageProperty);
    }

    public void startWebcam() {
        isRunning = true;
        startWebcamStream();
        stopWebcamBtn.setDisable(false);
        startWebcamBtn.setDisable(true);
    }
    public void stopWebcam() {
        isRunning = false;
        stopWebcamBtn.setDisable(true);
        startWebcamBtn.setDisable(false);
//        webcam.close();
    }
    public void confirm() {
        studentInfoPane.setVisible(false);
    }
}
