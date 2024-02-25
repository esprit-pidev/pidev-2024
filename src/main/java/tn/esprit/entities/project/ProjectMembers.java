package tn.esprit.entities.project;
import java.util.Date;

public class ProjectMembers {
    private int id;
    private Project project;
    private boolean is_owner;
    private Date joined_at;

    public ProjectMembers(int id, Project project, boolean is_owner, Date joined_at) {
        this.id = id;
        this.project= project;
        this.is_owner= is_owner;
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
        return project.getId();
    }
    public  void setIdProject(int idProject){
        project.setId(idProject);
    }

    public boolean getIsOwner() {
        return is_owner;
    }

    public void setIsOwner(boolean is_owner) {
        this.is_owner = is_owner;
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
                ", is_owner='" + is_owner + '\'' +
                ", joined_at='" + joined_at + '\'' +
                '}';
    }
}

