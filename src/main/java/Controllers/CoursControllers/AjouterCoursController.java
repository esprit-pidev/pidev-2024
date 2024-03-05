package Controllers.CoursControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.services.coursServices.CoursService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class AjouterCoursController {
    private File uploadedFile;
    private final CoursService cs = new CoursService();
    @FXML
    private TextField niveau;

    @FXML
    private Text modulecs;
    @FXML
    private Text niveaucs;
    @FXML
    private Text nomcs;
    @FXML
    private TextField nomcours;
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn(); //user
    private final UserService us = new UserService();//user
    Enseignant enseignant = (Enseignant) us.getById(userLoggedIn.getId());//user

    @FXML
    private TextField nommodule;
    private String coursUrl = "";
    private File globalFile;
    private Path globalDestinationPath;

    public void setGlobalFile(File globalFile) {
        this.globalFile = globalFile;
    }

    public void setGlobalDestinationPath(Path globalDestinationPath) {
        this.globalDestinationPath = globalDestinationPath;
    }

    @FXML
    public void ajouter(ActionEvent actionEvent) throws MessagingException {
        // Récupération des valeurs depuis les champs de saisie
        String nomCours = nomcours.getText();
        String nomModule = nommodule.getText();
        String niveauStr = niveau.getText();

        // Validation des champs
        boolean vnom = validerNomCours(nomCours);
        boolean vnomModule = validerNomModule(nomModule);
        boolean vniveauStr = validerNiveau(niveauStr);
        boolean test = !this.coursUrl.isEmpty();

        if (vnom && vnomModule && vniveauStr && test) {
            // Conversion de niveau en entier après validation
            int niveauInt = Integer.parseInt(niveauStr);

            // Ajout du cours avec les valeurs validées
            Enseignant enseignant = (Enseignant) us.getById(userLoggedIn.getId());
            cs.ajouter(new Cours(nomCours, nomModule, enseignant, niveauInt, this.coursUrl));
            try {
                java.nio.file.Files.copy(globalFile.toPath(), globalDestinationPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                sendEmail("Maram.hlali@esprit.tn", "Noveau support de cours", nomCours);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            naviguezVersAffichercours(null);
        } else {
            // Afficher un message d'erreur en cas d'échec de la validation
            afficherMessageErreur("Erreur de validation. Vérifiez les champs.");
        }
        //  naviguezVersAffichercours(null);
    }

    @FXML
    public void naviguezVersAffichercours(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Affichercour.fxml"));
            nomcours.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherMessageErreur(String message) {


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validerNomCours(String nomCours) {
        // Vérification : nomCours doit être une chaîne non vide
        if ((!nomcours.getText().matches("[a-zA-Z]+") || nomcours.equals(""))) {
            nomcs.setVisible(true);
            return (false);
        } else {
            return (true);
        }
    }

    private boolean validerNomModule(String nomModule) {
        // Affichage pour le débogage
        System.out.println("Nom du module à valider : " + nomModule);

        // Vérification : nomModule doit être une chaîne non vide et sa longueur ne doit pas dépasser 5
        boolean validation = !nomModule.isEmpty() && nomModule.length() >= 5;
        if (validation == false) {
            modulecs.setVisible(true);
        }

        return validation;
    }

    private boolean validerNiveau(String niveauStr) {
        try {
            // Tentative de conversion en entier
            int niveauInt = Integer.parseInt(niveauStr);

            // Réinitialiser la visibilité du composant visuel
            niveaucs.setVisible(false);

            // Vérification : niveauInt doit être un entier valide
            return true;
        } catch (NumberFormatException e) {
            // Échec de la conversion, ce n'est pas un entier valide
            niveaucs.setVisible(true);
            return false;
        }
    }

    @FXML
    public void uploadFileAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // Define the destination directory
            String destinationDirectory = "C:\\xampp\\htdocs\\img";
            // Get the name of the selected file
            String originalFileName = file.getName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String randomFileName = System.currentTimeMillis() + "-" + originalFileName + fileExtension;

            // Create a Path for the destination file
            Path destinationPath = new File(destinationDirectory, randomFileName).toPath();
            setGlobalFile(file);
            setGlobalDestinationPath(destinationPath);
            // Copy the selected file to the destination directory
            this.coursUrl = randomFileName;

        } else {
            System.out.println("No file selected.");
        }

    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        final String from = "Barhoumi.balkis@esprit.tn"; // Change to your email
        final String password = "Ahmed123"; // Change to your email password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com"); // Change to your SMTP host
        props.put("mail.smtp.port", "587"); // SMTP port (587 for TLS, 465 for SSL)
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}


