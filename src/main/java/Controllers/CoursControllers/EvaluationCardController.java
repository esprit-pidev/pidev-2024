package controllers;

import entities.Evaluation;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class EvaluationCardController implements Initializable {
    @javafx.fxml.FXML
    private Label titleLabel;
    private Evaluation evaluation;
    @javafx.fxml.FXML
    private Rectangle card;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        card.setOnMouseClicked(this::handleCardClick);

    }
    private void handleCardClick(MouseEvent event) {
        System.out.println("Card clicked for Evaluation: " + evaluation.getTitre());

        // Fire a custom event when the card is clicked
        fireEvent(new CardClickedEvent());
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.titleLabel.setText(evaluation.getTitre());
    }
    public void fireEvent(Event event) {
        Event.fireEvent(card, event);
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }
    public void setClickStyle() {
        card.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        titleLabel.setStyle("-fx-text-fill: red;");
    }

    // Method to set styles when the card is unclicked
    public void setCancelClickStyle() {
        card.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        titleLabel.setStyle("-fx-text-fill: black;");
    }

}
