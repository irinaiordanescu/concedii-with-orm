/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import database.HibernateUtil;
import java.util.List;
import models.TipAngajat;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Irina
 */

public class TipAngajatDao {
    public static List<TipAngajat> getAllTipAngajat() { //functia returneaza o lista cu toti TipAngajat
        List<TipAngajat> tipuriAngajati = null; //definesc o lista de tip TipAngajat, numita tipuriAngajati
        try (Session session = HibernateUtil.getSessionFactory().openSession()){ //se deschide o sesiune care se inchide la finalul try-catchului
            //realizez o interogare in limbaj Hibernate, prin care ii care BD sa returneze toate ob TipAngajat
            tipuriAngajati = session.createQuery("from TipAngajat", TipAngajat.class).getResultList();
            return tipuriAngajati;
        } catch (Exception e) {
            e.printStackTrace(); //afiseaza eroare
            return null;
        }
    }
    
    public static TipAngajat getTipAngajatWithId(int id) { //functia retuneaza TipAngajat cu id-ul respectiv
        TipAngajat tipAngajat = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { 
            tipAngajat = session.createQuery("from TipAngajat where id = :id", TipAngajat.class).setParameter("id", id).uniqueResult();
            return tipAngajat;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
