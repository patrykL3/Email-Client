module EmailClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;
    requires java.desktop;

    opens pl.patryklubik;
    opens pl.patryklubik.view;
    opens pl.patryklubik.controller;
    opens pl.patryklubik.model;
}