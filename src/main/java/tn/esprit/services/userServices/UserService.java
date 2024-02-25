package tn.esprit.services.userServices;


import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.*;
import tn.esprit.entities.User.Entreprise;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.ResponsableClub;
import tn.esprit.entities.User.User;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService<User> {

    Connection cnx;

    public UserService() {
        cnx = MyDB.getInstance().getCnx();
    }


    @Override
    public User getById(int id) {
        try {
            String sql = "select * from user where id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("role").equals("ADMIN")) {
                    Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"));
                    return admin;
                }
                if (rs.getString("role").equals("TEACHER")) {
                    Enseignant enseignant = new Enseignant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"),
                            rs.getString("cin"),
                            rs.getString("genre"),
                            rs.getDate("date_naissance"));
                    return enseignant;
                }
                if (rs.getString("role").equals("STUDENT")) {
                    Etudiant etudiant = new Etudiant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    return etudiant;
                }
                if (rs.getString("role").equals("CLUB_RH")) {
                    ResponsableClub responsableClub = new ResponsableClub(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    return responsableClub;
                }
                if (rs.getString("role").equals("ENTREPRISE_RH")) {
                    Entreprise entreprise = new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("website"),
                            rs.getString("pays"),
                            rs.getString("localisation"));
                    return entreprise;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("no user with this id");
        return null;
    }


    @Override
    public User getByEmail(String email) {
        try {
            String sql = "select * from user where email = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("role").equals("ADMIN")) {
                    Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"));
                    return admin;
                }
                if (rs.getString("role").equals("TEACHER")) {
                    Enseignant enseignant = new Enseignant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"),
                            rs.getString("cin"),
                            rs.getString("genre"),
                            rs.getDate("date_naissance"));
                    return enseignant;
                }
                if (rs.getString("role").equals("STUDENT")) {
                    Etudiant etudiant = new Etudiant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    return etudiant;
                }
                if (rs.getString("role").equals("CLUB_RH")) {
                    ResponsableClub responsableClub = new ResponsableClub(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    return responsableClub;
                }
                if (rs.getString("role").equals("ENTREPRISE_RH")) {
                    Entreprise entreprise = new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("website"),
                            rs.getString("pays"),
                            rs.getString("localisation"));
                    return entreprise;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("no user with this email");
        return null;
    }

    @Override
    public void add(User user) throws SQLException {
        String role = "", req = "";
        PreparedStatement ps;

        if (user instanceof Admin admin) {
            req = "insert into user (nom,prenom,email,password,role) values (?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
            ps.setString(5, String.valueOf(RoleName.ADMIN));

            ps.executeUpdate();
            System.out.println("Admin created !");
        } else if (user instanceof Enseignant enseignant) {
            req = "insert into user (nom,prenom,email,password,cin,genre,date_naissance,role) values (?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);
            ps.setString(1, enseignant.getNom());
            ps.setString(2, enseignant.getPrenom());
            ps.setString(3, enseignant.getEmail());
            ps.setString(4, BCrypt.hashpw(enseignant.getPassword(), BCrypt.gensalt()));
            ps.setString(5, enseignant.getCin());
            ps.setString(6, enseignant.getGenre());
            ps.setDate(7, new java.sql.Date(enseignant.getDate_naissance().getTime()));
            ps.setString(8, String.valueOf(RoleName.TEACHER));

            ps.executeUpdate();
            System.out.println("Enseignant created !");
        } else if (user instanceof Etudiant etudiant) {
            req = "insert into user (nom,prenom,date_naissance,niveau,genre,cin,classe,profil_picture,adresse,email,password,role) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, new java.sql.Date(etudiant.getDate_naissance().getTime()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getGenre());
            ps.setString(6, etudiant.getCin());
            ps.setString(7, etudiant.getClasse());
            ps.setString(8, etudiant.getProfil_picture());
            ps.setString(9, etudiant.getAdresse());
            ps.setString(10, etudiant.getEmail());
            ps.setString(11, BCrypt.hashpw(etudiant.getPassword(), BCrypt.gensalt()));
            ps.setString(12, String.valueOf(RoleName.STUDENT));

            ps.executeUpdate();
            System.out.println("Etudiant created !");
        } else if (user instanceof Entreprise entreprise) {
            req = "insert into user (nom,website,pays,localisation,email,password,role) values (?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getWebsite());
            ps.setString(3, entreprise.getPays());
            ps.setString(4, entreprise.getLocalisation());
            ps.setString(5, entreprise.getEmail());
            ps.setString(6, BCrypt.hashpw(entreprise.getPassword(), BCrypt.gensalt()));
            ps.setString(7, String.valueOf(RoleName.ENTREPRISE_RH));

            ps.executeUpdate();
            System.out.println("Entreprise created !");
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String role = "", req = "";
        PreparedStatement ps;

        if (user instanceof Admin admin) {
            req = "update user set nom=? ,prenom=? ,email=? ,password=? ,role=? where id = ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
            ps.setString(5, String.valueOf(RoleName.ADMIN));
            ps.setInt(6, admin.getId());

            ps.executeUpdate();
            System.out.println("Admin updated !");
        } else if (user instanceof Enseignant enseignant) {
            req = "update user set nom=?, prenom=?, email=?,password=? ,cin=? ,genre=? ,date_naissance=? ,role=? where id= ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, enseignant.getNom());
            ps.setString(2, enseignant.getPrenom());
            ps.setString(3, enseignant.getEmail());
            ps.setString(4, BCrypt.hashpw(enseignant.getPassword(), BCrypt.gensalt()));
            ps.setString(5, enseignant.getCin());
            ps.setString(6, enseignant.getGenre());
            ps.setDate(7, new java.sql.Date(enseignant.getDate_naissance().getTime()));
            ps.setString(8, String.valueOf(RoleName.TEACHER));
            ps.setInt(9, enseignant.getId());

            ps.executeUpdate();
            System.out.println("Enseignant updated !");
        } else if (user instanceof Etudiant etudiant) {
            req = "update user set nom=? ,prenom=? ,date_naissance=? ,niveau=? ,genre=? ,cin=? ,classe=? ,profil_picture=? ,adresse=? ,email=? ,password=? ,role=? where id = ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, new java.sql.Date(etudiant.getDate_naissance().getTime()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getGenre());
            ps.setString(6, etudiant.getCin());
            ps.setString(7, etudiant.getClasse());
            ps.setString(8, etudiant.getProfil_picture());
            ps.setString(9, etudiant.getAdresse());
            ps.setString(10, etudiant.getEmail());
            ps.setString(11, BCrypt.hashpw(etudiant.getPassword(), BCrypt.gensalt()));
            ps.setString(12, String.valueOf(RoleName.STUDENT));
            ps.setInt(13, etudiant.getId());

            ps.executeUpdate();
            System.out.println("Etudiant updated !");
        } else if (user instanceof Entreprise entreprise) {
            req = "update user set nom=?,website=? ,pays=? ,localisation=? ,email=? ,password=? ,role=? where id = ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getWebsite());
            ps.setString(3, entreprise.getPays());
            ps.setString(4, entreprise.getLocalisation());
            ps.setString(5, entreprise.getEmail());
            ps.setString(6, BCrypt.hashpw(entreprise.getPassword(), BCrypt.gensalt()));
            ps.setString(7, String.valueOf(RoleName.ENTREPRISE_RH));
            ps.setInt(8, entreprise.getId());

            ps.executeUpdate();
            System.out.println("Entreprise updated !");
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            int affectedRows = ste.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user was deleted. Check if the provided user ID exists.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from user";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("role").equals("ADMIN")) {
                    Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"));
                    users.add(admin);
                }
                if (rs.getString("role").equals("TEACHER")) {
                    Enseignant enseignant = new Enseignant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"),
                            rs.getString("cin"),
                            rs.getString("genre"),
                            rs.getDate("date_naissance"));
                    users.add(enseignant);
                }
                if (rs.getString("role").equals("STUDENT")) {
                    Etudiant etudiant = new Etudiant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    users.add(etudiant);
                }
                if (rs.getString("role").equals("CLUB_RH")) {
                    ResponsableClub responsableClub = new ResponsableClub(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getInt("niveau"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("cin"),
                            rs.getString("classe"),
                            rs.getString("profil_picture"),
                            rs.getString("adresse"),
                            rs.getDate("date_naissance"));
                    users.add(responsableClub);
                }
                if (rs.getString("role").equals("ENTREPRISE_RH")) {
                    Entreprise entreprise = new Entreprise(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("website"),
                            rs.getString("pays"),
                            rs.getString("localisation"));
                    users.add(entreprise);
                }

            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return users;
    }
}
