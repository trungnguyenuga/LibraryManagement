module com.aehanoidz123.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.annotation;
    requires static lombok;
    requires org.mongodb.bson;
    requires org.json;
    requires com.google.protobuf;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires com.google.gson;
    requires java.sql;
    requires webcam.capture;
    requires javafx.swing;
    requires jcommander;
    requires java.mail;
    requires activation;

    opens com.aehanoidz123.librarymanagement to javafx.fxml;
    exports com.aehanoidz123.librarymanagement;
    exports com.aehanoidz123.librarymanagement.Controllers;
    opens com.aehanoidz123.librarymanagement.Controllers to javafx.fxml;
    exports com.aehanoidz123.librarymanagement.Services;
    opens com.aehanoidz123.librarymanagement.Services to javafx.fxml;
    exports com.aehanoidz123.librarymanagement.Entities;
    opens com.aehanoidz123.librarymanagement.Entities to javafx.fxml;
}