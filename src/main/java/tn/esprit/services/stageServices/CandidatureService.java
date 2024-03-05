package tn.esprit.services.stageServices;

import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.User;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatureService {
    Connection cnx;
    private final UserService us = new UserService();

    public CandidatureService() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void ajouter(int offre_id, Etudiant etudiant, Date date, String status, String competences, String cv) {
        String sql = "insert into candidature (offre_id, user_id, date, status, competences, cv) values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, offre_id);
            statement.setInt(2, etudiant.getId());
            statement.setDate(3, new java.sql.Date(date.getTime()));
            statement.setString(4, status);
            statement.setString(5, competences);
            statement.setString(6, cv);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifier(Candidature C) {
        String sql = "update candidature set offre_id=?, user_id=?, date=?, status=?, competences=?, cv=? where id=?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, C.getOffre_id());
            statement.setInt(2, C.getEtudiant().getId());
            statement.setDate(3, new java.sql.Date(C.getDate().getTime()));
            statement.setString(4, C.getStatus());
            statement.setString(5, C.getCompetences());
            statement.setString(6, C.getCv());
            statement.setInt(7, C.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM candidature WHERE id = ?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Candidature> afficher() {
        List<Candidature> CandidatureList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM candidature";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Candidature C = new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                User etudiant = new User(); // Vous devez obtenir l'étudiant à partir de son ID
                C.setEtudiant(etudiant);
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                C.setCv(rs.getString("cv"));
                CandidatureList.add(C);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return CandidatureList;
    }

    public List<Candidature> afficherparoffre(Offre offre) {
        List<Candidature> CandidatureList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `candidature` where offre_id= ?";
            PreparedStatement ste=this.cnx.prepareStatement(sql);
            ste.setInt(1,offre.getId());
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Candidature C = new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                User etudiant = new User(); // Vous devez obtenir l'étudiant à partir de son ID
                C.setEtudiant(etudiant);
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                C.setCv(rs.getString("cv"));
                CandidatureList.add(C);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return CandidatureList;
    }
    public List<Candidature> getCandidaturesByOffre(Offre offre) {
        List<Candidature> candidatureList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM candidature WHERE offre_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, offre.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidature candidature = new Candidature();
                candidature.setId(rs.getInt("id"));
                candidature.setOffre_id(rs.getInt("offre_id"));
                candidature.setDate(rs.getDate("date"));
                candidature.setStatus(rs.getString("status"));
                candidature.setCompetences(rs.getString("competences"));
                candidature.setCv(rs.getString("cv"));
                candidatureList.add(candidature);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des candidatures par offre: " + ex.getMessage());
        }
        return candidatureList;
    }
    public static List<Candidature> getOffresByOffreId(int offreId) {
        List<Candidature> candidatures = new ArrayList<>();
        try (Connection connection = MyDB.getInstance().getCnx()) {
            String sql = "SELECT * FROM candidature WHERE offre_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, offreId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Candidature candidature = new Candidature();
                        candidature.setId(resultSet.getInt("id"));
                        // Récupérer les autres attributs de la candidature
                        candidatures.add(candidature);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidatures;
    }


    public Candidature getById(int id) {
        try {
            String sql = "select * from candidature where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidature C = new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                User etudiant = new User(); // Vous devez obtenir l'étudiant à partir de son ID
                C.setEtudiant(etudiant);
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                C.setCv(rs.getString("cv"));
                return C;
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return null;
    }

    public List<Candidature> getByOffreId(int offre_id) {
        List<Candidature> CandidatureList = new ArrayList<>();
        try {
            String sql = "select * from candidature where offre_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, offre_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Candidature C = new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                User etudiant = new User(); // Vous devez obtenir l'étudiant à partir de son ID
                C.setEtudiant(etudiant);
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                C.setCv(rs.getString("cv"));
                CandidatureList.add(C);
            }
        } catch (SQLException ex) {
            System.out.println("erreur:" + ex.getMessage());
        }
        return CandidatureList;
    }
}
