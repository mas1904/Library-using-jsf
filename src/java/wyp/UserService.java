/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyp;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Łukasz
 */
class UserService {
    Session session = null;
    
    public UserService() {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public Czytelnicy find(String username, String password) {
        Czytelnicy w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Czytelnicy as w where w.mail='"+username+"' and w.haslo='"+ password+"'");
            w = (Czytelnicy) q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    public Czytelnicy findU(String username) {
        Czytelnicy w = null;
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery("from Czytelnicy as w where w.mail='"+username+"'");
            w = (Czytelnicy) q.uniqueResult();
            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return w;
    }

    void add(String imie, String nazw, String miasto, String ulica, String mail, String haslo) {
        
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Czytelnicy c = new Czytelnicy();
            c.setAktywny(false);
            c.setBlokada(false);
            c.setImie(imie);
            c.setKara(0);
            c.setMail(mail);
            c.setMiasto(miasto);
            c.setNazwisko(nazw);
            c.setStopien(1);
            c.setUlica(ulica);
            c.setHaslo(haslo);
            session.persist(c);
            tx.commit();
            session.flush();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
