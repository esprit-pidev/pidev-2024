package tn.esprit.entities.events;

import tn.esprit.entities.User.User;

import java.util.Date;

public class EventReactions {

        private int reactionId;
        private User user;
        private int eventId;
        private String reactionType;
        private Date createdAt;

        public EventReactions( User user, int eventId, String reactionType) {
            this.user = user;
            this.eventId = eventId;
            this.reactionType = reactionType;

        }

        public EventReactions() {

        }

        public int getReactionId() {
            return reactionId;
        }

        public void setReactionId(int reactionId) {
            this.reactionId = reactionId;
        }

        public User getUserId() {
            return user;
        }

        public void setUserId(User user) {
            this.user = user;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getReactionType() {
            return reactionType;
        }

        public void setReactionType(String reactionType) {
            this.reactionType = reactionType;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "EventReaction{" +
                    ", reactionType='" + reactionType + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }
    }


