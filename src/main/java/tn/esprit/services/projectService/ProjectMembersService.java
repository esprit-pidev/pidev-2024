package tn.esprit.services.projectService;

import tn.esprit.entities.project.ProjectMembers;
import tn.esprit.tools.MyDB;

import java.sql.*;

public class ProjectMembersService {
    Connection cnx = MyDB.getInstance().getCnx();

    public void ajouterMembre(ProjectMembers projectMembers) {
        String sql = "INSERT INTO project_members (project_id, joined_at) VALUES (?, ?)";
        long millis = System.currentTimeMillis();
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectMembers.getIdProject());
            statement.setDate(2, new java.sql.Date(millis));
            statement.executeUpdate();
            System.out.println("Member Added : " + projectMembers.getId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
}

