package service;

import entities.Option;
import entities.Questions;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionService {
        private Connection conn;
        private Statement ste;
        private PreparedStatement pst;

        public OptionService() {
            conn = DataSource.getInstance().getCnx();
        }
    public void ajouteroption(Option option) {
        String requete = "INSERT INTO option (question_id,valeur,is_correct) VALUES (?, ?, ?)";

        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, option.getQuestion_id());
            pst.setString(2, option.getValeur());
            pst.setBoolean(3, option.getIs_correct());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                option.setId(generatedKeys.getInt(1));
            }
            System.out.println("Reaction Added for option ID: " + option.getId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
    public void modifieroptions(Option option) {
        String requete = "UPDATE option SET valeur=?  WHERE id=1";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, option.getValeur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimeroption(int id) {
        String requete = "DELETE FROM option WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Option> obtenirToutesLesoptions() {
        String requete = "SELECT id, question_id,valeur,is_correct  FROM option";
        List<Option> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getInt("question_id"), rs.getString("valeur"), rs.getBoolean("is_correct"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Option> obtenirToutesLesoptionsByQuestion(int questionId) {
        String requete = "SELECT id, question_id,valeur,is_correct  FROM option where question_id=?";

        List<Option> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, questionId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getInt("question_id"), rs.getString("valeur"), rs.getBoolean("is_correct"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Option obteniroption(int id) {
        String requete = "SELECT id,question_id,valeur,is_correct FROM option  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Option(rs.getInt("id"), rs.getInt("question_id"), rs.getString("valeur"), rs.getBoolean("is_correct")) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public Boolean SaveResponse(int optionid, int userid){
            String requete="insert into option_user (user_id,option_id) values(?,?)";
            try {
                pst = conn.prepareStatement(requete);
                pst.setInt(1,userid);
                pst.setInt(2,optionid);
                return pst.executeUpdate()!=0;

            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }
            return  false;

    }

    public boolean isPickedOptionByUser(int option_id, int userId) {
        String requete="SELECT * from option_user where user_id=? and option_id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1,userId);
            pst.setInt(2,option_id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return true;
            }

        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
        return  false;

    }
}
