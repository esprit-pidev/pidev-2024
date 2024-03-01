package org.example;
import tn.esprit.entities.User.User;
import tn.esprit.entities.project.Matiere;
import tn.esprit.entities.project.Project;
import tn.esprit.entities.project.Taches;
import tn.esprit.services.projectService.MatiereService;
import tn.esprit.services.projectService.ProjectMembersService;
import tn.esprit.services.projectService.ProjectService;
import tn.esprit.services.projectService.TacheService;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Taches tache = new Taches();
        User user = new User();
        TacheService ts = new TacheService();
        user.setId(1);
        tache.setUser(user);
        tache.setEtat("En cours");
        tache.setDescription("Faire la documentation");
        tache.setDate_ajout(new Date());
        tache.setDedline(new Date());
        ts.addTache(tache);

            }
        }





