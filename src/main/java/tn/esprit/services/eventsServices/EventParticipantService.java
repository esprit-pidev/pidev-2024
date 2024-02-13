package tn.esprit.services.eventsServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.events.EventParticipants;
import tn.esprit.tools.MyDB;

public class EventParticipantService {
    Connection cnx = MyDB.getInstance().getCnx();

    public EventParticipantService() {
    }

    public void ajouter(EventParticipants participant) {
        String sql = "INSERT INTO eventparticipants (user_id, event_id, is_attending, participation_date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, participant.getUserId());
            statement.setInt(2, participant.getEventId());
            statement.setBoolean(3, participant.isAttending());
            statement.setTimestamp(4, new java.sql.Timestamp(participant.getParticipationDate().getTime()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                participant.setParticipantId(generatedKeys.getInt(1));
            }
            System.out.println("Participant added!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(EventParticipants participant) {
        String sql = "UPDATE eventparticipants SET is_attending = ?, participation_date = ? WHERE participant_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setBoolean(1, participant.isAttending());
            statement.setTimestamp(2, new java.sql.Timestamp(participant.getParticipationDate().getTime()));
            statement.setInt(3, participant.getParticipantId());
            statement.executeUpdate();
            System.out.println("Participant updated!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(EventParticipants participant) {
        String sql = "DELETE FROM eventparticipants WHERE participant_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, participant.getParticipantId());
            statement.executeUpdate();
            System.out.println("Participant deleted!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<EventParticipants> display() {
        List<EventParticipants> participants = new ArrayList<>();
        String sql = "SELECT * FROM eventparticipants";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                EventParticipants participant = new EventParticipants();
                participant.setParticipantId(rs.getInt("participant_id"));
                participant.setUserId(rs.getInt("user_id"));
                participant.setEventId(rs.getInt("event_id"));
                participant.setAttending(rs.getBoolean("is_attending"));
                participant.setParticipationDate(rs.getTimestamp("participation_date"));
                participants.add(participant);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return participants;
    }
}
