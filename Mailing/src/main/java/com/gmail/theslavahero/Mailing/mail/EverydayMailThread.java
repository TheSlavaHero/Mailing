package com.gmail.theslavahero.Mailing.mail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

import static com.gmail.theslavahero.Mailing.SendEmailTLS.send;
import static com.gmail.theslavahero.Mailing.database.DatabaseMethods.*;

public class EverydayMailThread implements Runnable {
    public void run() {
        while (true) {
            updateCurrencyDatabase();
            int lastIndex = 10;
            Thread t = Thread.currentThread();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MailingUpdate");
            EntityManager em = emf.createEntityManager();
            TypedQuery<String> query = (TypedQuery<String>) em.createQuery("SELECT mail FROM Mail");
            ArrayList<String> mails = (ArrayList<String>) query.getResultList();
            lastIndex = mails.size();
            for (int i = 0; i < lastIndex; i++) {
                send(mails.get(i), createMessage(i));
            }
            try {
                Thread.sleep(86400000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String createMessage(int i) {
        String message = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MailingUpdate");
        EntityManager em = emf.createEntityManager();
        TypedQuery<String> queryEur = (TypedQuery<String>) em.createQuery("SELECT currencies FROM Mail");
        ArrayList<String> currencies = (ArrayList<String>) queryEur.getResultList();
        String[] currencyArr = currencies.get(i).split(".");
        for (String currency : currencyArr) {
            message += currency + " currency: max currency - " +
                    getHighestCurrency(currency) + "avg currency - " + getAverageCurrency(currency);
        }
        return message;
    }
}
