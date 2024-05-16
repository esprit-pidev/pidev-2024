package Controllers.etudiant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TacheController {

        @FXML
        private Label tacheD;
        private String taskDetails ;

        public void initData(String taskDetails){
            this.taskDetails = taskDetails ;
            tacheD.setText(taskDetails);
        }
    }



