package tn.esprit.entities.extrascolaire;

import java.util.Date;

public class Inscription {
    private int id;
    private int etudiant_id;
    private int activite_id;
    private Date date;
    private String nom;
    private String prenom;
    private String num_tel;
    private String email ;


    public Inscription() {
    }

    public Inscription(int id, int etudiant_id, int activite_id,
                       Date date, String nom, String prenom, String num_tel, String email ) {
        this.id= id;
        this.etudiant_id = etudiant_id;
        this.activite_id = activite_id;
        this.date = date;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtudiant_id() {
        return etudiant_id;
    }

    public void setEtudiant_id(int etudiant_id) {
        this.etudiant_id = etudiant_id;
    }

    public int getActivite_id() {
        return activite_id;
    }

    public void setActivite_id(int activite_id) {
        this.activite_id = activite_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
