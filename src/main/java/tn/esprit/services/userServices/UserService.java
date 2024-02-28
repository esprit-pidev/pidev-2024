package tn.esprit.services.userServices;


import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.User.*;
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
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("profil_picture"));
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
                            rs.getDate("date_naissance").toLocalDate());
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
                            rs.getDate("date_naissance").toLocalDate());
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
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("profil_picture"));
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
                            rs.getDate("date_naissance").toLocalDate());
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
                            rs.getDate("date_naissance").toLocalDate());
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
            req = "insert into user (nom,prenom,email,password,role,is_enabled) values (?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
            ps.setString(5, String.valueOf(RoleName.ADMIN));
            ps.setBoolean(6, admin.getIsEnabled());

            ps.executeUpdate();
            System.out.println("Admin created !");
        } else if (user instanceof Enseignant enseignant) {
            req = "insert into user (nom,prenom,email,password,cin,genre,date_naissance,profil_picture,role,is_enabled) values (?,?,?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, enseignant.getNom());
            ps.setString(2, enseignant.getPrenom());
            ps.setString(3, enseignant.getEmail());
            ps.setString(4, BCrypt.hashpw(enseignant.getPassword(), BCrypt.gensalt()));
            ps.setString(5, enseignant.getCin());
            ps.setString(6, enseignant.getGenre());
            ps.setDate(7, Date.valueOf(enseignant.getDate_naissance()));
            ps.setString(8, enseignant.getProfil_picture());
            ps.setString(9, String.valueOf(RoleName.TEACHER));
            ps.setBoolean(10, enseignant.getIsEnabled());

            ps.executeUpdate();
            System.out.println("Enseignant created !");
        } else if (user instanceof Etudiant etudiant) {
            req = "insert into user (nom,prenom,date_naissance,niveau,genre,cin,classe,profil_picture,email,password,role,is_enabled) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, Date.valueOf(etudiant.getDate_naissance()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getGenre());
            ps.setString(6, etudiant.getCin());
            ps.setString(7, etudiant.getClasse());
            ps.setString(8, etudiant.getProfil_picture());
            ps.setString(9, etudiant.getEmail());
            ps.setString(10, BCrypt.hashpw(etudiant.getPassword(), BCrypt.gensalt()));
            ps.setString(11, String.valueOf(RoleName.STUDENT));
            ps.setBoolean(12, etudiant.getIsEnabled());

            ps.executeUpdate();
            System.out.println("Etudiant created !");
        } else if (user instanceof Entreprise entreprise) {
            req = "insert into user (nom,website,pays,localisation,email,password,role,is_enabled) values (?,?,?,?,?,?,?,?)";
            ps = cnx.prepareStatement(req);

            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getWebsite());
            ps.setString(3, entreprise.getPays());
            ps.setString(4, entreprise.getLocalisation());
            ps.setString(5, entreprise.getEmail());
            ps.setString(6, BCrypt.hashpw(entreprise.getPassword(), BCrypt.gensalt()));
            ps.setString(7, String.valueOf(RoleName.ENTREPRISE_RH));
            ps.setBoolean(8, entreprise.getIsEnabled());

            ps.executeUpdate();
            System.out.println("Entreprise created !");
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String req = "";
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
            req = "update user set nom=?, prenom=?, email=?,password=? ,cin=? ,genre=? ,date_naissance=?, profil_picture=? ,role=? where id= ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, enseignant.getNom());
            ps.setString(2, enseignant.getPrenom());
            ps.setString(3, enseignant.getEmail());
            ps.setString(4, BCrypt.hashpw(enseignant.getPassword(), BCrypt.gensalt()));
            ps.setString(5, enseignant.getCin());
            ps.setString(6, enseignant.getGenre());
            ps.setDate(7, Date.valueOf(enseignant.getDate_naissance()));
            ps.setString(8, enseignant.getProfil_picture());
            ps.setString(9, String.valueOf(RoleName.TEACHER));
            ps.setInt(10, enseignant.getId());

            ps.executeUpdate();
            System.out.println("Enseignant updated !");
        } else if (user instanceof Etudiant etudiant) {
            req = "update user set nom=? ,prenom=? ,date_naissance=? ,niveau=? ,genre=? ,cin=? ,classe=? ,profil_picture=? ,email=? ,password=? ,role=? where id = ?";
            ps = cnx.prepareStatement(req);

            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, Date.valueOf(etudiant.getDate_naissance()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getGenre());
            ps.setString(6, etudiant.getCin());
            ps.setString(7, etudiant.getClasse());
            ps.setString(8, etudiant.getProfil_picture());
            ps.setString(9, etudiant.getEmail());
            ps.setString(10, BCrypt.hashpw(etudiant.getPassword(), BCrypt.gensalt()));
            ps.setString(11, String.valueOf(RoleName.STUDENT));
            ps.setInt(12, etudiant.getId());

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
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("profil_picture"));
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
                            rs.getDate("date_naissance").toLocalDate());
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
                            rs.getDate("date_naissance").toLocalDate());
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

    @Override
    public List<User> getByRole(RoleName role) {
        List<User> users = new ArrayList<>();
        String sql = "select * from user where role = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1,String.valueOf(role));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (role.equals(RoleName.ADMIN)) {
                    Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"));
                    users.add(admin);
                }
                if (role.equals(RoleName.TEACHER)) {
                    Enseignant enseignant = new Enseignant(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            RoleName.valueOf(rs.getString("role").toUpperCase()),
                            rs.getString("prenom"),
                            rs.getString("cin"),
                            rs.getString("genre"),
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("profil_picture"));
                    users.add(enseignant);
                }
                if (role.equals(RoleName.STUDENT)) {
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
                            rs.getDate("date_naissance").toLocalDate());
                    users.add(etudiant);
                }
                if (role.equals(RoleName.CLUB_RH)) {
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
                            rs.getDate("date_naissance").toLocalDate());
                    users.add(responsableClub);
                }
                if (role.equals(RoleName.ENTREPRISE_RH)) {
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

    @Override
    public void blockUser(int id) {
        String sql = "update user set is_enabled=false where id = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("User blocked");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void unblockUser(int id) {
        String sql = "update user set is_enabled=true where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("User unblocked");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void toClubRH(int id) {
        if(this.getById(id) instanceof Etudiant) {
            String sql = "update user set role=? where id = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(sql);
                ps.setString(1,String.valueOf(RoleName.CLUB_RH));
                ps.setInt(2,id);
                ps.executeUpdate();
                System.out.println("Changed role to club RH");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void toStudent(int id) {
        if(this.getById(id) instanceof ResponsableClub) {
            String sql = "update user set role=? where id = ?";
            try {
                PreparedStatement ps = cnx.prepareStatement(sql);
                ps.setString(1,String.valueOf(RoleName.STUDENT));
                ps.setInt(2,id);
                ps.executeUpdate();
                System.out.println("Changed role to club Student");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void adminUpdateEtudiant(Etudiant etudiant) {
        String req = "update user set nom=? ,prenom=? ,date_naissance=? ,niveau=? ,genre=? ,cin=? ,classe=? ,email=? where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, Date.valueOf(etudiant.getDate_naissance()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getGenre());
            ps.setString(6, etudiant.getCin());
            ps.setString(7, etudiant.getClasse());
            ps.setString(8, etudiant.getEmail());
            ps.setInt(9, etudiant.getId());

            ps.executeUpdate();
            System.out.println("Etudiant updated !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
        String req = "update user set nom=? ,prenom=? ,date_naissance=? ,niveau=? ,classe=? ,profil_picture=? where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, etudiant.getNom());
            ps.setString(2, etudiant.getPrenom());
            ps.setDate(3, Date.valueOf(etudiant.getDate_naissance()));
            ps.setInt(4, etudiant.getNiveau());
            ps.setString(5, etudiant.getClasse());
            ps.setString(6, etudiant.getProfil_picture());
            ps.setInt(7, etudiant.getId());

            ps.executeUpdate();
            System.out.println("Etudiant updated !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adminUpdateEnseignant(Enseignant enseignant) {
        String req = "update user set nom=? ,prenom=? ,date_naissance=? ,genre=? ,cin=? ,email=? where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, enseignant.getNom());
            ps.setString(2, enseignant.getPrenom());
            ps.setDate(3, Date.valueOf(enseignant.getDate_naissance()));
            ps.setString(4, enseignant.getGenre());
            ps.setString(5, enseignant.getCin());
            ps.setString(6, enseignant.getEmail());
            ps.setInt(7, enseignant.getId());

            ps.executeUpdate();
            System.out.println("Enseignant updated !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adminUpdateEntreprise(Entreprise entreprise) {
        String req = "update user set nom=? ,website=? , localisation=? ,pays=? ,email=? where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, entreprise.getNom());
            ps.setString(2, entreprise.getWebsite());
            ps.setString(3, entreprise.getLocalisation());
            ps.setString(4, entreprise.getPays());
            ps.setString(5, entreprise.getEmail());
            ps.setInt(6, entreprise.getId());

            ps.executeUpdate();
            System.out.println("Entreprise updated !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeMotDePasse(String password,User user) {
        String req= "update user set password=? where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setInt(2,user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}



