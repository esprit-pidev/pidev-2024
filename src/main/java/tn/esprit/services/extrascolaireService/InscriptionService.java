package tn.esprit.services.extrascolaireService;

import tn.esprit.entities.extrascolaire.Inscription;
import tn.esprit.tools.MyDB;

import java.sql.*;

public class InscriptionService {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public InscriptionService() {
        conn = MyDB.getInstance().getCnx();
    }

    public Inscription ajouterInscription(Inscription inscription ) {
        String requete = "INSERT INTO inscription (id, etudiant_id, activite_id, date, nom, prenom, num_tel, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, inscription.getId());
            pst.setInt(2, inscription.getEtudiant_id());
            pst.setInt(3, inscription.getActivite_id());
            // Vérifier si la date est null
            if (inscription.getDate() != null) {
                pst.setDate(4, new Date(inscription.getDate().getTime()));
            } else {
                pst.setNull(4, Types.DATE);
            }
            pst.setString(5, inscription.getNom());
            pst.setString(6, inscription.getPrenom());
            pst.setString(7,inscription.getNum_tel());
            pst.setString(8,inscription.getEmail());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                inscription.setId(rs.getInt(1));
                return inscription;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void modifierInscription(Inscription inscription) {
        String requete = "UPDATE inscription SET  etudiant_id=?, activite_id=?, date=?, nom=?, prenom=?, num_tel=?, email=?  WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, inscription.getEtudiant_id());
            pst.setInt(2, inscription.getActivite_id());
            pst.setDate(3, new Date(inscription.getDate().getTime()));
            pst.setString(4, inscription.getNom());
            pst.setString(5, inscription.getPrenom());
            pst.setString(6,inscription.getNum_tel());
            pst.setString(7,inscription.getEmail());
            pst.setInt(8, inscription.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Inscription getInscriptionById(int inscriptionId) {
        String requete = "SELECT * FROM inscription WHERE id = ?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, inscriptionId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Inscription inscription = new Inscription();
                inscription.setId(rs.getInt("id"));
                inscription.setEtudiant_id( rs.getInt("etudiant_id"));
                inscription.setActivite_id(rs.getInt("activite_id"));
                inscription.setDate(rs.getDate("date"));
                inscription.setNom(rs.getString("nom"));
                inscription.setPrenom(rs.getString("prenom"));
                inscription.setNum_tel(rs.getString("num_tel"));
                inscription.setEmail(rs.getString("email"));

                return inscription;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

