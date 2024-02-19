package tn.esprit.controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Entreprise;
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
import java.util.concurrent.ThreadLocalRandom;

public class SignUpController {

    private  final UserService userService = new UserService();


    private final MailSender mailSender = new MailSender();

    private String uploadedPhotoName = null;

    @FXML
    private TextField paysEnTF;

    @FXML
    private TextField cinETF;

    @FXML
    private TextField cinTF;

    @FXML
    private TextField classeTF;

    @FXML
    private DatePicker dateNaissanceETF;

    @FXML
    private DatePicker dateNaissanceTF;

    @FXML
    private TextField emailETF;

    @FXML
    private TextField emailEnTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Label errorMail;

    @FXML
    private Label errorMailE;

    @FXML
    private ComboBox<String> genreCB;

    @FXML
    private ComboBox<String> genreECB;

    @FXML
    private TextField localEnTF;

    @FXML
    private TextField niveauTF;

    @FXML
    private TextField nomETF;

    @FXML
    private TextField nomEnTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField pdwETF;

    @FXML
    private TextField prenomETF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField pwdEnTF;

    @FXML
    private TextField pwdTF;

    @FXML
    private TextField websiteEnTF;


    public void setUploadedPhotoName(String uploadedPhotoName) {
        this.uploadedPhotoName = uploadedPhotoName;
    }

    public void setCinETF(String cinETF) {
        this.cinETF.setText(cinETF);
    }

    public void setCinTF(String cinTF) {
        this.cinTF.setText(cinTF);
    }

    public void setClasseTF(String classeTF) {
        this.classeTF.setText(classeTF);
    }

    public void setDateNaissanceETF(LocalDate dateNaissanceETF) {
        this.dateNaissanceETF.setValue(dateNaissanceETF);
    }

    public void setDateNaissanceTF(LocalDate dateNaissanceTF) {
        this.dateNaissanceTF.setValue(dateNaissanceTF);
    }

    public void setEmailETF(String emailETF) {
        this.emailETF.setText(emailETF);
    }

    public void setEmailTF(String emailTF) {
        this.emailTF.setText(emailTF);
    }

    public void setGenreCB(String genreCB) {
        this.genreCB.setValue(genreCB);
    }

    public void setGenreECB(String genreECB) {
        this.genreECB.setValue(genreECB);
    }

    public void setNiveauTF(int niveauTF) {
        this.niveauTF.setText(String.valueOf(niveauTF));
    }

    public void setNomETF(String nomETF) {
        this.nomETF.setText(nomETF);
    }

    public void setNomTF(String nomTF) {
        this.nomTF.setText(nomTF);
    }

    public void setPdwETF(String pdwETF) {
        this.pdwETF.setText(pdwETF);
    }

    public void setPrenomETF(String prenomETF) {
        this.prenomETF.setText(prenomETF);
    }

    public void setPrenomTF(String prenomTF) {
        this.prenomTF.setText(prenomTF);
    }

    public void setPwdTF(String pwdTF) {
        this.pwdTF.setText(pwdTF);
    }

    @FXML
    public void initialize() {
        genreCB.getItems().addAll("Male", "Female");
        genreCB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        });

        genreECB.getItems().addAll("Male", "Female");
        genreECB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        });

    }

    @FXML
    void AddS(ActionEvent event) throws IOException {

        if (!emailTF.getText().endsWith("@esprit.tn")) {
            errorMail.setText("L'email doit se terminer par @esprit.tn");
        } else {
            errorMail.setText("");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmailVerification.fxml"));
            Parent root = loader.load();
            emailTF.getScene().setRoot(root);
            EmailVerificationController emailVerificationController = loader.getController();
            Etudiant etudiant = new Etudiant(nomTF.getText(), emailTF.getText(), pwdTF.getText(), Integer.parseInt(niveauTF.getText()), prenomTF.getText(), genreCB.getSelectionModel().getSelectedItem(), cinTF.getText(), classeTF.getText(), uploadedPhotoName, dateNaissanceTF.getValue());
            int verifCode = ThreadLocalRandom.current().nextInt(10000, 100000);

            // Pass the data to the EmailVerificationController
            emailVerificationController.initData(verifCode, etudiant);

            MailSender.sendEmail(emailTF.getText(), "Vérification Email", "Votre code de vérification est : " + verifCode);

        }
    }

    @FXML
    void AddT(ActionEvent event) throws IOException {
        if (!emailETF.getText().endsWith("@esprit.tn")) {
            errorMailE.setText("L'email doit se terminer par @esprit.tn");
        } else {
            errorMailE.setText("");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmailVerification.fxml"));
            Parent root = loader.load();
            emailETF.getScene().setRoot(root);
            EmailVerificationController emailVerificationController = loader.getController();
            Enseignant enseignant = new Enseignant(nomETF.getText(), emailETF.getText(), pdwETF.getText(), prenomETF.getText(), cinETF.getText(), genreECB.getSelectionModel().getSelectedItem(), dateNaissanceETF.getValue(), uploadedPhotoName);
            int verifCode = ThreadLocalRandom.current().nextInt(10000, 100000);

            emailVerificationController.initData(verifCode, enseignant);

            MailSender.sendEmail(emailETF.getText(), "Vérification Email", "Votre code de vérification est : " + verifCode);
        }
    }

    @FXML
    void AddE(ActionEvent event) throws IOException {
        try {
            userService.add(new Entreprise(nomEnTF.getText(),emailEnTF.getText(),pwdEnTF.getText(),websiteEnTF.getText(),paysEnTF.getText(),localEnTF.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        nomEnTF.getScene().setRoot(root);
    }

    @FXML
    void uploadPhoto(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");

        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String uniqueFileName = UUID.randomUUID().toString()+ file.getName();

                File destDir = new File("C:\\xampp\\htdocs");

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


    @FXML
    void UploadPhotoE(ActionEvent event) {
       
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");

        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                String uniqueFileName = UUID.randomUUID().toString()+ file.getName();

                File destDir = new File("C:\\xampp\\htdocs");

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
