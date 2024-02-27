package tn.esprit.entities.project;
import tn.esprit.entities.User.Etudiant;
import java.util.Date;

public class ProjectMembers {
    private int id;
    private Etudiant etudiant;
    private Project project;

    private Date joined_at;

    public ProjectMembers(int id, Etudiant etudiant ,Project project, Date joined_at) {
        this.id = id;
        this.etudiant = etudiant;
        this.project= project;
        this.joined_at = joined_at;
    }


    public ProjectMembers() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public String getEmail(){
        return etudiant.getEmail();
    }
    public void setEmail(Etudiant etudiant) {
        if (etudiant == null){
            etudiant = new Etudiant();
        }
        this.etudiant = etudiant;
    }
    public int getIdProject (){
        return project.getId();
    }
    public  void setIdProject(int idProject){
        project.setId(idProject);
    }


    public Date getJoinedAt() {
        return joined_at;
    }

    public void setJoinedAt(Date joined_at) {
        this.joined_at = joined_at;
    }


    @Override
    public String toString() {
        return "Project Members{" +
                "id=" + id +
                ", project_id=" + project.getId() +
                ", joined_at='" + joined_at + '\'' +
                '}';
    }
}

