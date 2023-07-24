package com.example.currencyconverter.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetJsonData {

    private CurrencyData data;

    public GetJsonData() {

        try {
            URL url = new URL("https://api.exchangerate-api.com/v4/latest/euro") ;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // check if connection is made
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                this.data = mapper.readValue(url.openStream(), CurrencyData.class);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CurrencyData getData() {
        return data;
    }
}
