package tn.esprit.services.eventsServices.projectMembersServices;
import tn.esprit.entities.events.projectmembers.ProjectMembers;
import tn.esprit.tools.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ProjectMembersService {
    Connection cnx = MyDB.getInstance().getCnx();
    public void ajouterMembre(ProjectMembers projectMembers) {
        String sql = "INSERT INTO project_members (id,project_id,is_owner,joined_at) VALUES (?, ?,??)";
        long millis=System.currentTimeMillis();
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectMembers.getId());
            statement.setInt(2, projectMembers.getProjectId());
            statement.setBoolean(3, projectMembers.getIsOwner());
            statement.setDate(4, new java.sql.Date(millis));
            statement.executeUpdate();
            System.out.println("Project Added : " + projectMembers.getId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
    public void modifierMember(ProjectMembers projectMembers) {
        String sql = "UPDATE project_members SET id = ? WHERE  id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectMembers.getId());
            statement.executeUpdate();
            System.out.println("member Updated!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(int projectMembers) {
        String sql = "DELETE FROM project_members WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectMembers);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("member deleted successfully");
            } else {
                System.out.println("No member found for user ID: " + projectMembers);
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<ProjectMembers> display() {
        List<ProjectMembers> members = new ArrayList<>();
        String sql = "SELECT * FROM project_members";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                ProjectMembers projectMembers = new ProjectMembers();
                projectMembers.setId(rs.getInt("id"));
                projectMembers.setProjectId(rs.getInt("project_id"));
                projectMembers.setIsOwner(rs.getBoolean("is_owner"));
                projectMembers.setJoinedAt(rs.getDate("joined_at"));

                members.add(projectMembers);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return members;
    }
}

