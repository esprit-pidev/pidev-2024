package tn.esprit.services.projectService;

import tn.esprit.entities.project.ProjectMembers;
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
        String query = "INSERT INTO tache (idMember, etat, description, date_ajout, dedline) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, tache.getIdProjectMembers());
            statement.setString(2, tache.getEtat());
            statement.setString(3, tache.getDescription());
            statement.setDate(4, new java.sql.Date(tache.getDate_ajout().getTime()));
            statement.setDate(5, new java.sql.Date(tache.getDedline().getTime()));
            statement.executeUpdate();
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour mettre à jour une tâche
    public void updateTache(Taches tache) {
        String query = "UPDATE tache SET idMember = ?, etat = ?, description = ?, date_ajout = ?, dedline = ? WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, tache.getProjectMembers().getId());
            statement.setString(2, tache.getEtat());
            statement.setString(3, tache.getDescription());
            statement.setDate(4, new java.sql.Date(tache.getDate_ajout().getTime()));
            statement.setDate(5, new java.sql.Date(tache.getDedline().getTime()));
            statement.setInt(6, tache.getId());
            statement.executeUpdate();
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    // Méthode pour supprimer une tâche
    public void deleteTache(int id) {
        String query = "DELETE FROM tache WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException var4) {
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
                int projectMemberId = resultSet.getInt("id_Member");
                String etat = resultSet.getString("etat");
                String description = resultSet.getString("description");
                Date date_ajout = resultSet.getDate("date_ajout");
                Date dedline = resultSet.getDate("dedline");

                ProjectMembers projectMembers = new ProjectMembers(); // Création d'un utilisateur temporaire avec juste l'ID
                projectMembers.setId(projectMemberId);

                Taches tache = new Taches(id, projectMembers, etat, description, date_ajout, dedline);
                tachesList.add(tache);
            }
        }
        return tachesList;
    }

    public List<Taches> getTacheByIdAndUserIdAndProjectId(int x, int y) {
        List<Taches> taches = new ArrayList<>();
        String query = "SELECT t.id , t.idMember,t.etat , t.description , t.date_ajout , t.dedline, t.idMember FROM tache t " +
                "JOIN project_members pm ON t.idMember = pm.id " +
                "JOIN project_members_user pmu ON pm.id = pmu.project_members_id " +
                "WHERE pmu.project_members_id = ? AND pm.project_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, x);
            statement.setInt(2,y );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Taches tache = new Taches();
                tache.setId(resultSet.getInt("id"));
                tache.setEtat(resultSet.getString("etat"));
                tache.setDescription(resultSet.getString("description"));
                tache.setDedline(resultSet.getDate("dedline"));
                tache.setDate_ajout(resultSet.getDate("date_ajout"));
                taches.add(tache);
                System.out.println(tache.getIdProjectMembers() + "w" + tache.getDate_ajout() + "+" + tache.getDescription());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return taches;
    }

}



