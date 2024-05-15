package Controllers.etudiant;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ContainerTacheController {

    @FXML
    private Pane containertache;
     private String taskDetails ;
    private int projectId;
    private int idMember;

    public String getTaskDetails() {
        return taskDetails;
    }



}

