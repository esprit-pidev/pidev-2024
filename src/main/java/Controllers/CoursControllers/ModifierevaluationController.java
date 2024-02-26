package controllers;

import entities.Cours;
import entities.Evaluation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.CoursService;
import service.EvaluationService;

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
       // naviguezVersAffichage(null);
    }

}
