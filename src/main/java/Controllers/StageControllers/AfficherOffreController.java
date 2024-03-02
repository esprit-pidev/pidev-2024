package Controllers.StageControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.OffreService;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.List;

public class AfficherOffreController {
    private final OffreService OS = new OffreService();

    @FXML
    private TextField competences;

    @FXML
    private TextField date;

    @FXML
    private TextField description;

    @FXML
    private TextField nbr;

    @FXML
    private ListView<Offre> offresListView;

    @FXML
    private TextField titre;

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
            List<Offre> offre = OS.afficher(); // Récupérer la liste des offres à partir du service
            ObservableList<Offre> items = FXCollections.observableList(offres);
            offresListView.setItems(items);


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
    @FXML
    void fillforum(MouseEvent event) {
        Offre offreSelectionnee = offresListView.getSelectionModel().getSelectedItem();
        if (offreSelectionnee != null) {
            titre.setText(offreSelectionnee.getTitre());
            competences.setText(offreSelectionnee.getCompetences());
            description.setText(offreSelectionnee.getDescription());
            nbr.setText(String.valueOf(offreSelectionnee.getNbr()));
            date.setText(offreSelectionnee.getDate().toString());
        }
    }

    @FXML
    void modifierOffre(ActionEvent event) {
        String titreText = titre.getText();
        String competencesText = competences.getText();
        String descriptionText = description.getText();
        String nbrText = nbr.getText();

        if (titreText.isEmpty() || competencesText.isEmpty() || descriptionText.isEmpty() || nbrText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Offre offreSelectionnee = offresListView.getSelectionModel().getSelectedItem();

        if (offreSelectionnee == null) {
            showAlert("Erreur", "Aucune offre sélectionnée.");
            return;
        }

        offreSelectionnee.setTitre(titreText);
        offreSelectionnee.setCompetences(competencesText);
        offreSelectionnee.setDescription(descriptionText);

        try {
            int nbrValue = Integer.parseInt(nbrText);
            offreSelectionnee.setNbr(nbrValue);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le champ 'nbr' doit être un entier.");
            return;
        }

        OS.modifier(offreSelectionnee);
        showAlert("Modification réussie", "L'offre de stage a été modifiée avec succès.");
        refresh(); // Actualiser l'affichage des offres
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void supprimerOffre(ActionEvent event) {
        Offre offre = offresListView.getSelectionModel().getSelectedItem();
        if (offre != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cette offre de stage ?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.YES) {
                OS.supprimer(offre.getId());
                showAlert("Suppression réussie", "L'offre de stage a été supprimée avec succès.");
                refresh(); // Actualiser la liste des offres après la suppression
            }
        }
    }

    public void refresh() {
        List<Offre> offres = OS.afficher();
        if (offres.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucune offre");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a aucune offre à afficher.");
            alert.showAndWait();
        } else {
            ObservableList<Offre> items = FXCollections.observableArrayList(offres);
            offresListView.setItems(items);
            afficherDetails(offres.get(0));
        }
    }
    @FXML
    void naviguerversAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterOffre.fxml"));
            Parent root = loader.load();

            // Utiliser le contrôleur associé à AjouterOffre.fxml
            AjouterOffreController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguerversAfficherListeDesCandidatures(ActionEvent event) {
        Offre selectedOffre = offresListView.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherListeDesCandidatures.fxml"));
                Parent root = loader.load();

                AfficherListeDesCandidaturesController controller = loader.getController();
                controller.initialize(selectedOffre);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une offre.");
        }
    }

}


