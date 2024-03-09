package Controllers.CoursControllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.entities.Cours.Questions;
import tn.esprit.entities.Cours.Option;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.User;
import tn.esprit.services.coursServices.EvaluationService;
import tn.esprit.services.coursServices.OptionService;
import tn.esprit.services.coursServices.QuestionService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EvaluationUserController implements Initializable {
    @javafx.fxml.FXML
    private VBox questionsPaper;
    private int evaluationId ;
    private List<Questions> listeQuestions = new ArrayList();
    private QuestionService qs=new QuestionService();
    private Map<Integer,Integer> responseMap=new HashMap<>();


    private User userId;

    public void setUserId(User userId) {
        this.userId = userId;
    }


    OptionService os = new OptionService();
    EvaluationService es=new EvaluationService();
    @javafx.fxml.FXML
    private Button submitSurveyId;
    public void setEvaluationId(int evaluationId1) {
        this.evaluationId = evaluationId1;


    }
    public void initializeUI() {
        System.out.println("evaluation id in initialize " + evaluationId);
        if (es.getIfUserAlreadySubmitEvaluation(evaluationId, userId.getId())) {
            submitSurveyId.setDisable(true);

            listeQuestions = qs.getQuestionByEvaluation(evaluationId);
            for (Questions question : listeQuestions) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionUser.fxml"));
                try {
                    AnchorPane questionCard = loader.load();

                    QuestionUserController questionUserController = loader.getController();
                    questionUserController.setQuestion(question, userId.getId());
                    //add listener of function runed in questionUserController

                    questionsPaper.getChildren().add(questionCard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            listeQuestions = qs.getQuestionByEvaluation(evaluationId);
            for (Questions question : listeQuestions) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuestionUser.fxml"));
                try {
                    AnchorPane questionCard = loader.load();

                    QuestionUserController questionUserController = loader.getController();
                    questionUserController.setQuestion(question, this::handleOptionPicked);
                    //add listener of function runed in questionUserController

                    questionsPaper.getChildren().add(questionCard);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        if (responseMap.keySet().size() != listeQuestions.size()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All questions are required");
            alert.setContentText("All questions are required");
            alert.showAndWait();

        } else {

            es.SaveResponseEvaluation(evaluationId, userId.getId());
            for (Map.Entry<Integer, Integer> entry : responseMap.entrySet()) {
                os.SaveResponse(entry.getValue(), userId.getId());
            }
            // Show notification
            // Show notification
            //  showNotification("Survey Submitted", "Survey submitted successfully!");


        }
    }
   /* private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }*/



}
