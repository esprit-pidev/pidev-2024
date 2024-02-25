package tn.esprit.services.eventsServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.events.Notifications;
import tn.esprit.tools.MyDB;

public class NotificationService {
    Connection cnx = MyDB.getInstance().getCnx();

    public NotificationService() {
    }

    public void ajouter(Notifications notification) {
        String sql = "INSERT INTO notifications (user_id, event_id, notification_text, notification_time, ) VALUES (?, ?, ?,?)";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, notification.getUserId());
            statement.setInt(2, notification.getEventId());
            statement.setString(4, notification.getNotificationText());
            statement.setTimestamp(5, new java.sql.Timestamp(notification.getNotificationTime().getTime()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                notification.setNotificationId(generatedKeys.getInt(1));
            }
            System.out.println("Notification added!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void modifier(Notifications notification) {
        String sql = "UPDATE notifications SET  notification_text = ?, notification_time = ? WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setString(1, notification.getNotificationText());
            statement.setTimestamp(2, new java.sql.Timestamp(notification.getNotificationTime().getTime()));
            statement.setInt(3, notification.getNotificationId());
            statement.executeUpdate();
            System.out.println("Notification updated!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public void supprimer(Notifications notification) {
        String sql = "DELETE FROM notifications WHERE id = ?";

        try {
            PreparedStatement statement = this.cnx.prepareStatement(sql);
            statement.setInt(1, notification.getNotificationId());
            statement.executeUpdate();
            System.out.println("Notification deleted!");
        } catch (SQLException var4) {
            System.out.println(var4.getMessage());
        }
    }

    public List<Notifications> display() {
        List<Notifications> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications";

        try {
            Statement statement = this.cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Notifications notification = new Notifications();
                notification.setNotificationId(rs.getInt("id"));
                notification.setUserId(rs.getInt("user_id"));
                notification.setEventId(rs.getInt("event_id"));
                notification.setNotificationText(rs.getString("notification_text"));
                notification.setNotificationTime(rs.getTimestamp("notification_time"));
                notifications.add(notification);
            }
        } catch (SQLException var6) {
            System.out.println(var6.getMessage());
        }
        return notifications;
    }
}
