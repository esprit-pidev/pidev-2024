package tn.esprit.services.eventsServices;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.events.Events;
import tn.esprit.tools.MyDB;
    public class EventService {
        Connection cnx = MyDB.getInstance().getCnx();

        public EventService() {
        }

        public void ajouter(Events event) {
            String sql = "INSERT INTO events (admin_id, event_name, description, event_date, created_at,event_photo) VALUES (?, ?, ?, ?, NOW(),?)";
            long millis=System.currentTimeMillis();
            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, event.getAdminId());
                statement.setString(2, event.getEventName());
                statement.setString(3, event.getDescription());
                statement.setDate(4, new java.sql.Date(millis));
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

        public void modifier(Events event) {
            String sql = "UPDATE events SET admin_id = ?, event_name = ?, description = ?, event_date = ? WHERE id = ?";
            long millis=System.currentTimeMillis();
            try {
                PreparedStatement statement = this.cnx.prepareStatement(sql);
                statement.setInt(1, event.getAdminId());
                statement.setString(2, event.getEventName());
                statement.setString(3, event.getDescription());
                statement.setDate(4, new java.sql.Date(millis));
                statement.setInt(5, event.getEventId());
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

        public List<Events> display() {
            List<Events> events = new ArrayList<>();
            String sql = "SELECT * FROM events";

            try {
                Statement statement = this.cnx.createStatement();
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


