package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MailSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class AddUserController {

    private final UserService userService = new UserService();

    private final MailSender mailSender = new MailSender();

    private String uploadedPhotoName;

    @FXML
    private TextField emailETF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField nomETF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField idTF;


    @FXML
    private TextField prenomETF;

    @FXML
    private TextField prenomTF;

    @FXML
    private PasswordField pwdETF;

    @FXML
    private PasswordField pwdTF;

    @FXML
    void AddS(ActionEvent event) {
        try {
            userService.add(new Etudiant(nomTF.getText(), emailTF.getText(), pwdTF.getText(), 3, prenomTF.getText(), "male", "11445859", "3A21", uploadedPhotoName, LocalDate.of(1980, 5, 15) ));
            MailSender.sendEmail(emailTF.getText(), "Paramétre d'accées", "Hello those are your access parametre : \nEmail : " + emailTF.getText() + "\nmot de passe :" + pwdTF.getText());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void AddT(ActionEvent event) {
        try {
            userService.add(new Enseignant(nomETF.getText(), emailETF.getText(), pwdETF.getText(), prenomETF.getText(), "12564789", "Female", LocalDate.of(1980, 5, 15), uploadedPhotoName));


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void Bloquer(ActionEvent event) {
        userService.blockUser(Integer.parseInt(idTF.getText()));
    }

    @FXML
    void uploadPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter for image files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                // Generate a unique file name
                String uniqueFileName = UUID.randomUUID().toString()+ file.getName();

                // Define destination path (adjust as necessary)
                File destDir = new File("C:\\xampp\\htdocs");

                File destFile = new File(destDir, uniqueFileName);

                // Copy the file to the destination
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                this.uploadedPhotoName = uniqueFileName;

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upload Successful");
                alert.setHeaderText(null);
                alert.setContentText("Profile picture uploaded successfully.");
                alert.showAndWait();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Could not upload the file: " + e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    void profil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
        Parent root = loader.load();
        emailTF.getScene().setRoot(root);
    }
}
