package tn.esprit.services.eventsServices;

import tn.esprit.entities.events.EventReactions;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EventReactionService {
    Connection cnx = MyDB.getInstance().getCnx();
    UserService userService = new UserService();
    public EventReactionService() {
    }

    public void ajouter(EventReactions reaction) {
        String sql = "INSERT INTO event_reactions (user_id, event_id, reaction_type, created_at) VALUES (?, ?, ?, NOW())";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, reaction.getUserId().getId());
            statement.setInt(2, reaction.getEventId());
            statement.setString(3, reaction.getReactionType());
            statement.executeUpdate();
            System.out.println("Reaction Added for Event ID: " + reaction.getEventId());
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(EventReactions reaction) {
        String sql = "UPDATE event_reactions SET reaction_type = ? WHERE user_id = ? AND event_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, reaction.getReactionType());
            statement.setInt(2, reaction.getUserId().getId());
            statement.setInt(3, reaction.getEventId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reaction updated successfully");
            } else {
                System.out.println("No reaction found for user ID: " + reaction.getUserId() + " and event ID: " + reaction.getEventId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(EventReactions reaction) {
        String sql = "DELETE FROM event_reactions WHERE user_id = ? AND event_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, reaction.getUserId().getId());
            statement.setInt(2, reaction.getEventId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reaction deleted successfully");
            } else {
                System.out.println("No reaction found for user ID: " + reaction.getUserId() + " and event ID: " + reaction.getEventId());
            }
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<EventReactions> display() {
        List<EventReactions> reactions = new ArrayList<>();
        String sql = "SELECT * FROM event_reactions";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                EventReactions reaction = new EventReactions();

                int  userId = rs.getInt("user_id");
                reaction.setUserId(userService.getAll().stream().filter(e->e.getId() ==userId ).findFirst().orElse(null));
                reaction.setEventId(rs.getInt("event_id"));
                reaction.setReactionType(rs.getString("reaction_type"));
                reaction.setCreatedAt(rs.getTimestamp("created_at"));
                reactions.add(reaction);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return reactions;
    }

}

