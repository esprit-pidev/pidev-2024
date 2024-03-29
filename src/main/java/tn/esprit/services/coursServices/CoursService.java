package tn.esprit.services.coursServices;

import tn.esprit.entities.Cours.Cours;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CoursService {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    private final UserService us=new UserService();

    public CoursService() {
        conn = MyDB.getInstance().getCnx();
    }
    public void ajouter(Cours cours) {
        String requete = "INSERT INTO cours ( nom_cours, nom_module, teacher_id, niveau,coursURLpdf) VALUES (?, ?, ?, ?,?)";

        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, cours. getNomcours());
            pst.setString(2, cours.getNommodule());
            pst.setInt(3, cours.getTeacher().getId());
            pst.setInt(4, cours. getNiveau());
            pst.setString(5, cours. getcoursURLpdf());

            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                cours.setCours_Id(generatedKeys.getInt(1));
            }
            System.out.println("cour added to cours: " + cours.getCours_Id());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }
    public void modifier(Cours cours) {
        String requete = "UPDATE cours SET nom_cours=?,  nom_module=?, niveau=?,coursURLpdf=? WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, cours.getNomcours());
            pst.setString(2, cours.getNommodule());
            pst.setInt(3, cours.getNiveau());
            pst.setString(4, cours.getcoursURLpdf());
            pst.setInt(5, cours.getCours_Id());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimerCours(int coursId) {
        String requete = "DELETE FROM cours WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, coursId);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Cours> obtenirToutesLesCours() {
        String requete = "SELECT id, nom_cours,nom_module,teacher_id,niveau,coursURlpdf FROM cours";
        List<Cours> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Cours (rs.getInt("id"),rs.getString("nom_cours"), rs.getString("nom_module") ,us.getById(rs.getInt("teacher_id")),rs.getInt("niveau"),rs.getString("coursURLpdf")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Cours obtenirCours(int coursId) {
        String requete = "SELECT id,nom_cours,nom_module,teacher_id,niveau,coursURLpdf FROM cours  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, coursId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cours(rs.getString("nom_cours"), rs.getString("nom_module") ,us.getById(rs.getInt("teacher_id")),rs.getInt("niveau"),rs.getString("coursURLpdf"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Cours> getcourteacher(int id)throws SQLException {
        List<Cours> cours = new ArrayList<>();

        String sql = "SELECT * FROM cours WHERE teacher_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Cours c = new Cours();
                    c.setCours_Id(rs.getInt("id"));
                    Enseignant enseignant=(Enseignant) us.getById(rs.getInt("teacher_id"));
                    c.setTeacher(enseignant);
                    c.setNiveau(rs.getInt("niveau"));
                    c.setNomcours(rs.getString("nom_cours"));
                    c.setCoursURLpdf(rs.getString("coursURLpdf"));
                    c.setNommodule(rs.getString("nom_module"));
                    cours.add(c);
                }
            }
        }
        return cours;
    }

    public Evaluation getEvaluationByIdCours(int id)throws SQLException {
        CoursService cs = new CoursService();
        List<Evaluation> evals = new ArrayList<>();

        String sql = "SELECT * FROM evaluation WHERE cours_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Evaluation e = new Evaluation();
                    e.setId(rs.getInt("id"));
                    e.setCours_Id(rs.getInt("cours_id"));
                    e.setTitre(rs.getString("titre"));
                    return e;
                  //  evals.add(e);
                }
            }
        }
        return null;
    }

}








