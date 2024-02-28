package Controllers.CoursControllers;

import tn.esprit.entities.Cours.Cours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import tn.esprit.services.coursServices.CoursService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class AffichercourController {
    private ObservableList<Cours> observableList;
    private final CoursService cs = new CoursService();
    @FXML
    private ListView<Cours> listviewcours;
    @FXML
    private Button buttonReturn;
    private Cours SelectedCour;
    private Cours selectedCour;
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
            observableList = FXCollections.observableList(cours);
            listviewcours.setItems(observableList);

            listviewcours.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedCour = newValue;
                    addEvaluationButton.setDisable(false);
                    deletebuttton.setDisable(false);
                    editButton.setDisable(false);
                }
            });
        } catch (SQLException e) {
            showAlert("Error", e.getMessage());
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Accueil .fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}



