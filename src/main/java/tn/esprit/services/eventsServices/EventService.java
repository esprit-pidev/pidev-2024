package tn.esprit.services.eventsServices;


import tn.esprit.entities.events.Events;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class EventService {
        Connection cnx = tn.esprit.tools.MyDB.getInstance().getCnx();
        EventParticipantService eventParticipantService = new EventParticipantService();

        public EventService() {
        }

        public void ajouter(Events event) {
            String sql = "INSERT INTO events (admin_id, event_name, description, event_date, created_at,event_photo) VALUES (?, ?, ?, ?, NOW(),?)";

            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, event.getAdminId());
                statement.setString(2, event.getEventName());
                statement.setString(3, event.getDescription());
                statement.setDate(4, event.getEventDate());
                statement.setString(5,event.getPhoto());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    event.setEventId(generatedKeys.getInt(1));
                }
                System.out.println("Event Added!");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }
        }

        public void modifier(Events event,int id) {
            String sql = "UPDATE events SET  event_name = ?, description = ?, event_date = ? ,event_photo=?  WHERE id = ?";
            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql);
                statement.setString(1, event.getEventName());
                statement.setString(2, event.getDescription());
                statement.setDate(3, event.getEventDate());
                statement.setString(4,event.getPhoto());
                statement.setInt(5, id);
                statement.executeUpdate();
                System.out.println("Event Updated!");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }
        }

        public void supprimer(Events event) {
            String sql = "DELETE FROM events WHERE id = ?";

            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql);
                statement.setInt(1, event.getEventId());
                statement.executeUpdate();
                System.out.println("Event Deleted!");
            } catch (SQLException var4) {
                System.out.println(var4.getMessage());
            }
        }

        public List<Events> display(String filterOption) {
            List<Events> events = new ArrayList<>();
            String sql = "SELECT * FROM events ";

            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    Events event = new Events();

                    event.setEventId(rs.getInt("id"));
                    event.setAdminId(rs.getInt("admin_id"));
                    event.setEventName(rs.getString("event_name"));
                    event.setDescription(rs.getString("description"));
                    event.setEventDate(rs.getDate("event_date"));
                    event.setCreatedAt(rs.getDate("created_at"));
                    event.setPhoto(rs.getString("event_photo"));
                    event.initializeParticipants(eventParticipantService);
                    events.add(event);
                }
            } catch (SQLException var6) {
                System.out.println(var6.getMessage());
            }
            return events;
        }
        public List<Events> display() {
            List<Events> events = new ArrayList<>();
            String sql = "SELECT * FROM events ";

            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql);

                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    Events event = new Events();
                    event.setEventId(rs.getInt("id"));
                    event.setAdminId(rs.getInt("admin_id"));
                    event.setEventName(rs.getString("event_name"));
                    event.setDescription(rs.getString("description"));
                    event.setEventDate(rs.getDate("event_date"));
                    event.setCreatedAt(rs.getDate("created_at"));
                    event.setPhoto(rs.getString("event_photo"));
                    events.add(event);
                }
            } catch (SQLException var6) {
                System.out.println(var6.getMessage());
            }
            return events;
        }

    }


