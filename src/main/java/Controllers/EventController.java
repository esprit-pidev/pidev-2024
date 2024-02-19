package Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventParticipants;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;

import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventParticipantService;
import tn.esprit.services.eventsServices.EventReactionService;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    @FXML
    private VBox CommentsContainer;
    @FXML
    private Text description;
    @FXML
    private Text title;
    @FXML
    private Button addComment;

    @FXML
    private TextField commentField;

    @FXML
    private VBox commentDisplay;



    @FXML
    private ImageView imgAngry;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgPost;

    @FXML
    private ImageView imgProfile;

    @FXML
    private ImageView imgReaction;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgWow;

    @FXML
    private HBox likeContainer;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbShares;

    @FXML
    private Label reactionName;

    @FXML
    private HBox reactionsContainer;



    @FXML
    Button participation;
    private long startTime = 0;
    Boolean isParticiped ;
    private final String  path  = "file:///C:/xampp/htdocs/img/";
    private EventReactionService eventReactionService =new EventReactionService();
    EventCommentService eventCommentService = new EventCommentService();
    EventParticipantService eventParticipantService = new EventParticipantService();
    private Events eventt ;
    EventParticipants participant;
    private int userId = 1;

    public EventController() {
    }

    public void setEventt(Events eventt) {
        this.eventt = eventt;
    }

    @FXML
    void onLikeContainerMouseReleased(MouseEvent event) {
        startTime = System.currentTimeMillis();
    }

    @FXML
    void onLikeContainerPressed(MouseEvent event) {
        if(System.currentTimeMillis() - startTime > 500){
            reactionsContainer.setVisible(true);
        }else {
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }

        }

    }

    @FXML
    void onReactionImgPressed(MouseEvent event) {


        String reactionType = null;
        switch (((ImageView) event.getSource()).getId()) {
            case "imgLove":
                reactionType = "Love";
                Image imageLove = new Image("/img/ic_love_.png");
                imgReaction.setImage(imageLove);
                break;
            case "imgCare":
                reactionType = "Care";
                Image imageCare = new Image("/img/ic_care.png");
                imgReaction.setImage(imageCare);
                break;
            case "imgHaha":
                reactionType = "Haha";
                Image imageHaha = new Image("/img/ic_haha.png");
                imgReaction.setImage(imageHaha);
                break;
            case "imgWow":
                reactionType = "Wow";
                Image imageWow = new Image("/img/ic_wow.png");
                imgReaction.setImage(imageWow);
                break;
            case "imgSad":
                reactionType = "Sad";
                Image imageSad = new Image("/img/ic_sad.png");
                imgReaction.setImage(imageSad);
                break;
            case "imgAngry":
                reactionType = "Angry";
                Image imageAngry = new Image("/img/ic_angry.png");
                imgReaction.setImage(imageAngry);
                break;
            default:
                reactionType = "Like";
                Image imageLike = new Image("/img/ic_like.png");
                imgReaction.setImage(imageLike);
                break;
        }
        boolean userExists = eventReactionService.display()
                .stream()
                .anyMatch(e -> e.getUserId() == userId && e.getEventId() == eventt.getEventId());
        if (!userExists) {
            eventReactionService.ajouter(new EventReactions(userId, eventt.getEventId(), reactionType));
        } else {
            // Check if the user's current reaction is equal to the one being pressed

            String finalReactionType = reactionType;
            boolean currentUserReactionEqualsPressed = eventReactionService.display().stream()
                    .anyMatch(e -> e.getUserId() == userId && e.getEventId() == eventt.getEventId() && e.getReactionType().equals(finalReactionType));

            if (currentUserReactionEqualsPressed) {
                // Perform action to delete the user's reaction
                eventReactionService.supprimer(new EventReactions(userId, eventt.getEventId(), reactionType));
            } else {
                // Perform action to modify the user's reaction
                eventReactionService.modifier(new EventReactions(userId, eventt.getEventId(), reactionType));
            }
        }

        reactionsContainer.setVisible(false);

        // Update nbReactions after performing the action
        long nbr2 = eventReactionService.display().stream().filter(e -> e.getEventId() == eventt.getEventId()).count();
        nbReactions.setText(String.valueOf(nbr2));
    }

    Boolean commentVisible = false;
    @FXML
    void showCommentsArea(MouseEvent event) {
        commentVisible =!commentVisible;
        if(!commentVisible){
            CommentsContainer.setVisible(false);
            CommentsContainer.setMaxHeight(0);

        }
        else {CommentsContainer.setVisible(true);
            CommentsContainer.setMaxHeight(300);


        }

    }

    private void addComment(javafx.event.ActionEvent e, int eventId, String comment) {
        eventCommentService.ajouter(new EventComments(1,eventId,comment));
        commentField.setText("");
        commentDisplay.getChildren().clear();
        displayComments();
    }

    public void displayComments(){
        ObservableList<String> commentsList = FXCollections.observableArrayList();
        List<EventComments> comments = eventCommentService.display(eventt);
        for(EventComments comment : comments ){
            HBox commentContainer = new HBox();
            Text text = new Text();
            double padding = 5; // Set the padding value as needed
            text.wrappingWidthProperty().bind(commentDisplay.widthProperty().subtract(padding * 12));
            text.setText(comment.getCommentText());
            commentContainer.setStyle("-fx-border-color: grey; -fx-border-width: 0 0 0.5 0;");
            commentContainer.setPadding(new Insets(5));
            commentContainer.getChildren().add(text);
            if(comment.getUserId() == userId){
                Button removeButton = new Button("Delete");
                removeButton.setPrefWidth(60);
                removeButton.setPrefHeight(15);
                removeButton.setStyle(
                        "-fx-background-color: grey; " + // Red background color
                                "-fx-text-fill: white; " + // White text color
                                "-fx-font-size: 14px; " + // Font size
                                "-fx-padding: 3px 3px; " + // Padding around the text
                                "-fx-border-radius: 5px; " + // Rounded corners
                                "-fx-cursor: hand;" // Hand cursor on hover
                );
                removeButton.setOnAction(e->removeComment(e,comment));
                commentContainer.getChildren().add(removeButton);
            }

            commentDisplay.getChildren().add(commentContainer);
        }


    }

    private void removeComment(ActionEvent e, EventComments comment) {
        eventCommentService.supprimer(comment);
        commentDisplay.getChildren().clear();
        displayComments();

    }
    private void removeParticipant(ActionEvent event, int userId) {
        EventParticipants  participant = eventParticipantService.display().stream().filter(e->e.getUserId() == userId).findFirst().get();
        eventParticipantService.supprimer(participant);
        participation.setText("Attend");
        participationLogic();
    }

    private void addParticipant(ActionEvent e, int userId, Events event) {
        eventParticipantService.ajouter(new EventParticipants(userId,event.getEventId()));
        participation.setText("Remove");
        participationLogic();
    }
    public void participationLogic() {
        isParticiped = eventParticipantService.display().stream()
                .anyMatch(e -> e.getUserId() == userId && e.getEventId() == eventt.getEventId());
        if (isParticiped) {
            participation.setText("Remove");
            participation.setOnAction(e -> removeParticipant(e, userId));
        } else {
            participation.setText("Attend");
            participation.setOnAction(e -> addParticipant(e, userId, eventt));
        }
    }
    public void affectPhotos(){
        Image imgEvent = new Image(path+eventt.getPhoto());
            imgPost.setImage(imgEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()-> nbReactions.setText(String.valueOf(eventReactionService.display().stream().filter(e->e.getEventId() == eventt.getEventId()).count())));
        CommentsContainer.setVisible(false);
        CommentsContainer.setMaxHeight(0);
        addComment.setOnAction(e -> addComment(e, eventt.getEventId(), commentField.getText()));
        Platform.runLater(() -> {
            displayComments();
            participationLogic();
            affectPhotos();
            title.setText(eventt.getEventName());
            description.setText(eventt.getDescription());

        });


    }



}