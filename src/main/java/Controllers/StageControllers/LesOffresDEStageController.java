package Controllers.StageControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.OffreService;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LesOffresDEStageController {

    @FXML
    private ListView<Offre> offresListView;
    private final OffreService OS = new OffreService();
    @FXML
    private TextField searchField;

    private ObservableList<Offre> offresObservableList;

    @FXML
    public void initialize() {
        List<Offre> offres = OS.afficher(); // Récupérer la liste des offres à partir du service
        if (offres.isEmpty()) {
            // Afficher un message si la liste est vide
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune offre");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune offre à afficher.");
            alert.showAndWait();
        } else {
            offresObservableList = FXCollections.observableList(offres);
            offresListView.setItems(offresObservableList);

            // Définir un gestionnaire d'événements pour afficher les détails de l'offre sélectionnée
            offresListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int selectedIndex = offresListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < offresObservableList.size()) {
                    // Naviguer vers la vue d'ajout de candidature
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCandidature.fxml"));
                        Parent root = loader.load();

                        AjouterCandidatureController controller = loader.getController();
                        controller.initData(offresObservableList.get(selectedIndex));

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filter(newValue));
    }

    private void filter(String keyword) {
        ObservableList<Offre> filteredList = FXCollections.observableArrayList();
        for (Offre offre : offresObservableList) {
            if (offre.getTitre().toLowerCase().startsWith(keyword.toLowerCase())) {
                filteredList.add(offre);
            }
        }
        offresListView.setItems(filteredList);
    }

    @FXML
    void rechercherOffre() {
        String titre = searchField.getText();
        OffreService offreService = new OffreService();
        List<Offre> offres = offreService.searchByTitre(titre);
        // Utilisez la liste des offres trouvées pour effectuer les actions nécessaires
        if (!offres.isEmpty()) {
            // Faites ce que vous voulez avec les offres trouvées
            for (Offre offre : offres) {
                System.out.println("Offre trouvée : " + offre.getTitre());
            }
        } else {
            // Aucune offre trouvée
            System.out.println("Aucune offre trouvée avec le titre : " + titre);
        }
    }

}
