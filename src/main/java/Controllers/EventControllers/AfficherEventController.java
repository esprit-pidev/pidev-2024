package Controllers.EventControllers;

import tn.esprit.entities.events.EventReactions;
import tn.esprit.entities.events.Events;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventParticipantService;
import tn.esprit.services.eventsServices.EventReactionService;
import tn.esprit.services.eventsServices.EventService;

import java.io.IOException;
import java.util.List;

public class AfficherEventController {
    @FXML
    private HBox chatContainer;
    @FXML
    private VBox feed;
    @FXML
    private VBox container;
    @FXML
    private TextField search;
    @FXML
    private VBox eventParticipated;
    @FXML
    private Button searchbutton;
    private final EventService eventService = new EventService();
    int userId = 8;
    private final EventReactionService eventReactionService = new EventReactionService();
    private final EventCommentService eventCommentService = new EventCommentService();
    private final EventParticipantService eventParticipantService = new EventParticipantService();



    @FXML
    public void updateEvents(String filter) {

        List<Events> events = eventService.display(filter);
        List<Events> newEvents = events.stream().filter(e -> e.getEventName().contains(filter) || e.getDescription().contains(filter)||e.getParticipants().stream().anyMatch(participant -> participant.getParticipant_name().contains(filter))).toList();

        feed.getChildren().clear();
        for (Events event : newEvents) {
            System.out.println("participant  "+event.getParticipants());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
            try {
                Parent componentRoot = fxmlLoader.load();
                EventController eventController = fxmlLoader.getController();
                eventController.setEventt(event);
                eventController.setUserId(userId);
                EventReactions reaction = eventReactionService.display().stream().filter(e->e.getEventId() == event.getEventId() && e.getUserId() == userId ).findFirst().orElse(null);
                eventController.setEventReactions(reaction);
                boolean isParticipate = eventParticipantService.display().stream().anyMatch(e -> e.getUserId() == userId && e.getEventId() == event.getEventId());
                eventController.isParticiped = isParticipate;
                eventController.participationLogic();
                feed.getChildren().add(componentRoot); // Add the loaded component to the container
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    {/* public void updateEventsParticipated(){
        List<EventParticipants> eventParticipants = eventParticipantService.display().stream().filter(e->e.getUserId()==userId ).toList();
        eventParticipated.getChildren().clear();
        for(EventParticipants participant:eventParticipants){
            Events  event = eventService.display().stream().filter(e->e.getEventId() == participant.getEventId()).findAny().orElse(null);
            HBox item = new HBox();
            item.setPadding(new Insets(5));
            Text text = new Text(event.getEventName());
            text.setStyle("-fx-padding: 10 10 10 10;"); // Add padding to the text
            item.setStyle("-fx-border-style: solid; " +
                    "-fx-border-width: 0 0 1 0; " + // Bottom border only
                    "-fx-border-color: #CCCCCC;"); // Border color
            item.getChildren().add(text);
            eventParticipated.getChildren().add(item);
        }
    }*/}


    public void initialize() {
        // Initially display all events
        updateEvents("");
        //updateEventsParticipated();
        {/*eventParticipated.setStyle("-fx-padding: 10; " + // Add padding to the container
                "-fx-border-style: solid; " + // Border style
                "-fx-border-width: 0 0 0 1; " + // Border width (top, right, bottom, left)
                "-fx-border-color: #CCCCCC;");
        */}
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat.fxml"));
        try {
            Parent componentRoot = fxmlLoader.load();
            ChatController chatController = fxmlLoader.getController();
            chatContainer.getChildren().add(chatController.getContainer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void search(KeyEvent event) {
        String filter = search.getText();
        updateEvents(filter);

    }

}
