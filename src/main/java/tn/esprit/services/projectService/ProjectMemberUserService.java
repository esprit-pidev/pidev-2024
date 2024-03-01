package tn.esprit.services.projectService;
import tn.esprit.entities.project.ProjectMemberUser;
import tn.esprit.tools.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectMemberUserService {
    Connection cnx = MyDB.getInstance().getCnx();

    public void ajouterMemberUser (ProjectMemberUser projectMemberUser){
        String sql = "INSERT INTO project_members_user (project_members_id, user_id) VALUES (?, ?)";
        long millis = System.currentTimeMillis();
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectMemberUser.getIdMember());
            statement.setInt(2, projectMemberUser.getIdUser());
            statement.executeUpdate();
            System.out.println("Member Added : " + projectMemberUser.getIdUser());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

    }

}
