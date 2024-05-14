package tn.esprit.services.userServices;


import tn.esprit.entities.User.RememberMeToken;
import tn.esprit.entities.User.User;
import tn.esprit.tools.MyDB;

import java.sql.*;

public class RememberMeTokenService implements IRememberMeToken {

    Connection cnx;

    private final UserService userService = new UserService();

    public RememberMeTokenService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public RememberMeToken getById(int id) {
        String req = "select * from remember_me_token where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RememberMeToken token = new RememberMeToken(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getTimestamp("expires_at").toLocalDateTime());
                return token;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public RememberMeToken getByToken(String tok) {
        String req = "select * from remember_me_token where token = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, tok);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RememberMeToken token = new RememberMeToken(
                        rs.getInt("id"),
                        userService.getById(rs.getInt("user_id")),
                        rs.getString("token"),
                        rs.getTimestamp("expires_at").toLocalDateTime());
                return token;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public RememberMeToken getByUser(User user) {
        String req = "select * from remember_me_token where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RememberMeToken token = new RememberMeToken(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getTimestamp("expires_at").toLocalDateTime());
                return token;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void add(RememberMeToken token) {
        String req = "insert into remember_me_token (user_id, token , expires_at) values (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, token.getUser().getId());
            ps.setString(2, token.getToken());
            ps.setTimestamp(3, Timestamp.valueOf(token.getExpiresAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteByUser(User user) {
        String sql = "delete from remember_me_token where user_id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, user.getId());
            int affectedRows = ste.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("remember_me_token deleted successfully!");
            } else {
                System.out.println("No remember_me_token was deleted. Check if the provided user ID exists.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from remember_me_token where id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            int affectedRows = ste.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("remember_me_token deleted successfully!");
            } else {
                System.out.println("No remember_me_token was deleted. Check if the provided user ID exists.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

