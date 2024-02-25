package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavbarController {

    @FXML
    private Button option1Button;

    @FXML
    private Button option2Button;

    @FXML
    private void handleOption1Click(ActionEvent event) {
        System.out.println("Option 1 clicked");
        // Ajoutez ici le code que vous souhaitez exécuter lorsque le bouton Option 1 est cliqué
    }

    @FXML
    private void handleOption2Click(ActionEvent event) {
        System.out.println("Option 2 clicked");
        // Ajoutez ici le code que vous souhaitez exécuter lorsque le bouton Option 2 est cliqué
    }
}

