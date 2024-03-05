package tn.esprit.services.extrascolaireService;

import tn.esprit.entities.extrascolaire.Paiement;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.Date;

public class PaiementService {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public PaiementService() {
        conn = MyDB.getInstance().getCnx();
    }

    public Paiement effectuerPaiement(int inscription_id, double montant) {
        String requete = "INSERT INTO paiement (inscription_id, montant, date) VALUES (?, ?, NOW())";
        try {
            pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, inscription_id);
            pst.setDouble(2, montant);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                Paiement paiement = new Paiement();
                paiement.setId(rs.getInt(1));
                paiement.setInscription_id(inscription_id);
                paiement.setMontant(montant);
                // Utilisez java.util.Date pour la date actuelle
                paiement.setDate(new Date());
                return paiement;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }





}
