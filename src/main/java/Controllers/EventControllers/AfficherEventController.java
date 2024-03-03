package Controllers.EventControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.entities.User.User;
import tn.esprit.entities.events.EventParticipants;
import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventParticipantService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.util.List;

public class AfficherEventController {

    @FXML
    private VBox chatContainer;
    @FXML
    private Button reload;
    @FXML
    private VBox feed;
    @FXML
    private HBox room;
    @FXML
    private VBox container;
    @FXML
    private TextField search;
    @FXML
    private VBox eventParticipated;
    @FXML
    private Button searchbutton;
    private final EventService eventService = new EventService();
    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();
    private User user =userLoggedIn;
    private final EventReactionService eventReactionService = new EventReactionService();
    private final EventCommentService eventCommentService = new EventCommentService();
    private final EventParticipantService eventParticipantService = new EventParticipantService();



    @FXML
    public void updateEvents(String filter) {

        List<Events> events = eventService.display(filter);
        List<Events> newEvents = events.stream().filter(e -> e.getEventName().contains(filter) || e.getDescription().contains(filter)||e.getParticipants().stream().anyMatch(participant -> participant.getParticipant_name().contains(filter))).toList();

        feed.getChildren().clear();
        for (Events event : newEvents) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
            try {
                Parent componentRoot = fxmlLoader.load();
                EventController eventController = fxmlLoader.getController();
                eventController.setEventt(event);
                eventController.setUserId(user);
                EventReactions reaction = eventReactionService.display().stream().filter(e->e.getEventId() == event.getEventId() && e.getUserId().getId() == user.getId() ).findFirst().orElse(null);
                eventController.setEventReactions(reaction);
                boolean isParticipate = eventParticipantService.display().stream().anyMatch(e -> e.getUserId().getId() == user.getId() && e.getEventId() == event.getEventId());
                eventController.isParticiped = isParticipate;
                eventController.participationLogic();

                feed.getChildren().add(componentRoot); // Add the loaded component to the container
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

 public void ChatRooms(){

     chatContainer.getChildren().clear();
        List<EventParticipants> eventParticipants = eventParticipantService.display().stream().filter(e->e.getUserId().getId()==user.getId() ).toList();
        chatContainer.getChildren().clear();
        for(EventParticipants participant:eventParticipants){
            FXMLLoader fxmlLoader_chat = new FXMLLoader(getClass().getResource("/chat.fxml"));
            try {
                Parent componentRoot = fxmlLoader_chat.load();
                ChatController chatController = fxmlLoader_chat.getController();
                chatContainer.getChildren().add(chatController.getContainer());
                Events EventName =   eventService.display().stream().filter(e->e.getEventId() == participant.getEventId()).findFirst().orElse(null);
                chatController.getMessageArea().setUserData(EventName.getEventName());
                chatController.setRoomId(EventName.getEventName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void initialize() {
        // Initially display all events
        updateEvents("");
        ChatRooms();
        reload.setOnAction(e -> {
            ChatRooms();
        });
        {/*eventParticipated.setStyle("-fx-padding: 10; " + // Add padding to the container
                "-fx-border-style: solid; " + // Border style
                "-fx-border-width: 0 0 0 1; " + // Border width (top, right, bottom, left)
                "-fx-border-color: #CCCCCC;");
        */}

    }



    @FXML
    void search(KeyEvent event) {
        String filter = search.getText();
        updateEvents(filter);

    }

}
