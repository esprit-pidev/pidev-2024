package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.User.User;
import tn.esprit.entities.project.Project;
import tn.esprit.services.projectService.ProjectService;

import java.io.IOException;
import java.util.Date;

public class AjouterProjetController {

    @FXML
    private TextField classe;

    @FXML
    private TextField description;

    @FXML
    private TextField matiere;

    @FXML
    private TextField nom;

    // Service pour la gestion des projets
    private final ProjectService projectService = new ProjectService();

    // Méthode pour ajouter un projet
    @FXML
    void ajouterProjet() {
        // Créer un nouvel objet User avec l'ID fixé à 1
        User user = new User();
        user.setId(1);

        // Créer un nouvel objet Project avec les données saisies par l'utilisateur
        Project nouveauProjet = new Project();
        nouveauProjet.setUser(user); // Définir l'utilisateur pour le projet
        nouveauProjet.setNom(nom.getText());
        nouveauProjet.setDescription(description.getText());
        nouveauProjet.setClasse(classe.getText());
        nouveauProjet.setMatiere(matiere.getText());
        nouveauProjet.setCreated_at(new Date());
        nouveauProjet.setUpdated_at(new Date());

        // Ajouter le projet en utilisant le service de projet
        projectService.ajouter(nouveauProjet);

     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProjet.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle à partir d'un nœud dans la vue actuelle
        Stage stage = (Stage) nom.getScene().getWindow();

        // Définir la nouvelle scène sur la fenêtre
        stage.setScene(scene);

        // Afficher la nouvelle vue
        stage.show();
    } catch (
    IOException e) {
        e.printStackTrace();
    }

}}
