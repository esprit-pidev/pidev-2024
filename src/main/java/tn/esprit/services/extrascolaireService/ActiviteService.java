package tn.esprit.services.extrascolaireService;

import tn.esprit.entities.extrascolaire.Activite;
import tn.esprit.tools.MyDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class ActiviteService {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public ActiviteService() {
        conn = MyDB.getInstance().getCnx();
    }

    public List<Activite> obtenirToutesLesActivites() {
        String requete = "SELECT id, nom, description FROM activite";
        List<Activite> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Activite(rs.getInt("id"), rs.getString("nom"), rs.getString("description")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Activite obtenirActivite(int activiteID) {
        String requete = "SELECT id, nom, description FROM activite WHERE id=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, activiteID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Activite(rs.getInt("id"), rs.getString("nom"), rs.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}


