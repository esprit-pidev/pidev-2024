package tn.esprit.services.eventsServices;

import tn.esprit.entities.events.EventParticipants;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventParticipantService {
    Connection cnx = MyDB.getInstance().getCnx();
    UserService userService = new UserService();
    public EventParticipantService() {
    }

    public void ajouter(EventParticipants participant) {
        String sql = "INSERT INTO event_participants (user_id, event_id, name_participant) VALUES (?, ?, ?)";

        try {
            // Retrieve the name of the participant from the users table based on user_id
            String nameParticipant = getNameParticipant(participant.getUserId().getId());

            PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, participant.getUserId().getId());
            statement.setInt(2, participant.getEventId());
            statement.setString(3, nameParticipant);
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

    private String getNameParticipant(int userId) throws SQLException {
        String sql = "SELECT nom FROM user WHERE id = ?";
        try (PreparedStatement statement = this.cnx.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nom");
            } else {
                return null;
            }
        }
    }


    public void modifier(EventParticipants participant) {
        String sql = "UPDATE event_participants SET , participation_date = ? WHERE participant_id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setTimestamp(2, new Timestamp(participant.getParticipationDate().getTime()));
            statement.setInt(3, participant.getParticipantId());
            statement.executeUpdate();
            System.out.println("Participant updated!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(EventParticipants participant) {
        String sql = "DELETE FROM event_participants WHERE participant_id = ?";

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
        String sql = "SELECT * FROM event_participants";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                EventParticipants participant = new EventParticipants();
                participant.setParticipantId(rs.getInt("participant_id"));
                int userId = rs.getInt("user_id");
                participant.setUserId(userService.getAll().stream().filter(e->e.getId() ==userId ).findFirst().orElse(null));

                participant.setEventId(rs.getInt("event_id"));
                participant.setParticipationDate(rs.getTimestamp("participation_date"));
                participant.setParticipant_name(rs.getString("name_participant"));

                participants.add(participant);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return participants;
    }
}
