package controllers;

import entities.Cours;
import entities.Evaluation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.CoursService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifiercourController {

    @FXML
    private TextField coursurl;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nomcour;

    @FXML
    private TextField nommodule;
    private Cours cours;
    private final CoursService cs=new CoursService();
    @FXML
            public void setCours(Cours cours) {
                this.cours = cours;
                // Update UI components with the Organisateur's data
        coursurl.setText(cours.getcoursURLpdf());
        niveau.setText(String.valueOf(cours.getNiveau()));
        nomcour.setText(cours.getNomcours());
        nommodule.setText(cours.getNommodule());

        // Set other fields as needed
            }
            @FXML
            void modifiercour(ActionEvent event)
            {
                cours.setCoursURLpdf(coursurl.getText());
                cours.setNiveau(Integer.parseInt(niveau.getText()));
                cours.setNomcours(nomcour.getText());
                cours.setNommodule(nommodule.getText());

                cs.modifier(cours);
                naviguezVersAffichage(null);
            }
            @FXML
            void naviguezVersAffichage(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
                    nommodule.getScene().setRoot(root);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }


}



