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
            // Vérification de la validité de l'ID du projet
            if (projectMembers.getIdProject() <= 0) {
                System.out.println("L'ID du projet n'est pas valide.");
                return;
            }
            statement.setInt(1, projectMembers.getIdProject());
            // Utilisation de Timestamp pour les colonnes de type TIMESTAMP
            statement.setTimestamp(2, new java.sql.Timestamp(millis));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Membre ajouté avec succès.");
            } else {
                System.out.println("Échec de l'ajout du membre.");
            }
        } catch (SQLException e) {
            // Gestion des exceptions
            System.out.println("Erreur lors de l'ajout du membre : " + e.getMessage());

        }
    }

    public int getLAstId(String nom) {
        String sql = "SELECT pm.id AS project_member_id " +
                "FROM project_members pm " +
                "JOIN project p ON pm.project_id = p.id " +
                "WHERE p.nom = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int projectMemberId = resultSet.getInt("project_member_id");
                System.out.println("Project member ID for project '" + nom + "': " + projectMemberId);
                return projectMemberId;
            } else {
                System.out.println("No project member found for project name: " + nom);
                return -1; // ou tout autre valeur par défaut appropriée
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving project member ID: " + e.getMessage());
            return -1; // ou tout autre valeur par défaut appropriée
        }
    }
}


