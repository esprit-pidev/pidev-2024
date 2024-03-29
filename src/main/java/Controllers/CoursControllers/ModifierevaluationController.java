package Controllers.CoursControllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.services.coursServices.CoursService;
import tn.esprit.services.coursServices.EvaluationService;

import java.io.IOException;


public class ModifierevaluationController {

    @FXML
    private TextField titre;
    private Evaluation evaluation;
    private final EvaluationService es=new EvaluationService();
    private final CoursService cs=new CoursService();
    @FXML
    private TextField nomCour;

    @Deprecated
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        // Update UI components with the Organisateur's data
        titre.setText(evaluation.getTitre());
        Cours cours=cs.obtenirCours(evaluation.getCours_Id());
        nomCour.setText(cours.getNomcours());


        // Set other fields as needed
    }
    @FXML
    void modifiereval(ActionEvent event)
    {
        evaluation.setTitre(titre.getText());
        es.modifierevaluation(evaluation);
        naviguezVersAfficherevaluation(event);
    }
    @FXML
    public void naviguezVersAfficherevaluation(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficherevaluation.fxml"));
            titre.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
