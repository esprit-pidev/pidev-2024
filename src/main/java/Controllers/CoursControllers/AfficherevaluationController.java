package Controllers.CoursControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.services.coursServices.EvaluationService;

import java.io.IOException;
import java.util.List;

public class AfficherevaluationController {
    private final EvaluationService es = new EvaluationService();
private Evaluation selectedEval;
    @FXML
    private Button ajoutereval;

    @FXML
    private Button modifiereval;

    @FXML
    private Button supprimereval;
    @FXML
    private VBox vboxList;
    @FXML
    private Button AjouterQuestion;

    @FXML
    void initialize()  {
        vboxList.getChildren().clear();
        List<Evaluation> evaluations = es.obtenirToutesLesevaluation();

        int col=1;
        for (Evaluation e : evaluations) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evaluationCard.fxml"));

            try {
                // Load the FXML and get the root node
                AnchorPane evaluationCard = loader.load();

                EvaluationCardController cardController = loader.getController();
                cardController.setEvaluation(e);
                evaluationCard.addEventHandler(CardClickedEvent.CARD_CLICKED, event -> {
                    Evaluation clickedEvaluation = cardController.getEvaluation();
                    selectedEval=clickedEvaluation;
                    modifiereval.setDisable(false);
                    supprimereval.setDisable(false);

                    // Notify the parent or add your logic here
                });                // Check the value of col and create a new HBox if needed
                if (col == 1) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);
                    hBox.getChildren().add(evaluationCard);
                    vboxList.getChildren().add(hBox);
                } else {
                    // Assuming col can be 2 or 3 here, adjust conditions as needed
                    HBox lastHBox = (HBox) vboxList.getChildren().get(vboxList.getChildren().size() - 1);
                    lastHBox.getChildren().add(evaluationCard);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Increment the col value
            col = (col % 3) + 1;
        }


    }


    @FXML
    public void navigate(ActionEvent actionEvent) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Ajouterevaluation.fxml"));
                ajoutereval.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }



    }

    @FXML
    public void navigateToEdit(ActionEvent actionEvent) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifierevaluation.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierevaluationController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView

            // Check if an item is actually selected
            if (selectedEval != null) {
                System.out.println("Selected evaluation"+selectedEval);
                // If an item is selected, pass it to the controller of the next scene
                controller.setEvaluation(selectedEval);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            ajoutereval.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void delete(ActionEvent actionEvent)
        {
            if(selectedEval!=null) {
                es.supprimerevaluation(selectedEval.getId());
            }
            initialize();
        }
    @FXML
    void navigatetoajouterQuestion(ActionEvent event) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterQuestion.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterQuestionController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView

            // Check if an item is actually selected
            if (selectedEval != null) {
                System.out.println("Selected evaluation"+selectedEval);
                // If an item is selected, pass it to the controller of the next scene
                controller.setEvaluation(selectedEval);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            ajoutereval.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    }





