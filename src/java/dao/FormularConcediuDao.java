/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import database.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.FormularConcediu;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Irina
 */

public class FormularConcediuDao {
    public static List<FormularConcediu> getAllFormulareConcediu() { //o functie numita getAllFormulareConcediu, de tip FormularConcediu, care returneaza lista de formulare
        List<FormularConcediu> formulare = null; //o lista de tip FormularConcediu numita formulare
        //am un ob de tip Seesion, numit session care apeleaza HibernateUtil, obtine sesiunea si o deschide
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //FormularConcediu.class - ii spune hibernateului ca rezultatul interogariisa respecte obiectul definit de clasa respectiva
            //getResultList() - ia toate rezultatele si le pune intr-o lista
            formulare = session.createQuery("from FormularConcediu", FormularConcediu.class).getResultList(); //interogarea
            return formulare;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<FormularConcediu> getFormulareConcediiFromUserId(int userId) { //fct returneaza formularele de concedii pt id-ul respectiv
        List<FormularConcediu> formulare = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //in interogare trebuie sa unesc(=join) tabelul FormularConcediu cu User si sa returnez doar rezultatele care au userId respectiv
            //fc - prescurtarea de la formular concediu
            //u - prescurtarea de la user
            //fc.user - 
            //u.id - 
            //:userId - 
            formulare = session.createQuery("select fc from FormularConcediu fc join fc.user u where u.id = :userId ", 
                    FormularConcediu.class).setParameter("userId", userId).getResultList(); //interogarea
            return formulare;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteFormularConcediuWithId(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //tranzactia are grija ca apelurile sa fie in ordine si sincronizate
            //se asigura ca orindea in care sunt transmise datele este respectata
            //tranzactiile se fol cand am apeluri catre BD
            transaction = session.beginTransaction();
            FormularConcediu fc = new FormularConcediu(); //creez un ob gol           
            fc.setId(id); //....... formularul de concdiu cu id-ul respectiv           
            session.delete(fc); //il stergem        
            transaction.commit(); //realizeaza transaction
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void createFormularConcediu(String tip_concediu, String descriere, Date prima_zi_concediu, Date ultima_zi_concediu, int user_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            FormularConcediu fc = new FormularConcediu();
            fc.setTipConcediu(tip_concediu); //echivalentul unui constructor; setez tip_concediu
            fc.setDescriere(descriere);
            fc.setPrimaZiConcediu(prima_zi_concediu);
            fc.setUltimaZiConcediu(ultima_zi_concediu);
            //trebuie sa iau user din BD pt ca doar un user care exista isi poate crea un formular de concediu
            //apelez UserDao ca sa iau user_id
            User user = UserDao.getUserWithId(user_id);
            if (user == null) {
                throw new Error("No user found");
            } else {
                fc.setUser(user);
            }
            session.save(fc); //sesiunea salveaza formularul de concediu
            transaction.commit(); //realizeaza tranzactia
        } catch (Exception e) {
            if (transaction != null) { //if(tranzactia este inceputa)
                transaction.rollback(); //o anuleaza
            }
            e.printStackTrace(); //afiseaza eroare
        }
    }

    public static int numarFormulareConcediiDinDepartamentInPerioadaCeruta(int userid, String deLaData, String panaLaData) {
        //fct returneaza 
        int numarFinal = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Date primaZi = format.parse(deLaData); //formateaza data, o face intr-un annumit format
            Date ultimaZi = format.parse(panaLaData);
            List<User> useriDinDepartament = UserDao.getUsersFromSameDepartment(userid);
            for (User user : useriDinDepartament) { //returneaza fiecare user din departament               
                if (user.getId() != userid) { //if este doar pentru restul angajatilor pt ca avem conditiile
                    for (FormularConcediu fc : user.getFormulareConcedii()) {
                        //daca ziua pe care angajatul o are nu e in conflict cu cea din parametrii crestem numarFinal 
                        //pt ca trebuie sa aflam cati angajati sunt in concediu in aceea perioada
                        if (primaZi.after(fc.getPrimaZiConcediu()) && primaZi.before(fc.getUltimaZiConcediu())) {
                            if (ultimaZi.after(fc.getPrimaZiConcediu()) && ultimaZi.before(fc.getUltimaZiConcediu())) {
                                numarFinal++;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numarFinal;
    }
}
