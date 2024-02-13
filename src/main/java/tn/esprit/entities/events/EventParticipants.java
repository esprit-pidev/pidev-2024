package tn.esprit.entities.events;

import java.util.Date;

public class EventParticipants {


        private int participantId;
        private int userId;
        private int eventId;
        private boolean isAttending;
        private Date participationDate;

        public EventParticipants(int participantId, int userId, int eventId, boolean isAttending, Date participationDate) {
            this.participantId = participantId;
            this.userId = userId;
            this.eventId = eventId;
            this.isAttending = isAttending;
            this.participationDate = participationDate;
        }

    public EventParticipants() {

    }

    public int getParticipantId() {
            return participantId;
        }

        public void setParticipantId(int participantId) {
            this.participantId = participantId;
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

        public boolean isAttending() {
            return isAttending;
        }

        public void setAttending(boolean attending) {
            isAttending = attending;
        }

        public Date getParticipationDate() {
            return participationDate;
        }

        public void setParticipationDate(Date participationDate) {
            this.participationDate = participationDate;
        }

        @Override
        public String toString() {
            return "EventParticipant{" +
                    ", isAttending=" + isAttending +
                    ", participationDate=" + participationDate +
                    '}';
        }
    }


