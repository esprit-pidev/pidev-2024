package Controllers.activiteController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.User.RoleName;
import tn.esprit.services.extrascolaireService.PublicationService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;
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
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();
    @FXML
    private Button ajouterPublicationButton; // Déclarez le bouton ici

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

        // Obtenir le rôle de l'utilisateur connecté
        String userRole = String.valueOf(userLoggedIn.getRole());

        // Ajoutez cette vérification pour rendre les boutons visibles uniquement pour le rôle CLUB_RH
        if ("CLUB_RH".equals(userRole)) {
            // Afficher les boutons qui sont autorisés pour CLUB_RH
            ajouterPublicationButton.setVisible(true);
            // Ajoutez des conditions similaires pour les autres boutons si nécessaire
        } else {
            // Masquer les boutons qui ne sont pas autorisés
            ajouterPublicationButton.setVisible(false);
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
    private  class PublicationListCell extends ListCell<String> {
        int idUtilisateurConnecte = userLoggedIn.getId();
        private RoleName roleUtilisateurConnecte;  // Déclaration de la variable de rôle // Déclaration de la variable de rôle

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
                // Récupérer le rôle de l'utilisateur connecté
                roleUtilisateurConnecte = userLoggedIn.getRole();

                // Récupérer l'identifiant de l'utilisateur associé à la publication
                int idUtilisateurPublication = PublicationService.obtenirIdUtilisateurParPublication(obtenirIdDeLaPublication(contenu));

                // Vérifier si l'utilisateur connecté est l'auteur de la publication ou a le rôle CLUB_RH
                if ((idUtilisateurConnecte == idUtilisateurPublication) || RoleName.CLUB_RH.equals(roleUtilisateurConnecte)) {
                    // Effacer les enfants existants avant d'ajouter de nouveaux enfants
                    buttonsContainer.getChildren().clear();

                    // Afficher les boutons seulement si l'utilisateur connecté est l'auteur et a le rôle CLUB_RH
                    buttonsContainer.getChildren().addAll(modifierButton, supprimerButton);

                    modifierButton.setOnAction(event -> {
                        // Récupérer l'identifiant de la publication associée à la cellule
                        int idPublicationAModifier = obtenirIdDeLaPublication(contenu);

                        // Récupérer l'identifiant de l'utilisateur connecté
                        int idUtilisateurConnecte = userLoggedIn.getId();

                        // Vérifier si l'utilisateur connecté est l'auteur de la publication
                        if (idUtilisateurConnecte == idUtilisateurPublication) {
                            // Récupérer le contenu actuel de la publication
                            String contenuActuel = contenu;

                            // Créer une boîte de dialogue personnalisée avec une zone de texte (TextArea)
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Modifier la publication");
                            dialog.setHeaderText("Modifier le contenu de la publication");

                            // Créer une zone de texte (TextArea) avec le contenu actuel
                            TextArea textArea = new TextArea(contenuActuel);
                            textArea.setWrapText(true);
                            textArea.setMaxWidth(Double.MAX_VALUE);
                            textArea.setMaxHeight(Double.MAX_VALUE);

                            // Ajouter la zone de texte à la boîte de dialogue
                            dialog.getDialogPane().setContent(textArea);

                            // Créer les boutons OK et Annuler
                            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOK, buttonTypeCancel);

                            // Définir le résultat du dialogue lorsque le bouton OK est cliqué
                            dialog.setResultConverter(buttonType -> {
                                if (buttonType == buttonTypeOK) {
                                    return textArea.getText(); // Renvoyer le nouveau contenu saisi
                                }
                                return null;
                            });

                            // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur
                            Optional<String> result = dialog.showAndWait();

                            // Si l'utilisateur appuie sur OK, mettez à jour la publication dans la base de données et actualisez la liste
                            result.ifPresent(nouveauContenu -> {
                                // Assurez-vous que vous avez une instance de PublicationService accessible ici
                                PublicationService publicationService = new PublicationService();

                                // Appelez votre méthode de mise à jour avec l'ID de la publication et le nouveau contenu
                                publicationService.modifierContenuPublication(idPublicationAModifier, nouveauContenu);

                                // Actualiser la liste (vous pouvez avoir une méthode dédiée dans votre classe pour cela)
                                actualiserListe();
                            });
                        } else {
                            // Afficher un message indiquant que l'utilisateur n'est pas autorisé à modifier cette publication
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous n'êtes pas autorisé à modifier cette publication.");
                            alert.showAndWait();
                        }
                    });



                    supprimerButton.setOnAction(event -> {
                        // Récupérer l'identifiant de l'utilisateur connecté
                        int idUtilisateurConnecte = userLoggedIn.getId();

                        // Récupérer l'identifiant de la publication associée à la cellule
                        int idPublicationASupprimer = obtenirIdDeLaPublication(contenu);

                        // Vérifier si l'utilisateur connecté est l'auteur de la publication
                        if (idUtilisateurConnecte == idUtilisateurPublication) {
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
                        } else {
                            // Afficher un message indiquant que l'utilisateur n'est pas autorisé à supprimer cette publication
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous n'êtes pas autorisé à supprimer cette publication.");
                            alert.showAndWait();
                        }
                    });
                } else {
                    // Si l'utilisateur n'est pas l'auteur de la publication ni n'a pas le rôle CLUB_RH, ne pas afficher les boutons
                    buttonsContainer.getChildren().clear();
                }
            }
        }}}
