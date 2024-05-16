package tn.esprit.entities.project;

import java.sql.Date;

public class fichier {
    private int id ;
    private ProjectMembers member;
    private String nom ;
    private Date date_ajout;
    private  String path ;

    public fichier(int id,String path , ProjectMembers member, String nom, Date date_ajout) {
        this.id = id;
        this.path = path;
        this.member = member;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public fichier(ProjectMembers member, String path, String nom, Date date_ajout) {
        this.member = member;
        this.path = path;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public fichier() {
        this.member = new ProjectMembers();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectMembers getMember() {
        return member;
    }

    public void setMember(ProjectMembers member) {
        if (member == null){
            member = new ProjectMembers();
        }
        this.member = member;
    }
    public int getIdMember() {
        if (member != null) {
            return member.getId();
        } else {
            return -1; // Ou une autre valeur par défaut appropriée
        }
    }
    public void setIdMember(int idMember){
        member.setId(idMember);
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    @Override
    public String toString() {
        return "fichier{" +
                "id=" + id +
                ", member=" + (member != null ? member.getId() : null) +
                ", nom='" + nom + '\'' +
                ", date_ajout=" + date_ajout +
                '}';
    }

}
