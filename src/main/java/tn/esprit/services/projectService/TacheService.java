package tn.esprit.services.projectService;

import tn.esprit.entities.project.Taches;
import tn.esprit.entities.User.User;
import tn.esprit.tools.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheService {
    Connection cnx = MyDB.getInstance().getCnx();


    // Méthode pour ajouter une tâche
    public void addTache(Taches tache) {
        String query = "INSERT INTO tache (iduser, etat, description, date_ajout, dedline) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, tache.getIdUser());
            statement.setString(2, tache.getEtat());
            statement.setString(3, tache.getDescription());
            statement.setDate(4, new java.sql.Date(tache.getDate_ajout().getTime()));
            statement.setDate(5, new java.sql.Date(tache.getDedline().getTime()));
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour mettre à jour une tâche
    public void updateTache(Taches tache)  {
        String query = "UPDATE tache SET iduser = ?, etat = ?, description = ?, date_ajout = ?, dedline = ? WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, tache.getUser().getId());
            statement.setString(2, tache.getEtat());
            statement.setString(3, tache.getDescription());
            statement.setDate(4, new java.sql.Date(tache.getDate_ajout().getTime()));
            statement.setDate(5, new java.sql.Date(tache.getDedline().getTime()));
            statement.setInt(6, tache.getId());
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour supprimer une tâche
    public void deleteTache(int id)  {
        String query = "DELETE FROM tache WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour récupérer toutes les tâches
    public List<Taches> getAllTaches() throws SQLException {
        List<Taches> tachesList = new ArrayList<>();
        String query = "SELECT * FROM tache";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("id_user");
                String etat = resultSet.getString("etat");
                String description = resultSet.getString("description");
                Date date_ajout = resultSet.getDate("date_ajout");
                Date dedline = resultSet.getDate("dedline");

                User user = new User(); // Création d'un utilisateur temporaire avec juste l'ID
                user.setId(userId);

                Taches tache = new Taches(id, user, etat, description, date_ajout, dedline);
                tachesList.add(tache);
            }
        }
        return tachesList;
    }

    // Méthode pour récupérer une tâche par son ID
    public Taches getTacheById(int id) throws SQLException {
        String query = "SELECT * FROM tache WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id_user");
                    String etat = resultSet.getString("etat");
                    String description = resultSet.getString("description");
                    Date date_ajout = resultSet.getDate("date_ajout");
                    Date dedline = resultSet.getDate("dedline");

                    User user = new User(); // Création d'un utilisateur temporaire avec juste l'ID
                    user.setId(userId);

                    return new Taches(id, user, etat, description, date_ajout, dedline);
                }
            }
        }
        return null;
    }
}
