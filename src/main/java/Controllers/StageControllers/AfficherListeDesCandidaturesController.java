package Controllers.StageControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.CandidatureService;
import tn.esprit.services.stageServices.OffreService;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javafx.scene.control.Alert;
import org.controlsfx.control.Notifications;
public class AfficherListeDesCandidaturesController {
    private final CandidatureService candidatureService = new CandidatureService();
    private ObservableList<Candidature> candidatureData = FXCollections.observableArrayList();

    @FXML
    private ListView<Candidature> candidatureListView;

    public void initialize(Offre offre) {
        List<Candidature> candidatures = candidatureService.getCandidaturesByOffre(offre);
        candidatureData.clear();

        if (candidatures.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune candidature");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune candidature à afficher pour cette offre.");
            alert.showAndWait();
        } else {
            candidatureData.addAll(candidatures);
            candidatureListView.setItems(candidatureData);
        }
        // Ajoutez un gestionnaire d'événements au champ de recherche pour filtrer la liste

    }
    @FXML
    void afficherDetailsCandidature() {
        Candidature candidatureSelectionnee = candidatureListView.getSelectionModel().getSelectedItem();
        if (candidatureSelectionnee != null) {
            afficherDetails(candidatureSelectionnee);
        } else {
            showAlert("Sélectionnez une candidature", "Veuillez sélectionner une candidature pour afficher ses détails.");
        }
    }

    private void afficherDetails(Candidature candidature) {
        OffreService offreService = new OffreService();
        Offre offre = offreService.getById(candidature.getOffre_id());
        if (offre != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails de la candidature");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Titre Offre: " + offre.getTitre() + "\n" +
                            "Compétences: " + candidature.getCompetences() + "\n" +
                            "Statut: " + candidature.getStatus() + "\n" +
                            "Date: " + candidature.getDate() + "\n" +
                            "CV: " + candidature.getCv()
            );
            alert.showAndWait();

            // Télécharger le PDF
            try {
                downloadPdf(candidature.getCv());
            } catch (IOException e) {
                showAlert("Erreur", "Impossible de télécharger le fichier PDF.");
            }
        } else {
            showAlert("Erreur", "L'offre associée à cette candidature n'existe pas.");
        }
    }
    private void downloadPdf(String pdfFilePath) throws IOException {
        if (pdfFilePath == null || pdfFilePath.isEmpty()) {
            showAlert("Erreur", "Chemin du fichier PDF invalide.");
            return;
        }

        File pdfFile = new File(pdfFilePath);
        if (!pdfFile.exists()) {
            showAlert("Erreur", "Le fichier PDF n'existe pas à l'emplacement spécifié.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le CV");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        File saveFile = fileChooser.showSaveDialog(null);

        if (saveFile != null) {
            try {
                Files.copy(pdfFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Fichier PDF téléchargé avec succès !");

                // Afficher une notification
                if (SystemTray.isSupported()) {
                    try {
                        SystemTray tray = SystemTray.getSystemTray();
                        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                        TrayIcon trayIcon = new TrayIcon(image, "Téléchargement réussi");
                        trayIcon.setImageAutoSize(true);
                        tray.add(trayIcon);
                        trayIcon.displayMessage("Téléchargement réussi", "Le fichier PDF a été téléchargé avec succès !", TrayIcon.MessageType.INFO);
                    } catch (AWTException e) {
                        System.err.println("Erreur lors de la création de la notification système : " + e.getMessage());
                        showAlert("Erreur", "Impossible de créer la notification système.");
                    }
                } else {
                    showAlert("Erreur", "La notification système n'est pas supportée sur ce système.");
                }

            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Impossible de télécharger le fichier PDF.", e);
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
