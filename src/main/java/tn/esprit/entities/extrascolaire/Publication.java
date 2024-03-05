package tn.esprit.entities.extrascolaire;

import java.util.Date;

public class Publication {
    private int id;
    private int club_rh_id;
    private String contenu;
    private Date date;

    public Publication() {
    }

    public Publication(int id, int club_rh_id, String contenu, Date date) {
        this.id = id;
        this.club_rh_id = club_rh_id;
        this.contenu = contenu;
        this.date = date;
    }
    private Date dateAjout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClub_rh_id() {
        return club_rh_id;
    }

    public void setClub_rh_id(int club_rh_id) {
        this.club_rh_id= club_rh_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateAjout() {
        return dateAjout;
    }
    public Publication(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
        // Initialisez d'autres propriétés si nécessaire
    }
}