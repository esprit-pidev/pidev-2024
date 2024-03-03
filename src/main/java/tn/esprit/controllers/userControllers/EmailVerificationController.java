package tn.esprit.controllers.userControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.User;
import tn.esprit.services.userServices.UserService;
import tn.esprit.tools.MailSender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class EmailVerificationController {

    private final UserService userService = new UserService();

    private int verifCode;
    private User user;

    public void initData(int verifCode, User user) {
        this.verifCode = verifCode;
        this.user = user;
    }

    @FXML
    private TextField codeTF;

    @FXML
    void verif(ActionEvent event) throws IOException {
        if (this.verifCode == Integer.parseInt(codeTF.getText())) {
            try {
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
        this.verifCode = ThreadLocalRandom.current().nextInt(10000, 100000);
        MailSender.sendEmail(user.getEmail(), "Vérification Email", "Votre code de vérification est : "+ verifCode);
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
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
