package Controllers.StageControllers;

import javafx.scene.image.Image;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public class AfficherCvController {
    @FXML
    private ImageView cvImageView;

    @FXML
    private Label competencesLabel;

    public void loadDetails(String cvFileName, String competences) {
        String cvFilePath = "C:\\xampp\\htdocs\\img\\" + cvFileName;
        File file = new File(cvFilePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            cvImageView.setImage(image);
        } else {
            System.out.println("Le fichier du CV n'existe pas : " + cvFilePath);
        }

        competencesLabel.setText("Compétences : " + competences);
    }
}
