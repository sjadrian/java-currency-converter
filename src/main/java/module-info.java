module com.example.currencyconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.currencyconverter to javafx.fxml;
    exports com.example.currencyconverter;

    exports com.example.currencyconverter.model;
}