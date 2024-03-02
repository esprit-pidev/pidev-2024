package tn.esprit.services.stageServices;

import tn.esprit.entities.User.Entreprise;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService {

    Connection cnx;
    private final UserService us = new UserService();

    public OffreService() {
        cnx = MyDB.getInstance().getCnx();
    }

    public boolean existeOffreAvecTitre(String titre) {
        List<Offre> offres = getAllOffres();
        for (Offre offre : offres) {
            if (offre.getTitre().equalsIgnoreCase(titre)) {
                return true;
            }
        }
        return false;
    }

    public void ajouter(Offre O) {
        String sql = "insert into offre (entreprise_id, titre, description, competences, nbr, date) values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, O.getEntreprise().getId());
            statement.setString(2, O.getTitre());
            statement.setString(3, O.getDescription());
            statement.setString(4, O.getCompetences());
            statement.setInt(5, O.getNbr());
            statement.setDate(6, new java.sql.Date(O.getDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifier(Offre O) {
        String sql = "update offre set  entreprise_id=?, titre=?, description=?, competences=?, nbr=?, date=? where id=?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, O.getEntreprise().getId());
            statement.setString(2, O.getTitre());
            statement.setString(3, O.getDescription());
            statement.setString(4, O.getCompetences());
            statement.setInt(5, O.getNbr());
            statement.setDate(6, new java.sql.Date(O.getDate().getTime()));
            statement.setInt(7, O.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM offre WHERE id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Offre> afficher() {
        List<Offre> OffreList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `offre`";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Offre O = new Offre();
                O.setId(rs.getInt("id"));
                Entreprise entreprise = (Entreprise) us.getById(rs.getInt("entreprise_id"));
                O.setEntreprise(entreprise);
                O.setTitre(rs.getString("titre"));
                O.setDescription(rs.getString("description"));
                O.setCompetences(rs.getString("competences"));
                O.setNbr(rs.getInt("nbr"));
                O.setDate(rs.getDate("date"));
                OffreList.add(O);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return OffreList;
    }


    public Offre getById(int id) {
        try {
            String sql = "select * from offre where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offre offre = new Offre(rs.getInt("id"),
                        (Entreprise) us.getById(rs.getInt("entreprise_id")),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("competences"),
                        rs.getInt("nbr"),
                        rs.getDate("date")
                );
                return offre;
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return null;
    }
    public static List<Offre> getOffresByOffreId(int offreId) {
        List<Offre> offres = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("url_de_votre_base_de_donnees")) {
            String sql = "SELECT * FROM offre WHERE offre_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, offreId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Offre offre = new Offre();
                        offre.setId(resultSet.getInt("offre_id"));
                        // Récupérer les autres attributs de l'offre
                        offres.add(offre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }


    public Offre getByEntreprise_id(int entreprise_id) {
        try {
            String sql = "select * from offre where entreprise_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, entreprise_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offre offre = new Offre(rs.getInt("id"),
                        (Entreprise) us.getById(rs.getInt("entreprise_id")),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("competences"),
                        rs.getInt("nbr"),
                        rs.getDate("date")
                );
                return offre;
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return null;
    }

    public List<Offre> getAllOffres() {
        List<Offre> offres = new ArrayList<>();
        try {
            String sql = "SELECT * FROM offre";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Offre offre = new Offre(rs.getInt("id"),
                        (Entreprise) us.getById(rs.getInt("entreprise_id")),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("competences"),
                        rs.getInt("nbr"),
                        rs.getDate("date")
                );
                offres.add(offre);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return offres;
    }
    public List<Offre> searchByTitre(String filter) {
        List<Offre> filteredOffres = new ArrayList<>();
        String sql = "SELECT * FROM offre WHERE titre LIKE ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, filter + "%"); // Utilisation du filtre avec le joker % pour rechercher les titres commençant par le filtre
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setId(resultSet.getInt("id"));
                offre.setTitre(resultSet.getString("titre"));
                offre.setCompetences(resultSet.getString("competences"));
                offre.setDescription(resultSet.getString("description"));
                offre.setNbr(resultSet.getInt("nbr"));
                offre.setDate(resultSet.getDate("date"));
                filteredOffres.add(offre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche par titre : " + e.getMessage());
        }

        return filteredOffres;
    }



}
