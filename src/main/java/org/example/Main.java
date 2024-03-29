package org.example;

import tn.esprit.entities.User.*;
import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;
import tn.esprit.services.userServices.UserService;

import java.sql.SQLException;

import java.time.LocalDate;
import java.util.prefs.Preferences;

public class Main {

    public static void main(String[] args) throws SQLException {
     UserService us = new UserService();
      //  userService.add(new Admin("Hadded","Taha Yassine","12345", RoleName.ADMIN,"Khalil"));
        Preferences prefs = Preferences.userRoot().node("com/myapp");
        prefs.remove("rememberMeToken");
        //us.add(new Admin("Hadded","admin","admin",RoleName.ADMIN,"Khalil"));

        us.add(new Entreprise("TEAMDEV","teamdev@gmail.com","0000",RoleName.ENTREPRISE_RH,"teamsyst.com","Tunisie","Tunisie"));




        /*try {
            //
            us.add(new Enseignant("Ahmed","Ahmed@esprit.tn","123456", RoleName.valueOf("TEACHER"),"Tahri","21111111","male", LocalDate.now()));
            us.add(new Etudiant("aymen","aymen@esprit.tn","0000",3,"hadded","male","11448475","3A21","aaa",LocalDate.now()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


     //   System.out.println(us.getById(7));
      //  System.out.println(us.getByEmail("teamdev@gmail.com"));
       // us.delete(1);
       // System.out.println(us.getAll());
    }
}