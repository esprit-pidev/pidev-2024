package tn.esprit.entities.events;

import tn.esprit.entities.User.User;

import java.util.Date;

public class EventParticipants {


        private int participantId;
        private User user;
        private int eventId;
        private String participant_name;
        private Date participationDate;

        public EventParticipants( User user, int eventId) {
            this.user = user;
            this.eventId = eventId;

        }

    public EventParticipants() {

    }

    public String getParticipant_name() {
        return participant_name;
    }

    public void setParticipant_name(String participant_name) {
        this.participant_name = participant_name;
    }

    public int getParticipantId() {
            return participantId;
        }

        public void setParticipantId(int participantId) {
            this.participantId = participantId;
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



        public Date getParticipationDate() {
            return participationDate;
        }

        public void setParticipationDate(Date participationDate) {
            this.participationDate = participationDate;
        }

    @Override
    public String toString() {
        return "EventParticipants{" +
                "userId=" + user +
                ", eventId=" + eventId +
                ", participant_name='" + participant_name + '\'' +
                ", participationDate=" + participationDate +
                '}';
    }
}


