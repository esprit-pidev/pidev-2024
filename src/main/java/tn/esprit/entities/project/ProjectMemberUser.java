package tn.esprit.entities.project;

import tn.esprit.entities.User.User;

public class ProjectMemberUser {
    private int projet_member_id;
    private User user;

    public ProjectMemberUser(int projet_member_id, User user) {
        this.projet_member_id = projet_member_id;
        this.user = user;
    }

    public ProjectMemberUser() {
    }

    public int getProjet_member_id() {
        return projet_member_id;
    }

    public void setProjet_member_id(int projet_member_id) {
        this.projet_member_id = projet_member_id;
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


    @Override
    public String toString() {
        return "ProjectMemberUser{" +
                "projet_member_id=" + projet_member_id +
                ", user=" + user +
                '}';
    }
}
