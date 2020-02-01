/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
/**
 *
 * @author Irina
 */

@Entity
@Table(name = "formular_concediu")
public class FormularConcediu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tip_concediu")
    private String tipConcediu;

    @Column(name = "descriere")
    private String descriere;

    @Column(name = "numar_zile_libere")
    private int numarZileLibere;

    @Column(name = "prima_zi_concediu")
    private Date primaZiConcediu;

    @Column(name = "ultima_zi_concediu")
    private Date ultimaZiConcediu; //Date e obiect

    @ManyToOne
    @JoinColumn(name = "fk_userid")
    private User user; // User e obiect

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipConcediu() {
        return tipConcediu;
    }

    public void setTipConcediu(String tipConcediu) {
        this.tipConcediu = tipConcediu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNumarZileLibere() {
        return numarZileLibere;
    }

    public void setNumarZileLibere(int numarZileLibere) {
        this.numarZileLibere = numarZileLibere;
    }

    public Date getPrimaZiConcediu() {
        return primaZiConcediu;
    }

    public void setPrimaZiConcediu(Date primaZiConcediu) {
        this.primaZiConcediu = primaZiConcediu;
    }

    public Date getUltimaZiConcediu() {
        return ultimaZiConcediu;
    }

    public void setUltimaZiConcediu(Date ultimaZiConcediu) {
        this.ultimaZiConcediu = ultimaZiConcediu;
    }

    public User getUser() {
        return this.user;
    }

   public void setUser(User user) {
        this.user = user;
    }
}
