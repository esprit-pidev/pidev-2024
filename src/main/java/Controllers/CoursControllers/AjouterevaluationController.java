
package Controllers.CoursControllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.services.coursServices.CoursService;
import tn.esprit.services.coursServices.EvaluationService;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterevaluationController implements Initializable {

    @FXML
    private TextField titre;
    private Cours cours;


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
      //  es.ajouter(new Evaluation(titre.getText(), listCours.getValue().getCours_Id()));
        String evaluationTitre = titre.getText().trim(); // Trim to remove leading and trailing whitespaces

        if (evaluationTitre.isEmpty()) {
            showAlert("Title Required", "Please enter a title for the evaluation.");
        } else {
            es.ajouter(new Evaluation(evaluationTitre, listCours.getValue().getCours_Id()));
        navigeuzVersAfficher();

    }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
   @FXML
    public void navigeuzVersAffichercours(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
            listCours.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

