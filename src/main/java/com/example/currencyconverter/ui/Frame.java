package com.example.currencyconverter.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Frame extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Currency Converter");

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dollar-logo.jpg")));
        stage.getIcons().add(image);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}