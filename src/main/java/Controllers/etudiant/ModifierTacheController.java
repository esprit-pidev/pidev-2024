package Controllers.etudiant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.services.projectService.TacheService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class ModifierTacheController {

    private int tacheId;
    private String desc;
    private Date ded;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label deadlineLabel;

    @FXML
    private Pane inputPane;
    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker deadlinePicker;
    private String newDescription;
    private LocalDate newDeadlineValue;
    @FXML
    private Button update;

    public void setTacheId(int tacheId) {
        this.tacheId = tacheId;
        setDescriptionAndDeadline(desc, ded);
    }

    public void setDescriptionAndDeadline(String description, Date deadline) {
        TacheService tacheService = new TacheService();
        String[] det = tacheService.getDescriptionAndDeadlineById(tacheId);
        desc = det[0]; // Mettre à jour la description
        ded = Date.valueOf(det[1]); // Mettre à jour la date limite
        descriptionLabel.setText("Description : " + desc);
        deadlineLabel.setText("Deadline : " + ded);
    }

    @FXML
    private void modifierDescription() {
        // Effacez le contenu précédent du Pane
        inputPane.getChildren().clear();

        // Utilisez le champ descriptionField existant de la classe
        descriptionField = new TextField();
        descriptionField.setPromptText("Nouvelle description");
        descriptionField.setPrefHeight(53.0);
        descriptionField.setPrefWidth(335.0);
        descriptionField.setLayoutX((inputPane.getWidth() - descriptionField.getPrefWidth()) / 2);
        descriptionField.setLayoutY((inputPane.getHeight() - descriptionField.getPrefHeight()) / 2);
        descriptionField.setStyle("-fx-border-color: #9F1C00;");
        inputPane.getChildren().add(descriptionField);
        newDescription = descriptionField.getText();
    }

    @FXML
    private void modifierDeadline() {
        // Effacez le contenu précédent du Pane
        inputPane.getChildren().clear();

        // Utilisez le champ deadlinePicker existant de la classe
        deadlinePicker = new DatePicker();
        deadlinePicker.setPromptText("Deadline");
        deadlinePicker.setPrefHeight(54.0);
        deadlinePicker.setPrefWidth(314.0);
        deadlinePicker.setLayoutX((inputPane.getWidth() - deadlinePicker.getPrefWidth()) / 2);
        deadlinePicker.setLayoutY((inputPane.getHeight() - deadlinePicker.getPrefHeight()) / 2);
        deadlinePicker.setStyle("-fx-border-color: #9F1C00;");
        deadlinePicker.setValue(LocalDate.now());
        inputPane.getChildren().add(deadlinePicker);
        newDeadlineValue = deadlinePicker.getValue();
    }

    @FXML
    public void update(MouseEvent event) {
        TacheService tacheService = new TacheService();
        if (descriptionField != null && deadlinePicker == null) {
            newDescription = descriptionField.getText();
            newDeadlineValue = ded.toLocalDate();
            Date dedli = Date.valueOf(newDeadlineValue);
            tacheService.updatedescded(newDescription,dedli,tacheId);
        } else if (deadlinePicker != null && descriptionField == null) {
            newDescription = desc;
            newDeadlineValue = deadlinePicker.getValue();
            Date dedli = Date.valueOf(newDeadlineValue);
            tacheService.updatedescded(newDescription,dedli,tacheId);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Champs non remplis");
            errorAlert.setContentText("Veuillez remplir l'un des champs.");
            errorAlert.showAndWait();
            return;
        }
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText("Tâche modifiée avec succès");
        successAlert.setContentText("Cliquez sur OK pour retourner à la page d'accueil.");
        successAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Naviguer vers la HomePage.fxml
                Stage stage = (Stage) descriptionField.getScene().getWindow();
                stage.close(); // Fermer la fenêtre actuelle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/HomePage.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage homeStage = new Stage();
                homeStage.setScene(new Scene(root));
                homeStage.show();
            }
        });
    }}





