package tn.esprit.services.coursServices;

import tn.esprit.entities.Cours.Questions;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
        private Connection conn;
        private Statement ste;
        private PreparedStatement pst;

        public QuestionService() {
            this.conn = MyDB.getInstance().getCnx();
        }
    public void ajouter(Questions questions) {
        String requete = "INSERT INTO question (evaluation_id,valeur) VALUES (?, ?)";

        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, questions.getEvaluation_id());
            pst.setString(2, questions.getValeur());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                questions.setId(generatedKeys.getInt(1));
            }
            System.out.println("Reaction Added for Event ID: " + questions.getId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
    public Questions ajouterWithRetunQuestion(Questions questions) {
        String requete = "INSERT INTO question (evaluation_id, valeur) VALUES (?, ?)";

        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, questions.getEvaluation_id());
            pst.setString(2, questions.getValeur());
            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();

            if (generatedKeys.next()) {
                questions.setId(generatedKeys.getInt(1));
                System.out.println("Question Added with ID: " + questions.getId());
            }

        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }

        return questions;
    }
    public void modifierquestions(Questions questions) {
        String requete = "UPDATE question SET valeur=?  WHERE id=1";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, questions.getValeur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimerquestions(int id) {
        String requete = "DELETE FROM question WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Questions> obtenirToutesLesevaluation() {
        String requete = "SELECT id, evaluation_id,valeur  FROM question";
        List<Questions> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Questions(rs.getInt("id"), rs.getInt("evaluation_id"), rs.getString("valeur"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Questions obtenirquestion(int id) {
        String requete = "SELECT id,evaluation_id,valeur FROM question  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Questions(rs.getInt("id"), rs.getInt("evaluation_id"), rs.getString("valeur")) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<Questions> getQuestionByEvaluation(int evaluationId) {
        String requete = "SELECT id, evaluation_id,valeur  FROM question where evaluation_id=?";
        List<Questions> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1,evaluationId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Questions(rs.getInt("id"), rs.getInt("evaluation_id"), rs.getString("valeur"))) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
