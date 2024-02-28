package tn.esprit.services.coursServices;

import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.tools.MyDB;
//import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationService {
        private Connection conn;
        private Statement ste;
        private PreparedStatement pst;

        public EvaluationService () {
            conn = MyDB.getInstance().getCnx();
        }
    public void ajouter(Evaluation evaluation) {
        String requete = "INSERT INTO evaluation (cours_id,titre) VALUES (?, ?)";
        System.out.println("ajouter service :"+evaluation.getTitre());
        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, evaluation.getCours_Id());
            pst.setString(2, evaluation.getTitre());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                evaluation.setId(generatedKeys.getInt(1));
            }
            System.out.println("Reaction Added for Evaluation ID: " + evaluation.getCours_Id());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
    public void modifierevaluation(Evaluation evaluation) {
        String requete = "UPDATE evaluation SET titre=?  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, evaluation.getTitre());
            pst.setInt(2, evaluation.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimerevaluation(int id) {
        String requete = "DELETE FROM evaluation WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Evaluation> obtenirToutesLesevaluation() {
        String requete = "SELECT id, cours_id,titre  FROM evaluation";
        List<Evaluation> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Evaluation(rs.getInt("id"), rs.getInt("cours_id"), rs.getString("titre"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Evaluation obtenirevaluation(int id) {
        String requete = "SELECT id,cours_id,titre FROM evaluation  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Evaluation(rs.getInt("id"), rs.getInt("cours_id"), rs.getString("titre")) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Evaluation> getEvaluationAndQuestionsAndOptions(){
        String requete = "SELECT * from evaluation,question , option where evaluation.id=question.evaluation_id and option.question_id=question.id";
        List<Evaluation> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                //
                list.add(new Evaluation(rs.getInt("id"), rs.getInt("cours_id"), rs.getString("titre"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public Boolean SaveResponseEvaluation(int evaluation_id, int userid){
        String requete="insert into evaluation_user (user_id,evaluation_id) values(?,?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1,userid);
            pst.setInt(2,evaluation_id);
            return pst.executeUpdate()!=0;

        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
        return  false;

    }
    public Boolean getIfUserAlreadySubmitEvaluation(int evaluation_id, int userid){
        String requete="select *  from evaluation_user where user_id=? and evaluation_id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1,userid);
            pst.setInt(2,evaluation_id);
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
