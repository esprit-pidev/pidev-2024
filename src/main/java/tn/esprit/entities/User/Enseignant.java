package tn.esprit.entities.User;

import java.util.Date;

public class Enseignant extends User{

    private String prenom,cin,genre;
    private Date date_naissance;

    public Enseignant(int id, String nom, String email, String password, RoleName role, String prenom, String cin, String genre, Date date_naissance) {
        super(id, nom, email, password, role);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
    }

    public Enseignant(String nom, String email, String password,String prenom, String cin, String genre, Date date_naissance) {
        super(nom, email, password);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
    }

    public Enseignant(String nom, String email, String password, RoleName role, String prenom, String cin, String genre, Date date_naissance) {
        super(nom, email, password, role);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    @Override
    public String toString() {
        return "Enseignant{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + prenom + '\'' +
                ", genre='" + genre + '\'' +
                ", cin='" + cin + '\'' +
                ", date_naissance=" + date_naissance +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
