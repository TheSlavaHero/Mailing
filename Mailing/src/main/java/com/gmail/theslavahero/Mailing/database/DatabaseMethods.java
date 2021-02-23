package com.gmail.theslavahero.Mailing.database;

import com.gmail.theslavahero.Mailing.currency.Currency;
import com.gmail.theslavahero.Mailing.currency.CurrencyData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.gmail.theslavahero.Mailing.currency.WebConnection.getJsonfromPB;

public class DatabaseMethods {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void createCurrencyDatabase() {//creates Currency table and adds last 30 days currencies
        int j = 0;
        final long currentTime = System.currentTimeMillis();
        emf = Persistence.createEntityManagerFactory("MailingCreateDrop");
        em = emf.createEntityManager();
        for (long i = 0; i < 86400000L * 30; i += 86400000) {
            Date date = new Date(currentTime - i);
            addCurrency(date);
        }
    }

    public static Integer getHighestCurrencyId() {
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        TypedQuery<Integer> query = (TypedQuery<Integer>) em.createQuery("SELECT MAX(id) FROM Currency");
        Integer integer = query.getSingleResult();
        if (integer != null) {
            return integer;
        } else {
            return 0;
        }
    }

    public static Date getHighestCurrencyDate() {
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        TypedQuery<Date> query = (TypedQuery<Date>) em.createQuery("SELECT MAX(date) FROM Currency");
        Date date = query.getSingleResult();
        if (date == null) {
            date = new Date(0);
        }
        return date;
    }

    public static ArrayList<String> getCurrencyArray() {
        ArrayList<String> currencyList = new ArrayList<String>();
        currencyList.add("USD");
        currencyList.add("EUR");
        currencyList.add("RUB");
        return currencyList;
    }

    public static void updateCurrencyDatabase() {
        if (!freshDataCurrencyDatabase()) {
            final long currentTime = System.currentTimeMillis();
            Date date = getHighestCurrencyDate();
            long dateMillis = date.getTime();
            for (long i = 0; i + dateMillis + 86400000L < currentTime; i += 86400000L) {
                date = new Date(dateMillis + 86400000L + i);
                addCurrency(date);
            }
        }
    }

    public static boolean freshDataCurrencyDatabase() {//returns true if data in table Currency are up to date; else false
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateStrCurrent = simpleDateFormat.format(System.currentTimeMillis());
        TypedQuery<Date> query = (TypedQuery<Date>) em.createQuery("SELECT MAX(date) FROM Currency");
        Date dateDB = query.getSingleResult();
        String dateStrDB = simpleDateFormat.format(dateDB);
        return dateStrCurrent.equals(dateStrDB);
    }

    public static long getHighestCurrency(String currency) {
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("SELECT MAX('currency') FROM Currency");
        Long maxCurrency = query.getSingleResult();
        return maxCurrency;
    }

    public static long getAverageCurrency(String currency) {
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        TypedQuery<Long> query = (TypedQuery<Long>) em.createQuery("SELECT AVG('currency') FROM Currency");
        Long avgCurrency = query.getSingleResult();
        return avgCurrency;
    }

    public static void addCurrency(Date date) {//adds Eur USD and RUB currencies depending on data
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        CurrencyData currencyData = new CurrencyData();
        try {
            String json = getJsonfromPB(date);
            currencyData = CurrencyData.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Currency[] curArr = currencyData.getExchangeRate();
        for (Currency currency : curArr) {
            try {
                if (currency.getCurrency().equals("EUR") || currency.getCurrency().equals("USD") || currency.getCurrency().equals("RUB")) {
                    currency.setId(getHighestCurrencyId() + 1);
                    currency.setDate(date);
                    try {
                        em.getTransaction().begin();
                        em.persist(currency);
                        em.getTransaction().commit();
                    } catch (RuntimeException re) {
                        if (em.getTransaction().isActive()) {
                            em.getTransaction().rollback();
                        }
                        throw re;
                    }
                }
            } catch (NullPointerException e) {
            }
        }
    }
}
