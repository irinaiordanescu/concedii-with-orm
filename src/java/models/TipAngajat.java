/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Irina
 */

@Entity
@Table(name = "tip_angajat")
public class TipAngajat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "denumire")
    private String denumire;

    @Column(name = "nr_zile_concediu")
    private int nrZileConcediu;

    @Column(name = "prioritate")
    private int prioritate;

    @OneToMany(mappedBy = "tipAngajat")
    private Set<User> users = new HashSet<User>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrZileConcediu() {
        return nrZileConcediu;
    }

    public void setNrZileConcediu(int nrZileConcediu) {
        this.nrZileConcediu = nrZileConcediu;
    }

    public int getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(int prioritate) {
        this.prioritate = prioritate;
    }

    public Set<User> getUsers() {
        return this.users;
    }
}
