package com.example.currencyconverter;

import com.example.currencyconverter.model.CurrencyConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.util.*;
import java.net.URL;

public class Controller implements Initializable, EventHandler{

    @FXML
    private Label topCurrencyLabel;
    @FXML
    private Label topCurrencyValueLabel;
    @FXML
    private Label botCurrencyLabel;
    @FXML
    private Label botCurrencyValueLabel;
    @FXML
    private ComboBox<String> topCurrencyComboBox;
    @FXML
    private ComboBox<String> botCurrencyComboBox;
    @FXML
    private Label currencyConversionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Button buttonClear;
    @FXML
    private Button buttonX;
    @FXML
    private Button buttonDot;

    private final CurrencyConverter currencyConverter = new CurrencyConverter();
    private LastInteraction lastInteraction = LastInteraction.TOP;
    private Event myEvent;
    private Map<String, String[]> hashMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // initialize combo boxes
        final ArrayList<String> currencyList = new ArrayList<>();
        hashMap = new HashMap<>();

        hashMap.put("United States - Dollar", new String[]{"USD", "$"});
        hashMap.put("Europe - Euro", new String[]{"EUR", " €"});
        hashMap.put("United Kingdom - Pound", new String[]{"GBP", "£"});
        hashMap.put("Japan - Yen", new String[]{"JPY", " ¥"});
        hashMap.put("Indian - Rupee", new String[]{"INR", "₹"});

        currencyList.addAll(hashMap.keySet());

        ObservableList observableList = FXCollections.observableList(currencyList);

        topCurrencyComboBox.setItems(observableList);
        botCurrencyComboBox.setItems(observableList);

        // set default value
        topCurrencyComboBox.getSelectionModel().selectFirst();
        botCurrencyComboBox.getSelectionModel().selectLast();

        updateCurrencySymbol(topCurrencyLabel, topCurrencyComboBox);
        updateCurrencySymbol(botCurrencyLabel, botCurrencyComboBox);

        // set default value for top currency
        topCurrencyValueLabel.setText("1");

        // get currency convert
        updateCurrency(topCurrencyValueLabel, botCurrencyValueLabel, topCurrencyComboBox, botCurrencyComboBox);

