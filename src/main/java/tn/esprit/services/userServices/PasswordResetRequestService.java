package tn.esprit.services.userServices;


import tn.esprit.entities.User.PasswordResetRequest;
import tn.esprit.entities.User.User;
import tn.esprit.tools.MyDB;

import java.sql.*;

public class PasswordResetRequestService implements IPasswordResetRequestService {

    Connection cnx;

    public PasswordResetRequestService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public PasswordResetRequest getById(int id) {
        String req = "select * from password_reset_request where id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PasswordResetRequest passwordResetRequest = new PasswordResetRequest(
                        rs.getInt("id"),
                        rs.getInt("reset_code"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("expires_at").toLocalDateTime(),
                        rs.getBoolean("is_valid"));
                return passwordResetRequest;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public int Add(PasswordResetRequest passwordResetRequest) {
        String req = "insert into password_reset_request (user_id, is_valid, expires_at, reset_code, created_at) values (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
            if (passwordResetRequest.getUser()!=null) {
                ps.setInt(1, passwordResetRequest.getUser().getId());
            }
            else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setBoolean(2,passwordResetRequest.isValid());
            ps.setTimestamp(3, Timestamp.valueOf(passwordResetRequest.getExpiresAt()));
            ps.setInt(4,passwordResetRequest.getResetCode());
            ps.setTimestamp(5, Timestamp.valueOf(passwordResetRequest.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    return generatedId;
                } else {
                    throw new SQLException("Creating password reset request failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public PasswordResetRequest getByUser(User user) {
        String req = "select * from password_reset_request where user_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PasswordResetRequest passwordResetRequest = new PasswordResetRequest(
                        rs.getInt("id"),
                        rs.getInt("reset_code"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("expires_at").toLocalDateTime(),
                        rs.getBoolean("is_valid"));
                return passwordResetRequest;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(User user) {
        String sql = "delete from password_reset_request where user_id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, user.getId());
            int affectedRows = ste.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("password_reset_request deleted successfully!");
            } else {
                System.out.println("No password_reset_request was deleted. Check if the provided user ID exists.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from password_reset_request where id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, id);
            int affectedRows = ste.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("password_reset_request deleted successfully!");
            } else {
                System.out.println("No password_reset_request was deleted. Check if the provided user ID exists.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
