package tn.esprit.services.projectService;

import tn.esprit.entities.project.ProjectMembers;
import tn.esprit.entities.project.Taches;
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

    public void addTache(Taches tache) {
        String query = "INSERT INTO tache (idMember, etat, description, date_ajout, dedline ) VALUES ( ?, ?, ?,?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, tache.getIdProjectMembers());
            statement.setString(2, tache.getEtat());
            statement.setString(3, tache.getDescription());
            statement.setDate(4, new java.sql.Date(tache.getDate_ajout().getTime()));
            statement.setDate(5, new java.sql.Date(tache.getDedline().getTime()));


            // Débogage
            System.out.println("Requête SQL : " + statement.toString());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Nombre de lignes affectées : " + rowsAffected);



        } catch (SQLException var4) {
            System.out.println("Erreur lors de l'exécution de la requête : " + var4.getMessage());
            var4.printStackTrace();
        }
    }


    public void updateTache(Taches tache) {
        if (tache.getProjectMembers() != null) {
            String query = "UPDATE tache SET etat = ?, description = ?, date_ajout = ?, dedline = ? WHERE id = ?";
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
        } else {
            System.out.println("Erreur: Le membre de projet associé à la tâche est null.");
        }
    }

    public void updatedescded(String desc , Date ded ,int tacheId) {
        String query = "UPDATE tache SET description = ?, dedline = ? WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1,desc);
            statement.setDate(2, (java.sql.Date) ded);
            statement.setInt(3, tacheId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEtat(int tacheId, String newState) {
        String query = "UPDATE tache SET etat = ? WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, newState);
            statement.setInt(2, tacheId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public List<Taches> getAllTaches(int idProject , int idUser) throws SQLException {
        List<Taches> tachesList = new ArrayList<>();
        String query = "SELECT * FROM tache where ";
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
    public String[] getDescriptionAndDeadlineById(int id) {
        String[] descriptionAndDeadline = new String[2];
        String query = "SELECT description, dedline FROM tache WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    descriptionAndDeadline[0] = resultSet.getString("description");
                    descriptionAndDeadline[1] = resultSet.getString("dedline");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptionAndDeadline;
    }

    public List<Taches> getTacheByIdAndUserIdAndProjectId(int x, int y, int idUser) {
        List<Taches> taches = new ArrayList<>();
        String query = "SELECT * " +
                "FROM tache t " +
                "JOIN project_members pm ON t.idMember = pm.id " +
                "JOIN project_members_user pmu ON pm.id = pmu.project_members_id " +
                "WHERE pmu.project_members_id = ? AND pm.project_id = ? AND pmu.user_id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, idUser);

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




    public List<Taches> getTasksDueToday() throws SQLException {

        List<Taches> tachesList = new ArrayList<>();
        String query = "SELECT *\n" +
                "FROM taches\n" +
                "WHERE deadline >= CURRENT_DATE";
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

                Taches tache = new Taches(id, projectMembers, etat, description, date_ajout, dedline );
                tachesList.add(tache);
            }
        }
        return tachesList;
    }


    public List<Taches> getTacheByProjectId(int projectId, int userIdToExclude) {
        List<Taches> taches = new ArrayList<>();
        String query = "SELECT t.id, t.idMember, t.etat, t.description, t.date_ajout, t.dedline " +
                "FROM tache t " +
                "JOIN project_members pm ON t.idMember = pm.id " +
                "WHERE pm.project_id = ? AND pm.id != ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, projectId);
            statement.setInt(2, userIdToExclude);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Taches tache = new Taches();
                tache.setId(resultSet.getInt("id"));
                tache.setEtat(resultSet.getString("etat"));
                tache.setDescription(resultSet.getString("description"));
                tache.setDedline(resultSet.getDate("dedline"));
                tache.setDate_ajout(resultSet.getDate("date_ajout"));
                taches.add(tache);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taches;
    }

    public List<Integer> getAllTachesMember( int idProjet, int idUser) {
        List<Integer> members = new ArrayList<>();
        String query = "SELECT t.id " +
                "FROM tache t " +
                "JOIN project_members pm ON t.idMember = pm.id " +
                "WHERE pm.project_id = ? AND pm.id != ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, idProjet);
            statement.setInt(2, idUser);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                members.add(taskId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


    public List<Taches> getTacheByIdAndProjectId(int memberId, int projectId) {
        List<Taches> taches = new ArrayList<>();
        String query = "SELECT * " +
                "FROM tache t " +
                "JOIN project_members pm ON t.idMember = pm.id " +
                "JOIN project_members_user pmu ON pm.id = pmu.project_members_id " +
                "WHERE pmu.project_members_id = ? AND pm.project_id = ? ";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(query);
            statement.setInt(1, projectId);
            statement.setInt(2, memberId);
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



