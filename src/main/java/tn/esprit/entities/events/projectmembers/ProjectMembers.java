package tn.esprit.entities.events.projectmembers;
import java.util.Date;

public class ProjectMembers {
    private int id;
    private int project_id;
    private boolean is_owner;
    private Date joined_at;

    public ProjectMembers(int id, int project_id, boolean is_owner, Date joined_at) {
        this.id = id;
        this.project_id= project_id;
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

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
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
                ", project_id=" + project_id +
                ", is_owner='" + is_owner + '\'' +
                ", joined_at='" + joined_at + '\'' +
                '}';
    }
}

