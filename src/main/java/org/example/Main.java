package org.example;
import tn.esprit.entities.User.User;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ProjectService ps = new ProjectService();
        Project p = new Project();
        p.setClasse("3a5");
        p.setMatiere("ppi");
        p.setNom("hayaa");
        p.setDescription("oooo");
        p.setId(8);
        ps.modifier(p);




}}