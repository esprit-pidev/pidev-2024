package tn.esprit.entities.events;

import tn.esprit.entities.User.User;
import tn.esprit.services.eventsServices.EventParticipantService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Events {
    private int eventId;
    private User admin;
    private String eventName;
    private String description;
    private java.sql.Date eventDate;
    private Date createdAt;
    private Date updatedAt;
    private List<EventParticipants> participants = new ArrayList<>();

    private String photo;
    EventParticipantService eventParticipantService = new EventParticipantService();
    public Events(User  admin, String eventName, String description, java.sql.Date eventDate, String photo) {
        this.admin = admin;
        this.eventName = eventName;
        this.description = description;
        this.eventDate = eventDate;
        this.photo = photo;
    }
    public List<EventParticipants> getParticipants() {
        return participants;
    }

    public void initializeParticipants(EventParticipantService eventParticipantService) {
        setParticipants( eventParticipantService.display().stream()
                .filter(eventParticipants -> eventParticipants.getEventId() == this.getEventId())
                .toList());
        System.out.println(participants);
    }

    public void setParticipants(List<EventParticipants> participants) {
        this.participants = participants;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Events() {

    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public User getAdminId() {
        return admin;
    }

    public void setAdminId(User admin) {
        this.admin = admin;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(java.sql.Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", adminId=" + admin +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';

    }
}
