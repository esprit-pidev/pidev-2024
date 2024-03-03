package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tn.esprit.entities.extrascolaire.Publication;
import tn.esprit.services.extrascolaireService.PublicationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserSession;
import tn.esprit.tools.MyDB;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Date;


public class PublierPublicationController {
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();


    @FXML
    private TextArea contenuArea;

    private byte[] imageBytes;

    // Utilisation de MyDB pour obtenir la connexion
    private Connection conn = MyDB.getInstance().getCnx();

    public void ajouterPublication() {
        try {
            // Vérifier si la connexion est ouverte avant d'appeler la méthode
            if (conn == null || conn.isClosed()) {
                // Initialiser ou réinitialiser la connexion ici
                // MyDB.getInstance().getCnx(); // Pas nécessaire car la connexion est déjà initialisée dans MyDB
            }

            // Récupérer le contenu de la TextArea
            String contenu = contenuArea.getText();
            // Vérifier la redondance du contenu
            if (estContenuRedondant(contenu)) {
                afficherAlerte("Contenu redondant", "Le contenu est déjà publié en avant. Veuillez saisir un contenu différent.");
                return;
            }

            // Vérifier les mots spécifiques
            if (contenuContientMotsInterdits(contenu)) {
                afficherAlerte("Contenu invalide", "Le contenu contient des mots interdits. Veuillez modifier le contenu.");
                return;
            }

            // Créer une nouvelle instance de la classe Publication
            Publication publication = new Publication();

            // Modification ici : passer l'ID de l'utilisateur connecté au champ club_rh_id
            publication.setClub_rh_id(userLoggedIn.getId());

            publication.setDate(new Date());
            publication.setContenu(contenu);

            // Ajouter la publication à la base de données en utilisant la méthode existante
            Publication addedPublication = ajouterPublication(publication);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Publication ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("Publication ajoutée avec succès. ID généré : " + addedPublication.getId());
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les erreurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'ajout de la publication.");
            alert.showAndWait();
        }
    }


    private Publication ajouterPublication(Publication publication) throws SQLException {
        // Utiliser votre méthode existante pour ajouter la publication à la base de données
        String requete = "INSERT INTO publication (club_rh_id, date, contenu) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, publication.getClub_rh_id());
            pst.setDate(2, new java.sql.Date(publication.getDate().getTime()));
            pst.setString(3, publication.getContenu());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                publication.setId(rs.getInt(1));
            }
        }
        return publication;
    }
    private boolean estContenuRedondant(String contenu) {
        // Assurez-vous d'avoir une instance de PublicationService accessible ici
        PublicationService publicationService = new PublicationService();

        // Utilisez la méthode du service pour vérifier si le contenu est déjà publié en avant
        return publicationService.estContenuDejaPublieEnAvant(contenu);
    }

    private boolean contenuContientMotsInterdits(String contenu) {
        // Liste des mots interdits
        String[] motsInterdits = {"fuck", "bitch", "kill"};

        // Vérifiez si le contenu contient l'un des mots interdits
        for (String mot : motsInterdits) {
            if (contenu.toLowerCase().contains(mot.toLowerCase())) {
                return true; // Le contenu contient un mot interdit
            }
        }
        return false; // Le contenu ne contient aucun mot interdit
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }


    // Méthode pour ajouter une image (peut être appelée depuis l'interface si nécessaire)

}
