package tn.esprit.services.projectService;

import tn.esprit.entities.project.fichier;
import tn.esprit.tools.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FichierService {
    Connection cnx = MyDB.getInstance().getCnx();


    public void ajouterFichier(fichier nouveauFichier) {
        String sql = "INSERT INTO fichier (nom, idMember, date_ajout, path) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setString(1, nouveauFichier.getNom());
            statement.setInt(2, nouveauFichier.getIdMember());
            statement.setDate(3, nouveauFichier.getDate_ajout());
            statement.setString(4, nouveauFichier.getPath());
            int rowsAffected = statement.executeUpdate(); // Utilisez executeUpdate() pour les requêtes d'insertion
            if (rowsAffected > 0) {
                System.out.println("Le fichier a été ajouté avec succès.");
            } else {
                System.out.println("Échec de l'ajout du fichier.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du fichier : " + e.getMessage());
        }
    }



    public void supprimerFichier(int idFichier) {
        String sql = "DELETE FROM fichier WHERE id = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, idFichier);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Fichier supprimé avec succès !");
            } else {
                System.out.println("Aucun fichier trouvé avec l'ID : " + idFichier);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du fichier : " + e.getMessage());
        }
    }

    public List<fichier> listerFichiers(int idProject) {
        List<fichier> fichiers = new ArrayList<>();
        String sql = "SELECT * FROM fichier f JOIN project_members pm ON pm.id = f.idMember WHERE pm.project_id =? ";
        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, idProject);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                fichier fichier = new fichier();
                fichier.setId(rs.getInt("id"));
                fichier.setNom(rs.getString("nom"));
                fichier.setIdMember(rs.getInt("idMember"));
                fichier.setDate_ajout(rs.getDate("date_ajout"));
                fichier.setPath(rs.getString("path"));
                fichiers.add(fichier);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des fichiers : " + e.getMessage());
        }
        return fichiers;
    }

    public String getNomByIdMember(int idMember) {
        String userName = null;
        String query = "select u.nom FROM user u JOIN project_members_user pmu on pmu.user_id = u.id WHERE pmu.project_members_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, idMember);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions SQL de manière appropriée dans ton application
        }
        return userName;
    }

    public List<fichier> getFileByUser(int idUser) {
        List<fichier> fichiers = new ArrayList<>();
        String sql = "SELECT * FROM fichier f JOIN project_members_user pmu on f.idMember = pmu.project_members_id JOIN user u ON u.id = pmu.user_id WHERE u.id = ?";

        try {
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                fichier file = new fichier();
                file.setId(rs.getInt("id"));
                file.setNom(rs.getString("nom"));
                file.setIdMember(rs.getInt("idMember"));
                file.setDate_ajout(rs.getDate("date_ajout"));
                file.setPath(rs.getString("path"));
                fichiers.add(file);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des fichiers : " + e.getMessage());
        }
        return fichiers;
    }

}
