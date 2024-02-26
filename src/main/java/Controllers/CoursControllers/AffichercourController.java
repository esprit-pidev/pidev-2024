package controllers;

import entities.Cours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import service.CoursService;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffichercourController {
    private final CoursService cs = new CoursService();
    @FXML
    private ListView<Cours> listviewcours;
    private Cours SelectedCour;
    @FXML
    private Button deletebuttton;
    @FXML
    private Button editButton;
    @FXML
    private Button addEvaluationButton;

    @FXML
    void initialize() {
        try {
            List<Cours> cours = cs.getcourteacher(1);
            ObservableList<Cours> observableList = FXCollections.observableList(cours);
            listviewcours.setItems(observableList);
            listviewcours.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Perform actions with the selected item (newValue)
                    System.out.println("Selected Item: " + newValue);
                    SelectedCour = (Cours) newValue;
                addEvaluationButton.setDisable(false);
                deletebuttton.setDisable(false);
                editButton.setDisable(false);

                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    public void naviguezVersAjoutercours(ActionEvent actionEvent) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Ajoutercour.fxml"));
                listviewcours.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        @FXML
        void naviguezVersmodifiercour (ActionEvent event){

            try {
                // Correctly create an FXMLLoader instance pointing to your FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifiercour.fxml"));

                // Load the FXML and get the root node in one step
                Parent root = loader.load();

                // Now that the FXML is loaded, get the controller
                ModifiercourController controller = loader.getController();

                // Here, you retrieve the selected item from your ListView

                // Check if an item is actually selected
                if (SelectedCour != null) {
                    // If an item is selected, pass it to the controller of the next scene
                    controller.setCours(SelectedCour);
                } else {
                    // Handle the case where no item is selected (optional)
                    System.out.println("No item selected");
                    return; // Optionally return to avoid changing scenes when no item is selected
                }

                // Finally, set the scene's root to switch to the new view
                listviewcours.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    @FXML
    void deletecours(ActionEvent event) {
        if ( listviewcours.getSelectionModel().getSelectedItem() != null) {
            Cours newValue =  listviewcours.getSelectionModel().getSelectedItem();
            cs.supprimerCours(newValue.getCours_Id());
            initialize();
        }
    }

    @FXML
    public void addEvaluation(ActionEvent actionEvent) {
       try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouterevaluation.fxml"));

        // Load the FXML and get the root node in one step
        Parent root = loader.load();

        // Now that the FXML is loaded, get the controller
        AjouterevaluationController controller = loader.getController();

        // Here, you retrieve the selected item from your ListView

        // Check if an item is actually selected
        if (SelectedCour != null) {
            // If an item is selected, pass it to the controller of the next scene
            controller.setCour(SelectedCour);
            listviewcours.getScene().setRoot(root);

        } else {
            // Handle the case where no item is selected (optional)
            System.out.println("No item selected");
            return; // Optionally return to avoid changing scenes when no item is selected
        }

        // Finally, set the scene's root to switch to the new view
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }

    }
}


