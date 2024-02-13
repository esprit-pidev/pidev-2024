package org.example;

import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;

public class Main {
    public static void main(String[] args) {
        EventComments eventComments1 = new EventComments(1, 4, "bingo4");
        EventCommentService eventCommentService = new EventCommentService();
        eventComments1.setCommentId(3);
        System.out.println(eventCommentService.display());

    }
}