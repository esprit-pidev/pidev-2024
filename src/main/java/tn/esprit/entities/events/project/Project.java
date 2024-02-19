package tn.esprit.entities.events.project;
import java.util.Date;
public class Project {
    private int id;
    private int teacher_id;
    private String nom;
    private String description;
    private String classe;
    private String matiere;
    private Date created_at;
    private Date updated_at;

    public Project(int id, int teacher_id, String nom, String description, String classe, String matiere, Date created_at, Date updated_at) {
        this.id = id;
        this.teacher_id = teacher_id;
        this.nom = nom;
        this.description = description;
        this.classe = classe;
        this.matiere = matiere;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Project(int teacher_id, String nom, String description, String classe, String matiere, Date created_at, Date updated_at) {
        this.teacher_id = teacher_id;
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

    public int getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(int teacher_id) {
        this.teacher_id = teacher_id;
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

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", teacher id=" + teacher_id +
                ", nom du projet='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", updated_at=" + 	updated_at +
                '}';
    }
}
