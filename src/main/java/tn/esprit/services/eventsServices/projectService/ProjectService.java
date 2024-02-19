package tn.esprit.services.eventsServices.projectService;
import tn.esprit.entities.events.project.Project;
import tn.esprit.tools.MyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ProjectService {
    Connection cnx = MyDB.getInstance().getCnx();


    public void ajouter(Project project) {
        String sql = "INSERT INTO project (teacher_id,classe,matiere,nom,description,created_at,updated_at) VALUES (?,?,?,?,?,?,?)";
        long millis=System.currentTimeMillis();
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, project.getTeacherId());
            statement.setString(2, project.getNom());
            statement.setString(3, project.getMatiere());
            statement.setString(4, project.getClasse());
            statement.setString(5, project.getDescription());
            statement.setDate(6, new java.sql.Date(millis));
            statement.setDate(7, new java.sql.Date(millis));
            statement.executeUpdate();
            System.out.println("Project Added : ");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(Project project) {
        String sql = "UPDATE project SET classe = ?, matiere = ? ,  nom = ? , description = ?  WHERE  id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, project.getClasse());
            statement.setString(2, project.getMatiere());
            statement.setString(3, project.getNom());
            statement.setString(4, project.getDescription());
            statement.setInt(5, project.getId());
            statement.executeUpdate();
            System.out.println("project Updated!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(Project project) {
        String sql = "DELETE FROM project WHERE  id = ? Or nom =?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, project.getId());
            statement.setString(2, project.getNom());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Project deleted successfully");
            } else {
                System.out.println("No project found for project ID: " + project.getId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<Project> display() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setTeacherId(rs.getInt("teacher_id"));
                project.setClasse(rs.getString("classe"));
                project.setMatiere(rs.getString("matiere"));
                project.setNom(rs.getString("nom"));
                project.setDescription(rs.getString("description"));
                project.setCreatedAt(rs.getDate("created_at"));
                project.setUpdatedAt(rs.getDate("updated_at"));
                projects.toString();
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return projects;
    }
}
