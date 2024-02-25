package tn.esprit.entities.extrascolaire;

public class Activite {
    private int activiteID;
    private String nom;
    private String description;

    public Activite() {
    }

    public Activite(int activiteID, String nom, String description) {
        this.activiteID = activiteID;
        this.nom = nom;
        this.description = description;
    }

    public int getID() {
        return activiteID;
    }

    public void setID(int activiteID) {
        this.activiteID = activiteID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "activiteID=" + activiteID +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // La suppression du setter pour la descriptionActivité indique que l'utilisateur ne peut pas la modifier.


}
