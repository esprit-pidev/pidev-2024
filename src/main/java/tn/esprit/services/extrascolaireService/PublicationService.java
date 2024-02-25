package tn.esprit.services.extrascolaireService;

import tn.esprit.entities.extrascolaire.Publication;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PublicationService {

    private static Connection conn;
    private static Statement ste;
    private PreparedStatement pst;

    public PublicationService() {
        conn = MyDB.getInstance().getCnx();
    }

    public static List<String> obtenirTousLesContenusPublications() {
        Connection conn = MyDB.getInstance().getCnx();
        String requete = "SELECT contenu FROM publication";
        List<String> list = new ArrayList<>();
        try {
            Statement ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(rs.getString("contenu"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }





    public Publication ajouterPublication(Publication publication) {
        String requete = "INSERT INTO publication (id, club_rh_id, date, contenu) VALUES (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, publication.getId());
            pst.setInt(2, publication.getClub_rh_id());
            pst.setDate(3, new java.sql.Date(publication.getDate().getTime()));
            pst.setString(4, publication.getContenu());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                publication.setId(rs.getInt(1));
                return publication;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int obtenirIdPublicationParContenu(String contenu) {
        Connection conn = MyDB.getInstance().getCnx();
        String requete = "SELECT id FROM publication WHERE contenu = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setString(1, contenu);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Retournez une valeur par défaut si l'ID n'est pas trouvé (par exemple, -1)
    }


    public void modifierContenuPublication(int idPublication, String nouveauContenu) {
        String requete = "UPDATE publication SET contenu=? WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, nouveauContenu);
            pst.setInt(2, idPublication);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void supprimerPublication(int id) {
        String requete = "DELETE FROM publication WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
