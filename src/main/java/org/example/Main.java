package org.example;

import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventService;

public class Main {
    public static void main(String[] args) {
        Events event = new Events();
        event.setEventId(4);
        EventService eventService = new EventService();
        eventService.supprimer(event);




/*
        try {
            //us.add(new Admin("Hadded","Taha Yassine","12345",RoleName.ADMIN,"Khalil"));
            //us.add(new Entreprise("TEAMDEV","teamdev@gmail.com","0000",RoleName.ENTREPRISE_RH,"teamsyst.com","Tunisie","Tunisie"));
            //us.add(new Enseignant("Ahmed","ahmed@gmail.com","0000","Tahri","11664468","male",new Date()));
            //us.add(new Etudiant("aymen","aymen@gmail.com","0000",3,"hadded","male","11448475","3A21","aaa","aaa",new Date()));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        System.out.println(us.getById(7));
        System.out.println(us.getByEmail("teamdev@gmail.com"));
        us.delete(1);
        System.out.println(us.getAll());
*/
    }
}