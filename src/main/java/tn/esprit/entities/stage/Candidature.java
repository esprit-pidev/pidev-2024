package tn.esprit.entities.stage;

import tn.esprit.entities.User.User;
import tn.esprit.services.stageServices.OffreService;

import java.util.Date;

public class Candidature {
    private final OffreService OS = new OffreService();
    private int id;
    private int offre_id;
    private User etudiant;
    private Date date;
    private String status;
    private String competences;
    private String cv;
    private String cvFilePath;

    public String getCvFilePath() {
        return cvFilePath;
    }

    public Candidature(int id, int offre_id, User etudiant, Date date, String status, String competences, String cv) {
        this.id = id;
        this.offre_id = offre_id;
        this.etudiant = etudiant;
        this.date = date;
        this.status = status;
        this.competences = competences;
        this.cv = cv;
    }

    public Candidature(int offre_id, User etudiant, Date date, String status, String competences, String cv) {
        this.offre_id = offre_id;
        this.etudiant = etudiant;
        this.date = date;
        this.status = status;
        this.competences = competences;
        this.cv = cv;
    }

    public Candidature() {
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffre_id() {
        return offre_id;
    }

    public void setOffre_id(int offre_id) {
        this.offre_id = offre_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        Offre offre = OS.getById(offre_id);
        if (offre != null) {
            return "Candidature{" +
                    "offre=" + offre.getTitre() +
                    ", date=" + date +
                    ", status='" + status + '\'' +
                    ", competences='" + competences + '\'' +
                    ", cv='" + cv + '\'' +
                    '}';
        } else {
            return "Candidature{" +
                    "offre=" +
                    ", date=" + date +
                    ", status='" + status + '\'' +
                    ", competences='" + competences + '\'' +
                    ", cv='" + cv + '\'' +
                    '}';
        }
    }

}
