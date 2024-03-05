package Controllers.activiteController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.User.RoleName;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavbarController  implements Initializable {

    private final Button logout = new Button("Se Déconnecter");

    @FXML
    private HBox buttonsContainer;
    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();
    private final User user =userLoggedIn;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(user == null){
            Button login = new Button("Se Connecter");
            login.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            login.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button signup = new Button("S'inscrire");
            signup.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            signup.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttonsContainer.getChildren().addAll(login,signup);

        }
        if(user != null && user.getRole() == RoleName.ADMIN){

            Button dashbord = new Button("Dashbrod");
            dashbord.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            dashbord.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button ajouterEvent = new Button("Evenemment");
            ajouterEvent.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            ajouterEvent.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            logout.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");
            logout.setOnAction(e-> {
                try {
                    logout(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonsContainer.getChildren().addAll(dashbord,ajouterEvent,logout);
        }
        if(user != null && user.getRole() == RoleName.TEACHER){

            Button cours = new Button("Cours");
            cours.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            cours.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCour.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            logout.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");
            logout.setOnAction(e-> {
                try {
                    logout(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonsContainer.getChildren().addAll(cours,logout);
        }
        if(user != null && user.getRole() == RoleName.ENTREPRISE_RH){

            Button offre = new Button("Offre");
            offre.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            offre.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterOffre.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });


            logout.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");
            logout.setOnAction(e-> {
                try {
                    logout(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonsContainer.getChildren().addAll(offre,logout);
        }
        if(user != null && user.getRole() == RoleName.STUDENT){

            Button eleve = new Button("Cours");
            eleve.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            eleve.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Eleve.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button event = new Button("Evenement");
            event.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            event.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEvent.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button stage = new Button("stage");
            stage.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            stage.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/LesOffresDEStage.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button activity = new Button("Activités");
            activity.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");

            activity.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Activites.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    currentStage.setScene(scene);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });





            logout.setStyle("-fx-font-size: 18px; " +
                    "-fx-background-color: transparent; " +
                    "-fx-text-fill: white;"+
                    "-fx-cursor: hand;");
            logout.setOnAction(e-> {
                try {
                    logout(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            buttonsContainer.getChildren().addAll(eleve,event,stage,activity,logout);
        }

    }

    public void logout(ActionEvent actionEvent) throws IOException {
            UserSession.Logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(scene);

    }
}

