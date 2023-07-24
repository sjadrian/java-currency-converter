package com.example.currencyconverter.model;

import java.net.URL;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetJsonData {

    public static CurrencyData getData() {

        CurrencyData data = null;

        try {
            URL url = new URL("https://api.exchangerate-api.com/v4/latest/euro") ;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // check if connection is made
            int responseCode = connection.getResponseCode();
            System.out.println("Response code:  " + responseCode);

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(url.openStream(), CurrencyData.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
