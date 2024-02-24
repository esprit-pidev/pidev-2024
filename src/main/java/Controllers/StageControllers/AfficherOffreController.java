package Controllers.StageControllers;

import tn.esprit.entities.stage.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import tn.esprit.services.stageServices.OffreService;

import java.sql.SQLException;
import java.util.List;

public class AfficherOffreController {
    private final OffreService OS = new OffreService();

    @FXML
    private ListView<String> offresListView;

    @FXML
    private Label competences;

    @FXML
    private Label date;

    @FXML
    private Label description;

    @FXML
    private Label nbr;

    @FXML
    private Label titre;

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
            // Créer une liste observable de titres d'offres
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Offre offre : offres) {
                items.add(offre.getTitre());
            }

            // Afficher les titres des offres dans la ListView
            offresListView.setItems(items);

            // Définir un gestionnaire d'événements pour afficher les détails de l'offre sélectionnée
            offresListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int selectedIndex = offresListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < offres.size()) {
                    afficherDetails(offres.get(selectedIndex));
                }
            });

            // Afficher les détails de la première offre dans la liste
            afficherDetails(offres.get(0));
        }
    }

    private void afficherDetails(Offre offre) {
        competences.setText(offre.getCompetences());
        titre.setText(offre.getTitre());
        date.setText(String.valueOf(offre.getDate()));
        description.setText(offre.getDescription());
        nbr.setText(String.valueOf(offre.getNbr()));
    }
}
