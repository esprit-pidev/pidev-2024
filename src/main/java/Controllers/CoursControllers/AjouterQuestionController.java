package controllers;

import entities.Evaluation;
import entities.Option;
import entities.Questions;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import service.OptionService;
import service.QuestionService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterQuestionController implements Initializable {
    @javafx.fxml.FXML
    private TextField question;
    @javafx.fxml.FXML
    private CheckBox isTrueOption;
    @javafx.fxml.FXML
    private Label evaluationLabel;
    @javafx.fxml.FXML
    private TextField option;
    private Evaluation evaluation;
    private Questions question_data = new Questions();
    private List<Option> listeOption=new ArrayList<>();
    private QuestionService qs=new QuestionService();
    private OptionService os=new OptionService();
    @javafx.fxml.FXML
    private Button openAddOptionButton;
    @javafx.fxml.FXML
    private Button addOptionButton;
    @javafx.fxml.FXML
    private AnchorPane optionForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            evaluation=new Evaluation();
            evaluation.setId(1);
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;

    }

    @javafx.fxml.FXML
    public void addOption(ActionEvent actionEvent) {
        String optionValue=option.getText();
        if(optionValue.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Setting the alert content text
            alert.setContentText("option field is required!");

            // Showing the alert
            alert.showAndWait();
        }else{
            boolean checked=isTrueOption.isSelected();
            if(listeOption.stream().anyMatch((e)->e.getIs_correct())&& checked){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                // Setting the alert content text
                alert.setContentText("only one option can be checked as a true value");

                // Showing the alert
                alert.showAndWait();
            }else{
                Option op=new Option(optionValue,checked);
                listeOption.add(op);
                option.setText("");
                isTrueOption.setSelected(false);
            }
        }
    }

    @javafx.fxml.FXML
    public void saveData(ActionEvent actionEvent) {
        if(!listeOption.stream().anyMatch((o)->o.getIs_correct())){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Setting the alert content text
            alert.setContentText("Please add at least one correct response.");

            // Showing the alert
            alert.showAndWait();
        }else {

            question_data.setEvaluation_id(evaluation.getId());
            question_data = qs.ajouterWithRetunQuestion(question_data);
            for (Option o : listeOption) {
                o.setQuestion_id(question_data.getId());
                os.ajouteroption(o);

            }
            listeOption.clear();
            question.setText("");
        }


    }

    @javafx.fxml.FXML
    public void addQuestion(ActionEvent actionEvent) {
        String questionValue=question.getText();
        if(questionValue.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Setting the alert content text
            alert.setContentText("Question field is required!");

            // Showing the alert
            alert.showAndWait();
        }else{
            question_data.setValeur(questionValue);
            System.out.println("Im here");
            openAddOptionButton.setDisable(false);


        }

    }

    @javafx.fxml.FXML
    public void openAddOption(ActionEvent actionEvent) {
        optionForm.setDisable(false);

    }
}
