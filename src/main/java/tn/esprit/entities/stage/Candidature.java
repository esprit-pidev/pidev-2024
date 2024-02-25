package tn.esprit.entities.stage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

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

    public StringProperty competencesProperty() {
        return new SimpleStringProperty(this.competences);
    }

    public StringProperty cvProperty() {
        return new SimpleStringProperty(this.cv);
    }

    public StringProperty statusProperty() {
        return new SimpleStringProperty(this.status);
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(this.id);
    }

    public Candidature() {
    }

    // Getters et setters

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}
