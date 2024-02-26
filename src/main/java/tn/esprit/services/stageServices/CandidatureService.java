package tn.esprit.services.stageServices;

import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;
public class CandidatureService {
    Connection cnx;

    public CandidatureService() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void ajouter(Candidature C) {
        String sql = "insert into candidature (offre_id, user_id, date, status, competences, cv) values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, C.getOffre_id());
            statement.setInt(2, C.getUser_id());
            statement.setDate(3, new java.sql.Date(C.getDate().getTime()));
            statement.setString(4, C.getStatus());
            statement.setString(5, C.getCompetences());
            statement.setString(6, C.getCv());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void modifier(Candidature C) {
        String sql = "update candidature set offre_id=?, user_id=?, date=?, status=?, competences=?, cv=? where id=?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, C.getOffre_id());
            statement.setInt(2, C.getUser_id());
            statement.setDate(3, new java.sql.Date(C.getDate().getTime()));
            statement.setString(4, C.getStatus());
            statement.setString(5, C.getCompetences());
            statement.setString(6, C.getCv());
            statement.setInt(7, C.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    public void modifier(Candidature C) {
        String sql = "update candidature set offre_id=?, user_id=?, date=?, status=?, competences=?, cv=? where id=?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, C.getOffre_id());
            statement.setInt(2, C.getUser_id());
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
        String sql = "DELETE FROM offre WHERE id = ?";
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
            String sql = "SELECT * FROM `candidature`";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Candidature C = new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                C.setUser_id(rs.getInt("user_id"));
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

    public Candidature setCandidature(int id) {
        try{
            String sql= "select * from offre where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Candidature C=new Candidature();
                C.setId(rs.getInt("id"));
                C.setOffre_id(rs.getInt("offre_id"));
                C.setUser_id(rs.getInt("user_id"));
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                C.setCv(rs.getString("cv"));
                return C;
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }

    public Candidature getById(int id) {
        try{
            String sql= "select * from Candidature where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Candidature C = new Candidature(rs.getInt("id"),
                        rs.getInt("offre_id"),
                        rs.getInt("user_id"),
                        rs.getDate("date"),
                        rs.getString("status"),
                        rs.getString("competences"),
                        rs.getString("cv"));
                return new Candidature();
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }

    public Candidature getByoffre_id(int offre_id) {
        try{
            String sql= "select * from offre where offre_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,offre_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Offre offre = new Offre(rs.getInt("id"),
                        rs.getInt("entreprise_id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("competences"),
                        rs.getInt("nbr"),
                        rs.getDate("date")
                );
                return new Candidature();
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }


    public List<Candidature> getAllOffres() {
        List<Candidature> CandidatureList=new ArrayList<>();
        try{
            String sql="SELECT * FROM candidature;";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Candidature C=new Candidature();
                //C.setId(rs.getInt("id"));
                //C.setOffre_Id(rs.getInt("offre_id"));
                //C.setUser_Id(rs.getInt("user_id"));
                C.setDate(rs.getDate("date"));
                C.setStatus(rs.getString("status"));
                C.setCompetences(rs.getString("competences"));
                //C.setCv(rs.getString("cv"));
                CandidatureList.add(C);
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return CandidatureList;

    }


}
