package org.example;

import tn.esprit.entities.User.Entreprise;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.RoleName;
import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;
import tn.esprit.services.userServices.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        final UserService us = new UserService();

        try {
            //us.add(new Admin("Hadded","Taha Yassine","12345",RoleName.ADMIN,"Khalil"));
            us.add(new Entreprise("TEAMDEV","teamdev@gmail.com","0000","teamsyst.com","Tunisie","Tunisie"));
            //us.add(new Enseignant("Ahmed","ahmed@gmail.com","0000","Tahri","11664468","male",new Date()));
            us.add(new Etudiant("aymen","aymen@gmail.com","0000",3,"hadded","male","11448475","3A21","aaa",LocalDate.now()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}