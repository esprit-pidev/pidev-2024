package Controllers.EventControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.entities.User.User;
import tn.esprit.entities.events.EventComments;
import tn.esprit.entities.events.Events;
import tn.esprit.services.eventsServices.EventCommentService;
import tn.esprit.services.eventsServices.EventService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AjouterEventController implements Initializable {
    @FXML
    private Button addEvent;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea description;

    @FXML
    private TextField name;
    @FXML
    private VBox eventsContainer;
    @FXML
    private VBox analysisContainer;
    @FXML
    private Button uploadImg;
    private String imgName ;
    FileChooser fileChooser = new FileChooser();
    private EventService eventService = new EventService();
    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();
    private User admin =userLoggedIn;

    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
     EventCommentService eventCommentService = new EventCommentService();


    public  String analyzeEmotions(Events event) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        StringBuilder concatenatedText = new StringBuilder();
        List<String> comments = eventCommentService.display(event).stream().map(EventComments::getCommentText)
                .collect(Collectors.toList());
        // Concatenate all comments into a single prompt
        for (String comment : comments) {
            concatenatedText.append(comment).append(" ");
        }
        System.out.println("comments are :::"+comments);
        String prompt = "Perform sentiment analysis on these comments to gauge public opinion " +
                "about an event in one or two phrases ,give suggestions for improvement based on negative comments only , dont suggest anything from your own, if the list of comments is empty just tell 'there is no comments': " + concatenatedText.toString();

        RequestBody body = RequestBody.create(mediaType, "{\"model\": \"gpt-3.5-turbo-0125\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}");

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response from API: " + responseBody); // Debug statement

                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray choicesArray = jsonResponse.getJSONArray("choices");
                if (choicesArray.length() > 0) {
                    JSONObject choiceObject = choicesArray.getJSONObject(0);
                    JSONObject messageObject = choiceObject.getJSONObject("message");
                    String content = messageObject.getString("content");
                    System.out.println("Content: " + content);
                    return content;
                }
            } else {
                System.err.println("Error: " + response.code() + " - " + response.message());
                System.err.println("Response Body: " + response.body().string());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void uploadImg(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Define the destination directory
            String destinationDirectory = "C:\\Users\\Gaming\\Desktop\\pidevSymfony\\PiSymfony\\public\\uploads";
            // Get the name of the selected file
            String fileName = file.getName();
            // Create a Path for the destination file

            Path destinationPath = new File(destinationDirectory, fileName).toPath();
            try {
                // Copy the selected file to the destination directory
                Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully to: " + destinationPath);
                imgName = fileName;
                uploadImg.setText("ready");
            } catch (IOException e) {
                System.out.println("Error uploading file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }

    }
    public void displayEvents(){
        List<Events> events = eventService.display();
        eventsContainer.getChildren().clear();
        analysisContainer.getChildren().clear();
        for (Events eventt : events){
            Text eventName = new Text(eventt.getEventName());
            eventName.setWrappingWidth(200);
            eventName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            eventName.setTextAlignment(TextAlignment.CENTER);
            eventName.setFill(javafx.scene.paint.Color.web("#262428"));
            Button delete = new Button("Delete");
            Button update = new Button("Update");
            delete.setMaxHeight(15);
            update.setMaxHeight(15);
            delete.setMaxWidth(60);
            update.setMaxWidth(60);
            delete.setStyle("-fx-background-color: #711c10; -fx-text-fill: white;");
            update.setStyle("-fx-background-color: #2f4558; -fx-text-fill: white;");
            HBox container1 =new HBox();
            HBox container2 =new HBox();
            container1.setStyle("-fx-background-color: white;-fx-background-radius: 10; -fx-border-color: white; -fx-border-width: 1;-fx-border-radius: 10;");
            container1.setSpacing(30);
            container1.setAlignment(Pos.CENTER_LEFT);
            container1.setPadding(new Insets(5,5,5,5));
            container2.setSpacing(10);
            container2.getChildren().addAll(delete,update);
            container1.getChildren().addAll(eventName,container2);
            delete.setOnAction(e->{
                deleteEvent(e,eventt);

            });

            update.setOnAction(event ->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvent.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    UpdateEventController controller;
                    controller = loader.getController();
                    controller.setEventToBeUpdated(eventt);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 600, 626));
                    stage.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            eventsContainer.getChildren().add(container1);
            eventsContainer.setPadding(new Insets(5,5,5,5));
            Text analyse = new Text( analyzeEmotions(eventt));

            HBox textPane = new HBox(analyse);

            textPane.setStyle("-fx-border-color: black; " +
                    "-fx-border-width: 1px; " +
                    "-fx-background-color: transparent; " +  // Background color
                    "-fx-background-radius: 10px; " +      // Background radius
                    "-fx-border-radius: 10px;");
            textPane.setPadding(new Insets(5,5,5,5));
            analyse.setWrappingWidth(340);
            analyse.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            analysisContainer.setPrefWidth(360);
            analysisContainer.setAlignment(Pos.CENTER);
            analysisContainer.setSpacing(10);
            analysisContainer.setPadding(new Insets(5,5,5,5));


            analysisContainer.getChildren().add(textPane);
        }
    }

    private void deleteEvent(ActionEvent e, Events eventt) {
        eventService.supprimer(eventt);
        displayEvents();
    }



    public void addEventt(ActionEvent event) {
        // Check if any field is empty
        if (name.getText().isEmpty() || description.getText().isEmpty() || datePicker.getValue() == null || imgName == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return; // Exit the method if any field is empty
        }

        // Check if the date is higher than tomorrow
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate.isBefore(LocalDate.now().plusDays(1))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Event date must be higher than tomorrow.");
            alert.showAndWait();
            return; // Exit the method if the date is not higher than tomorrow
        }

        // Check if the name and description contain special characters
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 ]");
        if (pattern.matcher(name.getText()).find() || pattern.matcher(description.getText()).find()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Special Characters");
            alert.setContentText("Name and description cannot contain special characters.");
            alert.showAndWait();
            return; // Exit the method if special characters are found
        }

        // Add the event
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
        eventService.ajouter(new Events(admin, name.getText(), description.getText(), sqlDate, imgName));
        displayEvents();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEvent.setOnAction(this::addEventt);
        eventsContainer.setSpacing(10);
        displayEvents();

    }



}
