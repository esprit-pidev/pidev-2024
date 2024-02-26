package controllers;

import entities.Option;
import entities.Questions;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import service.EvaluationService;
import service.OptionService;
import service.QuestionService;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EvaluationUserController implements Initializable {
    @javafx.fxml.FXML
    private VBox questionsPaper;
    private int evaluationId=1;
    private List<Questions> listeQuestions = new ArrayList();
    private QuestionService qs=new QuestionService();
    private Map<Integer,Integer> responseMap=new HashMap<>();
    private int userId=1;
    OptionService os = new OptionService();
    EvaluationService es=new EvaluationService();
    @javafx.fxml.FXML
    private Button submitSurveyId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(es.getIfUserAlreadySubmitEvaluation(evaluationId,userId)){
            submitSurveyId.setDisable(true);
            listeQuestions = qs.getQuestionByEvaluation(evaluationId);
            for (Questions question : listeQuestions) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionUser.fxml"));
                try {
                    AnchorPane questionCard = loader.load();

                    QuestionUserController questionUserController = loader.getController();
                    questionUserController.setQuestion(question,userId);
                    //add lisnten of function runed in questionUserContrroller

                    questionsPaper.getChildren().add(questionCard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }else {
            listeQuestions = qs.getQuestionByEvaluation(evaluationId);
            for (Questions question : listeQuestions) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionUser.fxml"));
                try {
                    AnchorPane questionCard = loader.load();

                    QuestionUserController questionUserController = loader.getController();
                    questionUserController.setQuestion(question, this::handleOptionPicked);
                    //add lisnten of function runed in questionUserContrroller

                    questionsPaper.getChildren().add(questionCard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }
    public void handleOptionPicked(PickedValueEvent event) {
        Option pickedOption = event.getPickedOption();
        Questions question = event.getQuestion();
        // Handle the picked option in the parent controller
        System.out.println("Option picked FROM child to parent  " + pickedOption.getValeur()+ " Question : "+ question.getValeur());
        responseMap.put(question.getId(),pickedOption.getId());

    }

    @javafx.fxml.FXML
    public void submitSurvey(ActionEvent actionEvent) {
        if(responseMap.keySet().size()!=listeQuestions.size()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All questions are required");
            alert.setContentText("All questions are required");
            alert.showAndWait();

        }else{

            es.SaveResponseEvaluation(evaluationId,userId);
            for (Map.Entry<Integer, Integer> entry : responseMap.entrySet()) {
            os.SaveResponse(entry.getValue(),userId);
            }


        }
    }
}
