package com.example.emrebabur.producttransactions.Pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emre.babur on 19.09.2016.
 */
public class Constants {
    public static final String RATES_FILE_PATH = "rates.json";
    public static final String TRANSACTIONS_FILE_PATH = "transactions.json";
    public static final String BASE_CURRENCY = "GBP";
    public static Map<String, String> currencySymbols;

    private static Constants instance = new Constants();
    private Constants() {
        currencySymbols = new HashMap<String, String>();
        currencySymbols.put("GBP", "£");
        currencySymbols.put("USD", "$");
        currencySymbols.put("EUR", "€");
    }
}