        updateBoldLabel(topCurrencyValueLabel, botCurrencyValueLabel);
        updateCurrencyConversion(topCurrencyComboBox, botCurrencyComboBox);
        dateLabel.setText("Updated: " + currencyConverter.getData().getDate());
    }

    @FXML
    @Override
    public void handle(Event event) {
        final Object source = event.getSource();
        System.out.println(source);

        this.myEvent = event;

        updateLastInteraction();
        updateCurrencyValue();
        updateComboBox();
    }

    private void xButton(Label initialLabel) {
        initialLabel.setText(initialLabel.getText().substring(0, initialLabel.getText().length() - 1));

        if (initialLabel.getText().isEmpty()) {
            initialLabel.setText("0");
        }

        // check if the next value to removed is a dot
        if (initialLabel.getText().charAt(initialLabel.getText().length() - 1) == '.') {
            initialLabel.setText(initialLabel.getText().substring(0, initialLabel.getText().length() - 1));
        }
    }

    private void clearButton() {
        topCurrencyValueLabel.setText("0");
        botCurrencyValueLabel.setText("0");
    }

    private void dotButton(Label initialValueLabel) {
        if (!initialValueLabel.getText().contains(".")) {
            initialValueLabel.setText(initialValueLabel.getText() + ".");
        }
    }

    private void updateComboBox() {
        final Object source = myEvent.getSource();

        if (source.equals(topCurrencyComboBox)) {
            // update the bottom
            updateCurrency(topCurrencyValueLabel, botCurrencyValueLabel, topCurrencyComboBox, botCurrencyComboBox);
            updateCurrencySymbol(topCurrencyLabel, topCurrencyComboBox);
        }

        if (source.equals(botCurrencyComboBox)) {
            // update the top
            updateCurrency(botCurrencyValueLabel, topCurrencyValueLabel, botCurrencyComboBox, topCurrencyComboBox);
            updateCurrencySymbol(botCurrencyLabel, botCurrencyComboBox);
        }
    }

    private void updateLastInteraction() {
        final Object source = myEvent.getSource();

        // check the last source
        if(source.equals(topCurrencyValueLabel)) {
            lastInteraction = LastInteraction.TOP;
            updateBoldLabel(topCurrencyValueLabel, botCurrencyValueLabel);
            updateCurrencyConversion(topCurrencyComboBox, botCurrencyComboBox);
        }
        if(source.equals(botCurrencyValueLabel)) {
            lastInteraction = LastInteraction.BOT;
            updateBoldLabel(botCurrencyValueLabel, topCurrencyValueLabel);
            updateCurrencyConversion(botCurrencyComboBox, topCurrencyComboBox);
        }
    }

    private void updateCurrencyValue() {
        if(lastInteraction == LastInteraction.TOP) {
            // update the bottom
            updateCurrencyValueLabel(topCurrencyValueLabel, botCurrencyValueLabel, topCurrencyComboBox, botCurrencyComboBox);
        }
        if(lastInteraction == LastInteraction.BOT) {
            // update the top
            updateCurrencyValueLabel(botCurrencyValueLabel, topCurrencyValueLabel, botCurrencyComboBox, topCurrencyComboBox);
        }
    }

    private String getButtonText() {
        final Object source = myEvent.getSource();

        if (source.getClass() == Button.class) {
            Button button = (Button) source;
            return button.getText();
        }
        return "";
    }

    private void updateCurrency(Label initialValueLabel, Label targetValueLabel, ComboBox initialCurrencyComboBox, ComboBox targetCurrencyComboBox) {
        String conversion = currencyConverter.convert(
                Double.valueOf(initialValueLabel.getText()),
                hashMap.get(initialCurrencyComboBox.getValue())[0],
                hashMap.get(targetCurrencyComboBox.getValue())[0]
        );
        targetValueLabel.setText(conversion);

        updateCurrencyConversion(initialCurrencyComboBox, targetCurrencyComboBox);
    }

    private void updateBoldLabel(Label makeThisBold, Label makeThisNormal) {
        makeThisBold.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        makeThisNormal.setFont(Font.font("Verdana", FontWeight.NORMAL, 30));
    }

    private void updateCurrencyConversion(ComboBox initialComboBox, ComboBox targetComboBox) {
        currencyConversionLabel.setText(currencyConverter.showConversion(hashMap.get(initialComboBox.getValue())[0], hashMap.get(targetComboBox.getValue())[0]));
    }

    private void updateCurrencyValueLabel(Label initialValueLabel, Label targetValueLabel, ComboBox initialCurrencyComboBox, ComboBox targetCurrencyComboBox) {
        final Object source = myEvent.getSource();

        if(source.equals(initialValueLabel)) {
            return;
        }

        if (source.equals(buttonClear)) {
            clearButton();
            return;
        }

        if (source.equals(buttonDot)) {
            dotButton(initialValueLabel);
            return;
        }

        if (source.equals(buttonX)) {
            xButton(initialValueLabel);
            updateCurrency(initialValueLabel, targetValueLabel, initialCurrencyComboBox, targetCurrencyComboBox);
            return;
        }

        if (initialValueLabel.getText().equals("0")) {
            initialValueLabel.setText(getButtonText());
        } else {
            initialValueLabel.setText(initialValueLabel.getText() + getButtonText());
        }
        updateCurrency(initialValueLabel, targetValueLabel, initialCurrencyComboBox, targetCurrencyComboBox);
    }

    private void updateCurrencySymbol(Label label, ComboBox comboBox){
        label.setText(hashMap.get(comboBox.getValue())[1]);
    }
}