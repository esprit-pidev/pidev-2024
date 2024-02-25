package tn.esprit.entities.events;

import java.util.Date;

public class EventReactions {

        private int reactionId;
        private int userId;
        private int eventId;
        private String reactionType;
        private Date createdAt;

        public EventReactions( int userId, int eventId, String reactionType) {
            this.userId = userId;
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


