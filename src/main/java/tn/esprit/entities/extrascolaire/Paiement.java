package tn.esprit.entities.extrascolaire;

import java.sql.Date;

public class Paiement {
    private int id;
    private int inscription_id; // Clé étrangère vers la table des inscriptions
    private double montant;
    private Date date;

    public Paiement() {
    }



    public Paiement(int inscription_id, double montant) {
        this.inscription_id = inscription_id;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInscription_id() {
        return inscription_id;
    }

    public void setInscription_id(int inscription_id) {
        this.inscription_id = inscription_id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate_paiement(Date date) {
        this.date= date;
    }

    public void setDate(java.util.Date date) {
    }
}
