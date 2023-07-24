package com.example.currencyconverter.model;


public class CurrencyConverter {

    private CurrencyData data;

    public CurrencyConverter() {
        this.data = new GetJsonData().getData();
    }

    public CurrencyData getData() {
        return data;
    }

    public String convert(double initial, String initialCurrency, String targetCurrency) {
        double conversion = Math.round(initial * (data.getRates().get(targetCurrency) / data.getRates().get(initialCurrency)) * 100) / 100.0;
        if (conversion == 0.0) {
            return "0";
        }
        return String.valueOf(conversion);
    }

    public String showConversion(String initialCurrency, String targetCurrency) {

        double conversionValue = data.getRates().get(targetCurrency) / data.getRates().get(initialCurrency);

        if (conversionValue >= 0.01) {
            conversionValue = Math.round(conversionValue * 100) / 100.0;
            return "1 " + initialCurrency + " = " +  conversionValue + " " + targetCurrency;
        } else {
            return String.format("1 %s = %f %s", initialCurrency, conversionValue, targetCurrency);
        }
    }
}
