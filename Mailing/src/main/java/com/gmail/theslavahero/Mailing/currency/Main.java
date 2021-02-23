package com.gmail.theslavahero.Mailing.currency;

import java.util.ArrayList;

import static com.gmail.theslavahero.Mailing.database.DatabaseMethods.createCurrencyDatabase;
import static com.gmail.theslavahero.Mailing.database.DatabaseMethods.getCurrencyArray;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> currencyList = getCurrencyArray();
        String currencies = "";
        StringBuilder sb = new StringBuilder(currencies);


        currencies = sb.toString();
        System.out.println("curr" + currencies);
        if (currencies.equals("")) {
            System.out.println("true");
        }
    }
}
