package tn.esprit.entities.project;
import java.util.Date;

public class ProjectMembers {
    private int id;
    private Project project;

    private Date joined_at;

    public ProjectMembers(int id, Project project, Date joined_at) {
        this.id = id;
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

    public int getIdProject (){
            if (project != null) {
                return project.getId();
            } else {
                return -1; // Ou une autre valeur par défaut selon vos besoins
            }
        }


    public  void setProjectId(int idProject){
        if (project == null) {
            project = new Project();
        }
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

