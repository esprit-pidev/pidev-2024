package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.services.extrascolaireService.PublicationService;
import tn.esprit.tools.MyDB;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AfficherPublicationsController {

    @FXML
    private ListView<String> publicationsListView;

    private Connection conn = MyDB.getInstance().getCnx();

    @FXML
    void AjouterPublication(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface de paiement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PublierPublication.fxml"));

            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(scene);

            // Montrer la nouvelle fenêtre
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions comme le chargement du FXML échoue
        }

    }

    @FXML
    private void initialize() {
        if (conn == null) {
            System.err.println("Erreur : La connexion à la base de données n'est pas établie.");
            return;
        }

        List<String> contenusPublications = PublicationService.obtenirTousLesContenusPublications();

        // Créer une ObservableList à partir de la liste de contenus
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.setAll(contenusPublications);

        publicationsListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new PublicationListCell();
            }
        });

        // Ajouter les contenus à la ListView
        publicationsListView.setItems(observableList);
    }

    // Classe de cellule personnalisée
    private static class PublicationListCell extends ListCell<String> {
        private final VBox container = new VBox();
        private final HBox buttonsContainer = new HBox();
        private final Label contenuLabel = new Label();
        private final Label dateLabel = new Label();
        private final Button modifierButton = new Button("Modifier");
        private final Button supprimerButton = new Button("Supprimer");
        private void actualiserListe() {
            // Assurez-vous d'avoir une instance de PublicationService accessible ici
            PublicationService publicationService = new PublicationService();

            // Obtenez la liste mise à jour des contenus des publications
            List<String> contenusPublications = publicationService.obtenirTousLesContenusPublications();

            // Créez une ObservableList à partir de la liste mise à jour
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.setAll(contenusPublications);

            // Actualisez la ListView avec la nouvelle liste d'observables
            ((ListView<String>) getListView()).setItems(observableList);
        }

        private int obtenirIdDeLaPublication(String contenu) {
            // Assurez-vous d'avoir une instance de PublicationService accessible ici
            PublicationService publicationService = new PublicationService();

            // Utilisez la méthode du service pour obtenir l'ID en fonction du contenu
            int idPublication = publicationService.obtenirIdPublicationParContenu(contenu);

            // Retournez l'ID obtenu
            return idPublication;
        }


        public PublicationListCell() {
            // Définir le style des boutons Modifier et Supprimer
            modifierButton.setStyle("-fx-background-color: #526779; -fx-text-fill: white;");
            supprimerButton.setStyle("-fx-background-color: #A3232B; -fx-text-fill: white;"); // Rouge
            buttonsContainer.getChildren().addAll(modifierButton, supprimerButton);
            container.getChildren().addAll(contenuLabel, dateLabel, buttonsContainer);
        }

        @Override
        protected void updateItem(String contenu, boolean empty) {
            super.updateItem(contenu, empty);

            if (empty || contenu == null) {
                setGraphic(null);
            } else {
                // Mettez à jour l'apparence de la cellule avec le contenu de la publication
                contenuLabel.setText(contenu);
                setGraphic(container);
                // Ajouter la date d'ajout
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateAjout = dateFormat.format(new Date());
                dateLabel.setText("Date d'ajout : " + dateAjout);

                // Appliquer le fond gris et la bordure noire
                container.setStyle("-fx-background-color: #F0F0F0;  -fx-border-width: 2;");

                // Gérer les actions des boutons (vous devez définir les gestionnaires d'événements appropriés)
                modifierButton.setOnAction(event -> {
                    // Récupérer l'identifiant de la publication associée à la cellule
                    int idPublicationAModifier = obtenirIdDeLaPublication(contenu);

                    // Récupérer le contenu actuel de la publication
                    String contenuActuel = contenu;

                    // Afficher une boîte de dialogue pour la modification du contenu
                    TextInputDialog dialog = new TextInputDialog(contenuActuel);
                    dialog.setTitle("Modifier la publication");
                    dialog.setHeaderText("Modifier le contenu de la publication");
                    dialog.setContentText("Nouveau contenu:");

                    // Obtenez la nouvelle valeur du contenu de la publication
                    Optional<String> result = dialog.showAndWait();

                    // Si l'utilisateur appuie sur OK, mettez à jour la publication dans la base de données et actualisez la liste
                    if (result.isPresent()) {
                        // Assurez-vous que vous avez une instance de PublicationService accessible ici
                        PublicationService publicationService = new PublicationService();

                        // Appelez votre méthode de mise à jour avec l'ID de la publication et le nouveau contenu
                        publicationService.modifierContenuPublication(idPublicationAModifier, result.get());

                        // Actualiser la liste (vous pouvez avoir une méthode dédiée dans votre classe pour cela)
                        actualiserListe();
                    }
                });



                supprimerButton.setOnAction(event -> {
                    // Récupérer l'identifiant de la publication associée à la cellule
                    int idPublicationASupprimer = obtenirIdDeLaPublication(contenu);

                    // Afficher une boîte de dialogue pour la confirmation de la suppression
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation de suppression");
                    alert.setHeaderText("Supprimer la publication");
                    alert.setContentText("Êtes-vous sûr de vouloir supprimer cette publication?");

                    // Obtenez la réponse de l'utilisateur
                    Optional<ButtonType> result = alert.showAndWait();

                    // Si l'utilisateur appuie sur OK, supprimer la publication de la base de données et actualiser la liste
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Assurez-vous que vous avez une instance de PublicationService accessible ici
                        PublicationService publicationService = new PublicationService();

                        // Appelez votre méthode de suppression avec l'ID de la publication à supprimer
                        publicationService.supprimerPublication(idPublicationASupprimer);

                        // Actualiser la liste (vous pouvez avoir une méthode dédiée dans votre classe pour cela)
                        actualiserListe();
                    }
                });

            }
        }


    }
}