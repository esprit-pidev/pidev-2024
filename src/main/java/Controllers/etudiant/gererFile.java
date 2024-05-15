package Controllers.etudiant;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.esprit.entities.project.fichier;
import tn.esprit.services.projectService.FichierService;

import java.util.List;

public class gererFile {
    private int idUser;
    private int id;
    @FXML
    private Pane listf;
    FichierService fichierService = new FichierService();
    fichier fichier = new fichier();

    public void setDet(int idUser) {
        this.idUser = idUser;
        List<fichier> fichiers = fichierService.getFileByUser(idUser);
        afficherFichiers(fichiers, idUser);
    }

    private void afficherFichiers(List<fichier> fichiers, int idUser) {
        List<fichier> files = fichierService.getFileByUser(idUser);
        double espaceEntreFichiers = 10.0;
        listf.getChildren().clear();
        if (fichiers != null) {
            VBox containerFichiersVBox = new VBox();
            containerFichiersVBox.setSpacing(espaceEntreFichiers);
            containerFichiersVBox.setAlignment(Pos.CENTER);
            for (fichier file : files) {
                if (file != null) {
                    System.out.println(file);
                    String imagePath = "project/images/new.png";
                    ImageView imageView = new ImageView(new Image(imagePath));
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(70);
                    Label nameLabel = new Label("Nom: " + file.getNom());
                    Label dateLabel = new Label("Date " + file.getDate_ajout());
                    VBox fileBox = new VBox(imageView, nameLabel, dateLabel);
                    fileBox.setPrefHeight(100);
                    fileBox.setPrefWidth(150);
                    fileBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #9F1C00;");
                    fileBox.setAlignment(Pos.CENTER);

                    // Ajout de la case à cocher pour supprimer le fichier
                    CheckBox checkBox = new CheckBox("Supprimer");
                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de suppression");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez-vous vraiment supprimer le fichier " + file.getNom() + " ?");

                            // Options des boutons de confirmation
                            ButtonType buttonTypeOui = new ButtonType("Oui");
                            ButtonType buttonTypeNon = new ButtonType("Non");
                            alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

                            // Gérer la réponse de l'utilisateur
                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == buttonTypeOui) {
                                    // Si l'utilisateur clique sur Oui, supprimer le fichier
                                    fichierService.supprimerFichier(file.getId());
                                    // Mettre à jour l'affichage
                                    afficherFichiers(fichiers, idUser);
                                } else {
                                    // Si l'utilisateur clique sur Non, décocher la case à cocher
                                    checkBox.setSelected(false);
                                }
                            });
                        }
                    });
                    fileBox.getChildren().add(checkBox);

                    containerFichiersVBox.getChildren().add(fileBox);
                }
            }
            listf.getChildren().add(containerFichiersVBox);

            double x = (listf.getWidth() - containerFichiersVBox.getWidth()) / 2;
            double y = (listf.getHeight() - containerFichiersVBox.getHeight()) / 2;
            containerFichiersVBox.setLayoutX(x);
            containerFichiersVBox.setLayoutY(y);
        }
    }

}
