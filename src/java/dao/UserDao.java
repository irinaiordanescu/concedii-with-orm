/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import models.Departament;
import models.TipAngajat;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Irina
 */
public class UserDao {

    //functia returneaza un obiect numit user, cu username si password date
    //cand este returnat user, primesc toate info despre el(este_admin, password,....)
    public static User getUserWithUsernameAndPassword(String username, String password) {
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //User.class - indica faptul ca sigur returneaza un ob de tip user
            //uniqueResult() - am un singur rezultat
            user = session.createQuery("from User where username = :username", User.class).setParameter("username", username).uniqueResult();
            if (user == null) {
                return null;
            }
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getUsersWithTipAngajat(String tipAngajat) { //fct returneaza o lista de user
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //setParameter ia variabila din query :tipAngajat si ii da valoarea din parantezele fct
            List<User> users = session.createQuery("select u from User u join u.tipAngajat ta where ta.denumire = :tipAngajat")
                    .setParameter("tipAngajat", tipAngajat).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getUsersWithDepartment(int idDepartament) { //fct returneaza toti users in fct de id-ul departamentului
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("select u from User u join u.departament d where d.id = :id")
                    .setParameter("id", idDepartament).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getUsersFromSameDepartment(int userId) { //fct returneaza toti utilizatorii din acelasi departament (returneaza toti colegii unui user)
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = getUserWithId(userId);
            if (user == null) {
                throw new Error("Nu exista utiliztorul");
            }
            List<User> users = session.createQuery("select u from User u join u.departament d where d.denumire = :denumire")
                    .setParameter("denumire", user.getDepartament().getDenumire()).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User").getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getUserWithId(int id) {
        User user = null; //am creat un ob nou initializat cu null
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            user = session.createQuery("from User where id = :id", User.class).setParameter("id", id).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User updateUsernameAndPassword(int id, String newUsername, String newPassword) {
        User user = getUserWithId(id); //trimit id-ul ca sa iau user-ul din BD dinainte sa fie modificat
        if (user == null) {
            return null;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); //incepe tranzactia pt tabelele din BD, implicate in query-ul de mai jos
            if (newUsername != null || newUsername.length() != 0) {
                user.setUsername(newUsername);
            }
            if (newPassword != null && newPassword.length() != 0) {
                user.setPassword(newPassword);
            }
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Error e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public static User updateAllInformation(int id, String username, int idDepartament, int idTipAngajat, int esteAdmin) {
        User user = getUserWithId(id);
        if (user == null) {
            return null;
        }
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (username != null || username.length() != 0) {
                user.setUsername(username);
            }

            Departament departament = DepartamentDao.getDepartmentWithId(idDepartament);
            if (departament == null) {
                throw new Error("Nu exista departamentul");
            } else {
                user.setDepartament(departament);
            }

            TipAngajat tipAngajat = TipAngajatDao.getTipAngajatWithId(idTipAngajat);
            if (tipAngajat == null) {
                throw new Error("Nu exista departamentul");
            } else {
                user.setTipAngajat(tipAngajat);
            }

            user.setEsteAdmin(esteAdmin);
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Error e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public static User createUser(String username, String password, int idDepartament, int idTipAngajat, int esteAdmin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = new User();
            transaction = session.beginTransaction();

            user.setUsername(username);
            user.setPassword(password);
            user.setEsteAdmin(esteAdmin);

            Departament departament = DepartamentDao.getDepartmentWithId(idDepartament);
            if (departament == null) {
                throw new Error("Nu exista departamentul");
            } else {
                user.setDepartament(departament);
            }

            TipAngajat tipAngajat = TipAngajatDao.getTipAngajatWithId(idTipAngajat);
            if (tipAngajat == null) {
                throw new Error("Nu exista departamentul");
            } else {
                user.setTipAngajat(tipAngajat);
            }

            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public static User removeUser(String userid) {
        User user = new User(); //creez un ob nou; new User() - constructor fara param
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user.setId(Integer.parseInt(userid));
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
