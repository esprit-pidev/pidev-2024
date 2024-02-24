package tn.esprit.entities.stage;

import javafx.beans.value.ObservableValue;

import java.util.Date;
import java.util.Objects;

public class Candidature {
    private int id;
    private int offre_id;
    private int user_id;
    private Date date;
    private String status;
    private String competences;
    private String cv;

    public Candidature (int id, int offre_id, int user_id, Date date, String status, String competences, String cv) {
        this.id = id;
        this.offre_id = offre_id;
        this.user_id = user_id;
        this.date = date;
        this.status = status;
        this.competences = competences;
        this.cv = cv;
    }

    public Candidature(int offre_id, int user_id, Date date, String status, String competences, String cv) {
        this.offre_id = offre_id;
        this.user_id = user_id;
        this.date = date;
        this.status = status;
        this.competences = competences;
        this.cv = cv;
    }

    public Candidature() {
    }


    public int getId() {
        return id;
    }

    public int getOffre_Id() {
        return offre_id;
    }

    public int getUser_Id() {
        return user_id;
    }

    public Date getDate() {
        return this.date;
    }

    public String getStatus() {
        return status;
    }

    public String getCompetences() {
        return competences;
    }

    public String getCv() {
        return cv;
    }

    public void setId(int id) {
        id = id;
    }

    public void setOffre_Id(int offre_Id) {
        this.offre_id = offre_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_id = user_Id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return "Candidature{" +
                "Id=" + id +
                ", offre_Id=" + offre_id +
                ", user_Id=" + user_id +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", competences='" + competences + '\'' +
                ", cv='" + cv + '\'' +
                '}';
    }

    public Candidature(String competences, String cv) {
        this.competences = competences;
        this.cv = cv;
    }

}
