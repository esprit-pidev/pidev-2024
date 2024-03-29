package tn.esprit.services.eventsServices;

import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.Events;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventCommentService {
    Connection cnx = MyDB.getInstance().getCnx();
    UserService userService = new UserService();
    public EventCommentService() {
    }

    public void ajouter(EventComments comment) {
        String sql = "INSERT INTO event_comments (user_id, event_id, comment_text, created_at) VALUES (?, ?, ?, NOW())";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, comment.getUserId().getId());
            statement.setInt(2, comment.getEventId());
            statement.setString(3, comment.getCommentText());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                comment.setCommentId(generatedKeys.getInt(1));
            }
            System.out.println("Comment Added for Event ID: " + comment.getEventId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(EventComments comment) {
        String sql = "UPDATE event_comments SET comment_text = ? WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, comment.getCommentText());
            statement.setInt(2, comment.getCommentId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Comment updated successfully");
            } else {
                System.out.println("No comment found for comment ID: " + comment.getCommentId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(EventComments comment) {
        String sql = "DELETE FROM event_comments WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, comment.getCommentId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Comment deleted successfully");
            } else {
                System.out.println("No comment found for comment ID: " + comment.getCommentId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<EventComments> display(Events event) {

        List<EventComments> comments = new ArrayList<>();
        String sql = "SELECT * FROM event_comments where event_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, event.getEventId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                EventComments comment = new EventComments();
                comment.setCommentId(rs.getInt("id"));
                int userId = rs.getInt("user_id");
                comment.setUserId(userService.getAll().stream().filter(e->e.getId() ==userId ).findFirst().orElse(null));

                comment.setEventId(rs.getInt("event_id"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comments.add(comment);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return comments;
    }
}
