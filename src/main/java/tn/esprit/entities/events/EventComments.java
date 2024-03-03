package tn.esprit.entities.events;

import tn.esprit.entities.User.User;

import java.util.Date;

public class EventComments {

        private int commentId;
        private User user;
        private int eventId;
        private String commentText;
        private Date createdAt;

        public EventComments( User user, int eventId, String commentText ) {

            this.user = user;
            this.eventId = eventId;
            this.commentText = commentText;
            this.createdAt = createdAt;
        }

    public EventComments() {
    }

    public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public User getUserId() {
            return user;
        }

        public void setUserId(User userId) {
            this.user = userId;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "EventComment{" +
                    ", commentText='" + commentText + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }
    }


