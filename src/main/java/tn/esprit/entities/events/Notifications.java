package tn.esprit.entities.events;

import java.util.Date;

public class Notifications {

        private int notificationId;
        private int userId;
        private int eventId;

        private String notificationText;
        private Date notificationTime;


        public Notifications( int userId, int eventId,  String notificationText, Date notificationTime) {
            this.notificationId = notificationId;
            this.userId = userId;
            this.eventId = eventId;
            this.notificationText = notificationText;
            this.notificationTime = notificationTime;
        }

    public Notifications() {

    }

    public int getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(int notificationId) {
            this.notificationId = notificationId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }



        public String getNotificationText() {
            return notificationText;
        }

        public void setNotificationText(String notificationText) {
            this.notificationText = notificationText;
        }

        public Date getNotificationTime() {
            return notificationTime;
        }

        public void setNotificationTime(Date notificationTime) {
            this.notificationTime = notificationTime;
        }



        @Override
        public String toString() {
            return "Notification{" +
                    ", notificationText='" + notificationText + '\'' +
                    ", notificationTime=" + notificationTime +
                    '}';
        }
    }


