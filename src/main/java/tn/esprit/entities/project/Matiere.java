package tn.esprit.entities.project;

public class Matiere {
    private String niveau ;
    private  String nom;

    public Matiere() {
    }

    public Matiere(String niveau, String nom) {
        this.niveau = niveau;
        this.nom = nom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "niveau='" + niveau + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}
