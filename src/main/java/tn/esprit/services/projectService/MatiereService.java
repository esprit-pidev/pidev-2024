package tn.esprit.services.projectService;

import tn.esprit.entities.project.Matiere;
import tn.esprit.tools.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MatiereService {

    Connection cnx = MyDB.getInstance().getCnx();

    public void addMatiere(Matiere matiere) {
        String query = "INSERT INTO matiere (niveau, nom) VALUES (?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, matiere.getNiveau());
            statement.setString(2, matiere.getNom());
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour mettre à jour une matière
    public void updateMatiere(Matiere matiere) {
        String query = "UPDATE matiere SET nom = ? WHERE niveau = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, matiere.getNom());
            statement.setString(2, matiere.getNiveau());
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour supprimer une matière
    public void deleteMatiere(String niveau) {
        String query = "DELETE FROM matiere WHERE niveau = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, niveau);
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour récupérer une matière par son niveau
    public Matiere getMatiereByNiveau(String niveau) throws SQLException {
        String query = "SELECT * FROM matiere WHERE niveau = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, niveau);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Matiere(resultSet.getString("niveau"), resultSet.getString("nom"));
                }
            }
        }
        return null;
    }
}
