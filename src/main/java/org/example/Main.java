package org.example;


import tn.esprit.entities.events.project.Project;
import tn.esprit.services.eventsServices.projectService.ProjectService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ProjectService ps = new ProjectService();
        ps.display();


    }



}