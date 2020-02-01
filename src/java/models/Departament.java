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
@Table(name = "departament")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "denumire")
    private String denumire;

    @OneToMany(mappedBy = "departament")
    private Set<User> users = new HashSet<User>(); //set este o lista cu elem unice

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

    public Set<User> getUsers() {
        return this.users;
    }
}
