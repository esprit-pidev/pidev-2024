package tn.esprit.services.projectService;

import tn.esprit.entities.User.User;
import tn.esprit.entities.project.Project;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectService {
    Connection cnx = MyDB.getInstance().getCnx();


    public void ajouter(Project project) {
        String sql = "INSERT INTO project (teacher_id, classe, matiere, nom, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        long millis = System.currentTimeMillis();
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, project.getIdUser());
            statement.setString(2, project.getClasse());
            statement.setString(3, project.getMatiere());
            statement.setString(4, project.getNom());
            statement.setString(5, project.getDescription());
            statement.setDate(6, new java.sql.Date(millis));
            statement.setDate(7, new java.sql.Date(millis));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Project Added successfully");
            } else {
                System.out.println("Failed to add project");
            }
        } catch (SQLException e) {
            System.out.println("Error adding project: " + e.getMessage());
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

    public void supprimer(int id) {
        String sql = "DELETE FROM project WHERE  id =?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Project deleted successfully");
            } else {
                System.out.println("No project found for project ID: " + id);
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
            cnx.setAutoCommit(true);

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setUser((new User()));
                project.setIdUser(rs.getInt("teacher_id"));
                project.setClasse(rs.getString("classe"));
                project.setMatiere(rs.getString("matiere"));
                project.setNom(rs.getString("nom"));
                project.setDescription(rs.getString("description"));
                project.setCreated_at(rs.getDate("created_at"));
                project.setUpdated_at(rs.getDate("updated_at"));
                projects.toString();
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return projects;
    }

    public List<String> displayClasses() {
        List<String> classes = new ArrayList<>();
        String sql = "SELECT DISTINCT classe FROM project";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String classe = rs.getString("classe");
                classes.add(classe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public List<Project> displayProjectsInfo(int teacherId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT nom, classe, updated_at, id FROM project WHERE teacher_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, teacherId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setNom(rs.getString("nom"));
                project.setClasse(rs.getString("classe"));
                project.setUpdated_at(rs.getDate("updated_at"));
                project.setId(rs.getInt(("id")));
                System.out.println("ID du projet récupéré : " + project.getId());

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }


    public List<Project> getProjectsByClasse(String classe , int teacher_id) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM project WHERE classe = ? and teacher_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, classe);
            statement.setInt(2, teacher_id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setClasse(rs.getString("classe"));
                project.setNom(rs.getString("nom"));
                project.setUpdated_at(rs.getDate("updated_at"));
                project.setId(rs.getInt("id"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void modifierNomProjet(Project project) {

        String sql = "UPDATE project SET nom = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, project.getNom());
            statement.setInt(2, project.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Nom du projet mis à jour avec succès !");
            } else {
                System.out.println("Aucun projet trouvé avec l'ID : " + project.getId());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du nom du projet : " + e.getMessage());
        }
    }

    public void modifierClasseProjet(Project project) {

        String sql = "UPDATE project SET classe = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, project.getClasse());
            statement.setInt(2, project.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Classe mis à jour avec succès !");
            } else {
                System.out.println("Aucune classe trouvé avec l'ID : " + project.getId());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du classe : " + e.getMessage());
        }
    }
    public boolean matiereExists(String nomMatiere) {
        String query = "SELECT COUNT(*) FROM matiere WHERE matiere = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nomMatiere);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean projetExists(String nomProjet) {
        String query = "SELECT COUNT(*) AS count FROM project WHERE nom = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, nomProjet);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public int recupererIdParNom(String nomProjet) {
        String query = "SELECT id FROM project WHERE nom = ?";
        int idProjet = 0;
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setString(1, nomProjet);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idProjet = rs.getInt("id");
            } else {
                System.out.println("echec");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idProjet;
    }

    public List<String> getNameProjectsByUserId(int userId) {
        List<String> projectNames = new ArrayList<>();
        String sql = "SELECT p.nom FROM project p " +
                "INNER JOIN project_members pm ON p.id = pm.project_id " +
                "INNER JOIN project_members_user pmu ON pm.id = pmu.project_members_id " +
                "WHERE pmu.user_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String projectName = rs.getString("nom");
                projectNames.add(projectName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectNames;
    }
    public  int getIdMemberByIdUser(int idUser, int idProjet) {
        String query = "SELECT pm.id " +
                "FROM project_members pm " +
                "INNER JOIN project_members_user pmu ON pm.id = pmu.project_members_id " +
                "WHERE pm.project_id = ? " +
                "AND pmu.user_id = ?";
        int idMembre = 0;
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, idProjet);
            statement.setInt(2, idUser);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                idMembre = rs.getInt("id");
            } else {
                System.out.println("Aucun membre trouvé pour l'utilisateur avec l'ID : " + idUser + " et le projet avec l'ID : " + idProjet);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMembre;
    }

    public int getIdProjectByName(String projectName) {
        String query = "SELECT id FROM project WHERE nom = ?";
        int projectId =0;
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setString(1, projectName);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                projectId = rs.getInt("id");
            } else {
                System.out.println("Aucun projet trouvé avec ce nom : " + projectName );
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectId;
    }
}
