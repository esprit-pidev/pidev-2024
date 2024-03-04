package tn.esprit.entities.project;

import tn.esprit.entities.User.User;

import java.util.Date;


public class Taches {
    private int id;
    private ProjectMembers projectMembers;
    private String etat ;
    private String description ;
    private Date date_ajout ;
    private  Date dedline ;

    public Taches() {
    }

    public Taches(int id, ProjectMembers projectMembers, String etat, String description, Date date_ajout, Date dedline) {
        this.id = id;
        this.projectMembers = projectMembers;
        this.etat = etat;
        this.description = description;
        this.date_ajout = date_ajout;
        this.dedline = dedline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectMembers getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(ProjectMembers projectMembers) {
            if (projectMembers == null){
                projectMembers = new ProjectMembers();
            }
            this.projectMembers = projectMembers;
        }

    public int getIdProjectMembers ()  {  return (projectMembers != null) ? projectMembers.getId() : -1;

}

    public void setIdProjectMembers(int projectMembersId){
        if (projectMembers == null) {
            projectMembers = new ProjectMembers();
        }
        projectMembers.setId(projectMembersId);

    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Date getDedline() {
        return dedline;
    }

    public void setDedline(Date dedline) {
        this.dedline = dedline;
    }

    @Override
    public String toString() {
        return "Taches{" +
                "id=" + id +
                "student id" + (projectMembers != null ? projectMembers.getId() :"null") +
                ", etat='" + etat + '\'' +
                ", description='" + description + '\'' +
                ", date_ajout=" + date_ajout +
                ", dedline=" + dedline +
                '}';
    }
}
