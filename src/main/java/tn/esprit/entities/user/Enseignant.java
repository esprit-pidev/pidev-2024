package tn.esprit.entities.User;

import java.time.LocalDate;

public class Enseignant extends tn.esprit.entities.User.User {

    private String prenom,cin,genre,profil_picture;
    private LocalDate date_naissance;

    public Enseignant(int id, String nom, String email, String password, tn.esprit.entities.User.RoleName role, String prenom, String cin, String genre, LocalDate date_naissance, String profil_picture) {
        super(id, nom, email, password, role);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
        this.profil_picture=profil_picture;
    }

    public Enseignant(String nom, String email, String password,String prenom, String cin, String genre, LocalDate date_naissance,String profil_picture) {
        super(nom, email, password);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
        this.profil_picture=profil_picture;
    }

    public Enseignant(String nom, String email, String password, tn.esprit.entities.User.RoleName role, String prenom, String cin, String genre, LocalDate date_naissance, String profil_picture) {
        super(nom, email, password, role);
        this.prenom = prenom;
        this.cin = cin;
        this.genre = genre;
        this.date_naissance = date_naissance;
        this.profil_picture=profil_picture;
    }

    public Enseignant(String nom, String email, String password, tn.esprit.entities.User.RoleName role, String prenom, String cin, String genre, LocalDate date_naissance) {
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

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
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
                ", profil_picture='" + profil_picture + '\'' +
                '}';
    }
}
