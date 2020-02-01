/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

/**
 *
 * @author Irina
 */

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "username", unique = true)
    private String username;
    
    @Column(columnDefinition= "LONGBLOB", name = "password")
    @ColumnTransformer(
        read = "AES_DECRYPT(password, 'yourkey')",
        write = "AES_ENCRYPT(?, 'yourkey')"
    )
    private String password;
    
    @Column(name = "este_admin")
    private int esteAdmin;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_departament")
    private Departament departament;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_tipangajat")
    private TipAngajat tipAngajat;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy ="user")
    private Set<FormularConcediu> formulareConcedii;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getEsteAdmin() {
        return esteAdmin;
    }

    public void setEsteAdmin(int esteAdmin) {
        this.esteAdmin = esteAdmin;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public TipAngajat getTipAngajat() {
        return tipAngajat;
    }

    public void setTipAngajat(TipAngajat tipAngajat) {
        this.tipAngajat = tipAngajat;
    }

    public Set<FormularConcediu> getFormulareConcedii() {
        return formulareConcedii;
    }

    public void setFormulareConcedii(Set<FormularConcediu> formulareConcedii) {
        this.formulareConcedii = formulareConcedii;
    }
}
