package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

public class EditProfilTeacherController {

    String uploadedPhotoName = "";

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();

    public void initialize() {
        String photoFileName="";
            Enseignant enseignant = (Enseignant) userService.getById(userLoggedIn.getId());
            photoFileName = "C:\\xampp\\htdocs\\img\\"+enseignant.getProfil_picture();
        loadImage(photoFileName);


        setNomTF(enseignant.getNom());
        setPrenomTF(enseignant.getPrenom());
        setDateNaissance(enseignant.getDate_naissance());
    }

    private void loadImage(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            Image image = new Image(file.toURI().toString());
            profilPhoto.setImage(image);
        } else {
            // Handle case where image doesn't exist (e.g., set a default image)
            System.out.println("Image file does not exist: " + filePath);
        }
    }


    @FXML
    private DatePicker dateNaissance;


    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private ImageView profilPhoto;


    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance.setValue(dateNaissance);
    }

    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setPrenomTF(String prenomTF) {
        this.prenomTF.setText(prenomTF);
    }

    @FXML
    void saveChanges(ActionEvent event) throws IOException {
        Enseignant enseignant = (Enseignant) userService.getById(userLoggedIn.getId());
        enseignant.setNom(nomTF.getText());
        enseignant.setPrenom(prenomTF.getText());
        enseignant.setDate_naissance(dateNaissance.getValue());
        if (!uploadedPhotoName.equals(""))
            enseignant.setProfil_picture(uploadedPhotoName);
        else
            enseignant.setProfil_picture(enseignant.getProfil_picture());
        userService.updateEnseignant(enseignant);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEnseignant.fxml"));
        Parent root = loader.load();
        nomTF.getScene().setRoot(root);
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileEnseignant.fxml"));
        Parent root = loader.load();
        nomTF.getScene().setRoot(root);
    }

    @FXML
    void upload(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");

        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String uniqueFileName = UUID.randomUUID().toString()+ file.getName();

                File destDir = new File("C:\\xampp\\htdocs\\img");

                File destFile = new File(destDir, uniqueFileName);

                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                this.uploadedPhotoName = uniqueFileName;

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


}
