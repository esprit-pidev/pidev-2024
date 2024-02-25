package tn.esprit.entities.project;
import tn.esprit.entities.User.User;

import java.util.Date;
public class Project {
    private int id;
    private User user;
    private String nom;
    private String description;
    private String classe;
    private String matiere;
    private Date created_at;
    private Date updated_at;

    public Project(int id, User user, String nom, String description, String classe, String matiere, Date created_at, Date updated_at) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.description = description;
        this.classe = classe;
        this.matiere = matiere;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Project(User user, String nom, String description, String classe, String matiere, Date created_at, Date updated_at) {
        this.user = user;
        this.nom = nom;
        this.description = description;
        this.classe = classe;
        this.matiere = matiere;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null){
            user = new User();
        }
        this.user = user;
    }

    public int getIdUser (){
       return user.getId();
    }

    public void setIdUser(int userId){
        user.setId(userId);
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                "teacher id" + (user != null ? user.getId() :"null") +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", classe='" + classe + '\'' +
                ", matiere='" + matiere + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
