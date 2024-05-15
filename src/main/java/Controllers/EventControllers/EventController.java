package Controllers.EventControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.entities.User.User;
import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.EventParticipants;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventParticipantService;
import tn.esprit.services.eventsServices.EventReactionService;

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
    private ImageView imgPost;
    @FXML
    private ImageView imgReaction;
    @FXML
    private Label nbReactions;
    @FXML
    private HBox reactionsContainer;
    @FXML
    Button participation;
    private long startTime = 0;
    Boolean isParticiped ;
    private final String  path  = "file:///C:/Users/Gaming/Desktop/pidevSymfony/PiSymfony/public/uploads/";
    private EventReactionService eventReactionService =new EventReactionService();
    EventCommentService eventCommentService = new EventCommentService();
    EventParticipantService eventParticipantService = new EventParticipantService();
    private Events eventt ;
    EventParticipants participant;
    EventReactions eventReactions;
    private User user ;

    public void setEventt(Events eventt) {
        this.eventt = eventt;
    }

    public void setEventReactions(EventReactions eventReactions) {
        this.eventReactions = eventReactions;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public Button getParticipation() {
        return participation;
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
                .anyMatch(e -> e.getUserId().getId() == user.getId() && e.getEventId() == eventt.getEventId());
        if (!userExists) {
            eventReactionService.ajouter(new EventReactions(user, eventt.getEventId(), reactionType));
        } else {
            // Check if the user's current reaction is equal to the one being pressed

            String finalReactionType = reactionType;
            boolean currentUserReactionEqualsPressed = eventReactionService.display().stream()
                    .anyMatch(e -> e.getUserId().getId() == user.getId() && e.getEventId() == eventt.getEventId() && e.getReactionType().equals(finalReactionType));

            if (currentUserReactionEqualsPressed) {
                // Perform action to delete the user's reaction
                eventReactionService.supprimer(new EventReactions(user, eventt.getEventId(), reactionType));
                Image imageSad = new Image("/img/ic_like_outline.png");
                imgReaction.setImage(imageSad);
            } else {
                // Perform action to modify the user's reaction
                eventReactionService.modifier(new EventReactions(user, eventt.getEventId(), reactionType));
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

    private void addComment(ActionEvent e, int eventId, String comment) {
        // Check if the comment is empty
        if (comment.isEmpty()) {
            // Display an alert for an empty comment
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Comment");
            alert.setContentText("Comment cannot be empty!");
            alert.showAndWait();
            return;
        }

        // Add the comment if it's not empty
        eventCommentService.ajouter(new EventComments(user, eventId, comment));
        commentField.setText("");
        commentDisplay.getChildren().clear();
        displayComments();
    }

    public void displayComments(){
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

            if(comment.getUserId().getId() == user.getId()){
                Button removeButton = new Button("Del");
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
        participation.setText("del");
        displayComments();

    }
    private void removeParticipant(ActionEvent ee, User user,Events event) {
        EventParticipants  participant = eventParticipantService.display().stream().filter(e->e.getUserId().getId() == user.getId()).findFirst().get();
        eventParticipantService.supprimer(participant);
        participation.setText("Attend");
        participation.setStyle("-fx-background-color:#781c10;");
        participationLogic();
        event.initializeParticipants(eventParticipantService);




    }

    private void addParticipant(ActionEvent e, User user, Events event) {
        EventParticipants participant_object = new EventParticipants(user,event.getEventId());
        eventParticipantService.ajouter(participant_object);
        participation.setText("Remove");
        participation.setStyle("-fx-background-color:#7AA95C;");
        participationLogic();
        event.initializeParticipants(eventParticipantService);



    }
    public void participationLogic() {
        isParticiped = eventParticipantService.display().stream()
                .anyMatch(e -> e.getUserId().getId() == user.getId() && e.getEventId() == eventt.getEventId());
        if (isParticiped) {
            participation.setText("Remove");
            participation.setOnAction(e -> removeParticipant(e, user,eventt));
        } else {
            participation.setText("Attend");
            participation.setOnAction(e -> addParticipant(e, user, eventt));
        }
    }
    public void affectPhotos(){
        Image imgEvent = new Image(path+eventt.getPhoto());
            imgPost.setImage(imgEvent);
    }
    public void initializeReaction(EventReactions eventReactions) {

        if(eventReactions!= null) {

            if (eventReactions.getReactionType().equals("Love")) {
                Image imageLove = new Image("/img/ic_love_.png");
                imgReaction.setImage(imageLove);
            }
            if (eventReactions.getReactionType().equals("haha")) {
                Image imageHaha = new Image("/img/ic_haha.png");
                imgReaction.setImage(imageHaha);
            }
            if (eventReactions.getReactionType().equals("Wow")) {
                Image imageWow = new Image("/img/ic_wow.png");
                imgReaction.setImage(imageWow);
            }
            if (eventReactions.getReactionType().equals("Sad")) {
                Image imageSad = new Image("/img/ic_sad.png");
                imgReaction.setImage(imageSad);
            }
            if (eventReactions.getReactionType().equals("Angry")) {
                Image imageAngry = new Image("/img/ic_angry.png");
                imgReaction.setImage(imageAngry);
            }
            if (eventReactions.getReactionType().equals("Like")) {
                Image imageLike = new Image("/img/ic_like.png");
                imgReaction.setImage(imageLike);
            }
        }

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
            initializeReaction(eventReactions);
            if (participation.getText().equals("Remove")){
                participation.setStyle("-fx-background-color:#781c10;");
            }
            else{
                participation.setStyle("-fx-background-color:#7AA95C;");
            }
        });


    }



}