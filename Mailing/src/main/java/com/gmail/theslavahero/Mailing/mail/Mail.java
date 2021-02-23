package com.gmail.theslavahero.Mailing.mail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Mail")
public class Mail {
    @Id
    private String mail;
    private String currencies;

    public Mail(String mail, String currencies) {
        this.mail = mail;
        this.currencies = currencies;
    }

    public Mail() {
    }

    public static String toJson(Mail mail) {
        String json = new Gson().toJson(mail);
        return json;
    }

    public static Mail fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        Mail mail = gson.fromJson(json, Mail.class);
        return mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "mail='" + mail + '\'' +
                ", currencies='" + currencies + '\'' +
                '}';
    }
}

