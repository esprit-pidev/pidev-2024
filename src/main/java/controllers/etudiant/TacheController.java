package controllers.etudiant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TacheController {

    @FXML
    private Label tache;

    public void setTache(String taskDetails) {

        this.tache.setText(taskDetails);
    }
}
