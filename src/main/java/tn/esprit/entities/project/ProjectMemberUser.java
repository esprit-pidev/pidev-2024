package tn.esprit.entities.project;

import tn.esprit.entities.User.User;

public class ProjectMemberUser {
    private ProjectMembers projectMembers;
    private User user;

    public ProjectMemberUser(ProjectMembers projetMembers, User user) {
        this.projectMembers = projetMembers;
        this.user = user;
    }

    public ProjectMemberUser() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null){
            user = new User();
        }
        this.user = user;
    }

    public void setIdMember(int idmember){
        projectMembers.setId(idmember);
    }
    public int getIdMember(){
        return projectMembers.getId();
    }

    public int getIdUser (){
        return user.getId();
    }

    public void setIdUser(int userId){
        user.setId(userId);
    }

    @Override
    public String toString() {
        return "ProjectMemberUser{" +
                "Member Id=" + (projectMembers != null ? projectMembers.getId() :"null") +
                ", Id user=" +  (user != null ? user.getId() :"null") +
                '}';
    }
}
