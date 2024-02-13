package tn.esprit.entities.User;

import java.util.Date;

public class Etudiant extends User{

    private int niveau;
    private String prenom,genre,cin,classe,profil_picture,adresse;
    private Date date_naissance;

    public Etudiant(int id, String nom, String email, String password, RoleName role, int niveau, String prenom, String genre, String cin, String classe, String profil_picture, String adresse, Date date_naissance) {
        super(id, nom, email, password, role);
        this.niveau = niveau;
        this.prenom = prenom;
        this.genre = genre;
        this.cin = cin;
        this.classe = classe;
        this.profil_picture = profil_picture;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
    }

    public Etudiant(String nom, String email, String password, RoleName role, int niveau, String prenom, String genre, String cin, String classe, String profil_picture, String adresse, Date date_naissance) {
        super(nom, email, password, role);
        this.niveau = niveau;
        this.prenom = prenom;
        this.genre = genre;
        this.cin = cin;
        this.classe = classe;
        this.profil_picture = profil_picture;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
    }

    public Etudiant(String nom, String email, String password,int niveau, String prenom, String genre, String cin, String classe, String profil_picture, String adresse, Date date_naissance) {
        super(nom, email, password);
        this.niveau = niveau;
        this.prenom = prenom;
        this.genre = genre;
        this.cin = cin;
        this.classe = classe;
        this.profil_picture = profil_picture;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + prenom + '\'' +
                ", niveau=" + niveau +
                ", genre='" + genre + '\'' +
                ", cin='" + cin + '\'' +
                ", classe='" + classe + '\'' +
                ", profil_picture='" + profil_picture + '\'' +
                ", adresse='" + adresse + '\'' +
                ", date_naissance=" + date_naissance +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
