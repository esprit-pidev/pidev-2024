package Controllers.StageControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.entities.stage.Candidature;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class AfficherCandidatureDetailsController {

    @FXML
    private TextField competences;

    private String cvFilePath;

    public void initialize() {
        // Méthode initiale de votre application, peut-être pas nécessaire ici
    }

    public void initData(String competences, String cvFilePath) {
        this.competences.setText(competences);
        this.cvFilePath = cvFilePath;
    }

    public void afficherPdf() {
        System.out.println("Chemin du fichier PDF : " + cvFilePath);
        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(cvFilePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.err.println("Le fichier PDF n'existe pas : " + cvFilePath);
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de l'ouverture du fichier PDF : " + e.getMessage());
            }
        }
    }

}
