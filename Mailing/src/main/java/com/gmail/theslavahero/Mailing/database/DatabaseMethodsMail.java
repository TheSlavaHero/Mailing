package com.gmail.theslavahero.Mailing.database;

import com.gmail.theslavahero.Mailing.mail.Mail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;


public class DatabaseMethodsMail {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void addMail(Mail mail) {
        emf = Persistence.createEntityManagerFactory("MailingUpdate");
        em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mail);
            em.getTransaction().commit();
        } catch (RuntimeException re) {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw re;

        }
    }
}

