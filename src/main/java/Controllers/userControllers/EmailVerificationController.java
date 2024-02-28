package Controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.PasswordResetRequest;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.PasswordResetRequestService;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MailSender;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class EmailVerificationController {

    private final UserService userService = new UserService();

    private final PasswordResetRequestService passwordResetRequestService = new PasswordResetRequestService();

    private int id;

    private User user;

    public void initData(int id,User user) {
        this.id = id;
        this.user = user;
    }

    @FXML
    private TextField codeTF;

    @FXML
    void verif(ActionEvent event) throws IOException {


        if (passwordResetRequestService.getById(id).getResetCode() != Integer.parseInt(codeTF.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code error");
            alert.setHeaderText(null);
            alert.setContentText("Code erroné");
            alert.showAndWait();
        }
        else if (LocalDateTime.now().isAfter(passwordResetRequestService.getById(id).getExpiresAt())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Code expiré");
            alert.setHeaderText(null);
            alert.setContentText("Code expiré");
            alert.showAndWait();
        }
        else {
            try {
                passwordResetRequestService.delete(id);
                userService.add(user);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root = loader.load();
                codeTF.getScene().setRoot(root);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void renvoi(ActionEvent event) {
        passwordResetRequestService.delete(id);
        int verifCode = ThreadLocalRandom.current().nextInt(10000, 100000);
        this.id = passwordResetRequestService.Add(new PasswordResetRequest(verifCode, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10),true,null));
        MailSender.sendEmail(user.getEmail(), "Vérification Email", "Votre code de vérification est : "+ verifCode);
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        passwordResetRequestService.delete(id);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
        Parent root = loader.load();
        SignUpController sign = loader.getController();
        if (user instanceof Etudiant etudiant) {
            sign.setNomTF(etudiant.getNom());
            sign.setPrenomTF(etudiant.getPrenom());
            sign.setEmailTF(etudiant.getEmail());
            sign.setPwdTF(etudiant.getPassword());
            sign.setNiveauTF(etudiant.getNiveau());
            sign.setCinTF(etudiant.getCin());
            sign.setClasseTF(etudiant.getClasse());
            sign.setDateNaissanceTF(etudiant.getDate_naissance());
            sign.setUploadedPhotoName(etudiant.getProfil_picture());
            sign.setGenreCB(etudiant.getGenre());
        }

        if (user instanceof Enseignant enseignant) {
            sign.setNomETF(enseignant.getNom());
            sign.setPrenomETF(enseignant.getPrenom());
            sign.setEmailETF(enseignant.getEmail());
            sign.setPdwETF(enseignant.getPassword());
            sign.setCinETF(enseignant.getCin());
            sign.setDateNaissanceETF(enseignant.getDate_naissance());
            sign.setUploadedPhotoName(enseignant.getProfil_picture());
            sign.setGenreECB(enseignant.getGenre());
        }

        codeTF.getScene().setRoot(root);
    }

}
