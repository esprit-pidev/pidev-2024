package tn.esprit.services.stageServices;

import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;


public class OffreService {
    Connection cnx ;
    public OffreService(){
        cnx = MyDB.getInstance().getCnx();
    }
    public void ajouter (Offre O){
        String sql ="insert into offre (entreprise_id, titre, description, competences, nbr, date) values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, O.getEntreprise_id());
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
    public void modifier(Offre O){
        String sql ="update offre set  entreprise_id=?, titre=?, description=?, competences=?, nbr=?, date=? where id=?";
        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, O.getEntreprise_id());
            statement.setString(2, O.getTitre());
            statement.setString(3, O.getDescription());
            statement.setString(4, O.getCompetences());
            statement.setInt(5, O.getNbr());
            statement.setDate(6, new java.sql.Date(O.getDate().getTime()));
            statement.setInt(7,O.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimer (int id){
        String sql = "DELETE FROM offre WHERE id = ?";
        try  {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Offre> afficher() {
        List<Offre> OffreList=new ArrayList<>();
        try{
            String sql="SELECT * FROM `offre`";
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                Offre O=new Offre();
                O.setId(rs.getInt("id"));
                O.setEntreprise_id(rs.getInt("Entreprise_id"));
                O.setTitre(rs.getString("titre"));
                O.setDescription(rs.getString("description"));
                O.setCompetences(rs.getString("competences"));
                O.setNbr(rs.getInt("nbr"));
                O.setDate(rs.getDate("date"));
                OffreList.add(O);
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return OffreList;

    }


    public Offre getById(int id) {
        try{
            String sql= "select * from offre where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,id);
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
                return offre;
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }
    public Offre getByEntreprise_id(int entreprise_id) {
        try{
            String sql= "select * from offre where entreprise_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,entreprise_id);
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
                return offre;
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }

    public Offre setOffre(int entreprise_id) {
        try{
            String sql= "select * from offre where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,entreprise_id);
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
                return offre;
            }
        }catch (SQLException ex){
            System.out.println("erreur:"+ex.getMessage());
        }
        return null;

    }






}

