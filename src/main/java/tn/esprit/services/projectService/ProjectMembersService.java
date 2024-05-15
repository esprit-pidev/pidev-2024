package tn.esprit.services.projectService;

import tn.esprit.entities.User.RoleName;
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
            statement.setTimestamp(2, new Timestamp(millis));
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
    public int getIdMemberByIduser(int idUser , int proejctId) {
        String sql = "SELECT pm.id FROM project_members pm JOIN project_members_user pmu ON pmu.project_members_id = pm.id WHERE pm.project_id =? AND pmu.user_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, idUser);
            statement.setInt(2, proejctId);
            ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error while retrieving project member ID: " + e.getMessage());
        }
        return 0;
    }

    public int getLAstId(int idp) {
        String sql = "SELECT id FROM project_members WHERE project_id = ? ORDER BY id DESC LIMIT 1"; // Sélectionnez le dernier ID inséré pour le projet spécifié
        int lastId = 0;

        try {
            Connection connection = MyDB.getInstance().getCnx();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idp);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lastId = resultSet.getInt("id"); // Récupérer l'ID du dernier membre inséré pour le projet spécifié
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId;
    }
    public String getNomTbyProjectId(int projectId) {
        String nomMembre = null; // Initialisez à null ou toute autre valeur par défaut appropriée

        String sql = "SELECT DISTINCT u.nom\n" +
                "FROM project_members pm\n" +
                "JOIN project_members_user pmu ON pm.id = pmu.project_members_id\n" +
                "JOIN user u ON u.role = \"STUDENT\"\n" +
                "WHERE pm.project_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nomMembre = resultSet.getString("nom"); // Récupérez le nom du membre
            } else {
                System.out.println("Aucun membre trouvé pour le projet avec l'ID " + projectId);
                // ou attribuez une valeur par défaut appropriée à nomMembre
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nom du membre du projet : " + e.getMessage());
            // ou attribuez une valeur par défaut appropriée à nomMembre
        }

        return nomMembre;
    }
    public String getNombyIdUser(int userId) {
        String userName = null;
        String query = "SELECT u.nom " +
                "FROM user u " +

                "WHERE id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions SQL de manière appropriée dans ton application
        }
        return userName;
    }


    public int getIdUserbyIdMember(int idMembre) {
        int userid = 0;
        String query = "SELECT user_id " +
                "FROM  project_members_user " +
                "WHERE project_members_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, idMembre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userid = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions SQL de manière appropriée dans ton application
        }
        return userid;

    }
    public boolean isStudentByEmail(String email) {
        try {
            String sql = "SELECT * FROM user WHERE email = ? AND role = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, String.valueOf(RoleName.STUDENT));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isExist(String email){
        String sql = "SELECT COUNT(*) FROM user WHERE email = ? AND role = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, "STUDENT"); // Vérifiez que le rôle est "étudiant"
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public int getIdStudent(String email) {
        try {
            String sql = "SELECT id FROM user WHERE email = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID de l'utilisateur : " + e.getMessage());
        }
        return -1; // Retourne -1 si l'e-mail n'a pas été trouvé ou s'il y a eu une erreur
    }

}


