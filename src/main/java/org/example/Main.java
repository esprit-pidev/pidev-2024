package org.example;

import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;

public class Main {
    public static void main(String[] args) {
        Events event = new Events();
        event.setEventId(4);
        EventService eventService = new EventService();
        eventService.supprimer(event);

    }
}