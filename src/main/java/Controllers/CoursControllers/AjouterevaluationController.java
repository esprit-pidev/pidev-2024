
package controllers;

import entities.Cours;
import entities.Evaluation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.CoursService;
import service.EvaluationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterevaluationController implements Initializable {

    @FXML
    private TextField titre;
    private  Cours cours;


    private final EvaluationService es =new EvaluationService();
    private final CoursService cs=new CoursService();
    @FXML
    private ComboBox<Cours> listCours;

    public  void setCour(Cours cours){
        this.cours=cours;
        listCours.setValue(cours);
        listCours.setDisable(true);
//        this.nomcours.setText(cours.getNomcours());

    }
    @FXML
    public void Ajouter(ActionEvent actionEvent) {
        es.ajouter(new Evaluation(titre.getText(), listCours.getValue().getCours_Id()));
        navigeuzVersAfficher();

    }

    public void navigeuzVersAfficher(){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/Afficherevaluation.fxml"));
                listCours.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       System.out.print("Im here ");
        listCours.getItems().addAll(cs.obtenirToutesLesCours());
    }

}