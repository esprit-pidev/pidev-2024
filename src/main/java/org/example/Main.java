package org.example;

import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.ResponsableClub;
import tn.esprit.entities.events.EventComments;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.userServices.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EventComments eventComments1 = new EventComments(1, 4, "bingo4");
        EventCommentService eventCommentService = new EventCommentService();
        eventComments1.setCommentId(3);
        System.out.println(eventCommentService.display());
        UserService us = new UserService();


        //us.add(new Admin("Hadded","Taha Yassine","12345",RoleName.ADMIN,"Khalil"));
        //us.add(new Entreprise("TEAMDEV","teamdev@gmail.com","0000",RoleName.ENTREPRISE_RH,"teamsyst.com","Tunisie","Tunisie"));
        //us.add(new Enseignant("Ahmed","ahmed@gmail.com","0000","Tahri","11664468","male",new Date()));
        try {
            us.add(new ResponsableClub("BenSalah","Mayar15@esprit.tn","0000",3,"Mayar","female","11156987","3A21","aaa",LocalDate.of(1980, 5, 15)));
            us.toClubRH(5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}