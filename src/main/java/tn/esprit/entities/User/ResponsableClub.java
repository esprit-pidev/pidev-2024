package tn.esprit.entities.User;

import java.time.LocalDate;

public class ResponsableClub extends Etudiant {

    public ResponsableClub(int id, String nom, String email, String password, RoleName role, int niveau, String prenom, String genre, String cin, String classe, String profil_picture, LocalDate date_naissance) {
        super(id, nom, email, password, role, niveau, prenom, genre, cin, classe, profil_picture, date_naissance);
    }

    public ResponsableClub(String nom, String email, String password, RoleName role, int niveau, String prenom, String genre, String cin, String classe, String profil_picture, LocalDate date_naissance) {
        super(nom, email, password, role, niveau, prenom, genre, cin, classe, profil_picture, date_naissance);
    }

    public ResponsableClub(String nom, String email, String password, int niveau, String prenom, String genre, String cin, String classe, String profil_picture, LocalDate date_naissance) {
        super(nom, email, password, niveau, prenom, genre, cin, classe, profil_picture, date_naissance);
    }

    @Override
    public String toString() {
        return "ResponsableCLUB {" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + this.getPrenom() + '\'' +
                ", niveau=" + this.getNiveau() +
                ", genre='" + this.getGenre() + '\'' +
                ", cin='" + this.getCin() + '\'' +
                ", classe='" + this.getClasse() + '\'' +
                ", profil_picture='" + this.getProfil_picture() + '\'' +
                ", date_naissance=" + this.getDate_naissance() +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }

}
