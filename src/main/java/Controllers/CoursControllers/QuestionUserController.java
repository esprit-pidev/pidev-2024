package controllers;

import entities.Option;
import entities.Questions;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import service.OptionService;

import java.util.ArrayList;
import java.util.List;

public class QuestionUserController {
    @javafx.fxml.FXML
    private VBox optionZone;
    private Questions question;
    @javafx.fxml.FXML
    private Label questionLabel;
    private List<Option> optionList = new ArrayList<>();
    private OptionService os=new OptionService();
    private Option optionPicked;
    private EventHandler<PickedValueEvent> optionPickedEventHandler;
    public void setQuestion(Questions question,int UserId ){
        this.question = question;
        this.optionList = os.obtenirToutesLesoptionsByQuestion(question.getId());
        ToggleGroup toggleGroup = new ToggleGroup();

        for (Option option : optionList) {
            RadioButton radio = new RadioButton();
            radio.setText(option.getValeur());
            radio.setId(String.valueOf(question.getId()));
            radio.setToggleGroup(toggleGroup);
            if(option.getIs_correct()){
                System.out.println("isCorrect");
                radio.setStyle("-fx-text-fill: white;-fx-background-color: green");


            }
            if(os.isPickedOptionByUser(option.getId(),UserId)){
                radio.setSelected(true);
            }

            optionZone.getChildren().add(radio);
        }

    }


        public void setQuestion(Questions question, EventHandler<PickedValueEvent> optionPickedEventHandler){
        this.optionPickedEventHandler=optionPickedEventHandler;
        this.question = question;
        this.optionList = os.obtenirToutesLesoptionsByQuestion(question.getId());

        ToggleGroup toggleGroup = new ToggleGroup();

        for (Option option : optionList) {
            RadioButton radio = new RadioButton();
            radio.setText(option.getValeur());
            radio.setId(String.valueOf(question.getId()));
            radio.setToggleGroup(toggleGroup);

            radio.setOnMouseClicked(event -> {
                this.optionPicked = option;
                System.out.println(option);
                fireOptionPickedEvent();

            });

            optionZone.getChildren().add(radio);
        }

        this.questionLabel.setText(question.getValeur());
    }
    public Option getOptionPicked(){
        return optionPicked;
    }
    private void fireOptionPickedEvent() {
        PickedValueEvent event = new PickedValueEvent(optionPicked,question);
        if (optionPickedEventHandler != null) {
            optionPickedEventHandler.handle(event);
        }
    }

}
