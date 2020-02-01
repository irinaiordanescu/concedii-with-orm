/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import database.HibernateUtil;
import java.util.List;
import models.Departament;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Irina
 */

public class DepartamentDao {
    public static List<Departament> getAllDepartamente() { //functia returneaza o lista cu toate departamentele
        List<Departament> departamente = null; //definesc o lista de tip Departamaent, numita departamente
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { //se deschide o sesiune care se inchide la finalul try-catchului
            //se realizeaza o interogare in limbaj Hibernate, prin care ii care BD sa returneze toate ob Departament
            departamente = session.createQuery("from Departament", Departament.class).getResultList();
            return departamente;
        } catch (Exception e) {
            e.printStackTrace(); //afiseaza eroare
            return null;
        }
    }

    public static Departament getDepartmentWithId(int id) { //fct returneaza departamentul cu id-ul respectiv
        Departament departament = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            departament = session.createQuery("from Departament where id = :id", Departament.class).setParameter("id", id).uniqueResult();
            return departament;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
