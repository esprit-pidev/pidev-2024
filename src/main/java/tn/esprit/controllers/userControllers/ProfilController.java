package tn.esprit.controllers.userControllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.Etudiant;
import tn.esprit.entities.User.RoleName;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.File;

public class ProfilController {

    private final UserService userService = new UserService();

    AuthResponseDTO userLoggedIn= UserSession.getUser_LoggedIn();


    @FXML
    private ImageView ProfilPhoto;

    public void initialize() {
        String photoFileName="";
        if (userLoggedIn.getRole().equals(RoleName.STUDENT)) {
            Etudiant etudiant =(Etudiant) userService.getById(userLoggedIn.getId());
            photoFileName = "C:\\xampp\\htdocs\\img\\"+etudiant.getProfil_picture(); // Adjust path and name accordingly
        }
        if (userLoggedIn.getRole().equals(RoleName.TEACHER)) {
            Enseignant enseignant =(Enseignant) userService.getById(userLoggedIn.getId());
            photoFileName = "C:\\xampp\\htdocs\\img\\"+enseignant.getProfil_picture(); // Adjust path and name accordingly
        }

        loadImage(photoFileName);
    }

    private void loadImage(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            Image image = new Image(file.toURI().toString());
            ProfilPhoto.setImage(image);
        } else {
            // Handle case where image doesn't exist (e.g., set a default image)
            System.out.println("Image file does not exist: " + filePath);
        }
    }

}
