package Controllers.CoursControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.services.coursServices.CoursService;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;


public class EleveController {
    @FXML
    private TextField rechercheTF;
    CoursService c = new CoursService();
    @FXML
    private VBox Courv;
    @FXML
    private Button buttonReturn;

    @FXML
    public void initialize() {
        try {
            Courv.setSpacing(25);
            displayCours();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayCours() throws SQLException {

        List<Cours> cours = c.obtenirToutesLesCours(); // Adjust this line to match your method for fetching competitions

        for (Cours cour : cours) {
            Pane produitEntry = createCoursEntry(cour);
            Courv.getChildren().add(produitEntry);
        }
    }

    public Pane createCoursEntry(Cours cours) {
        Pane coursPane = new Pane();
        coursPane.setPrefSize(912, 217);
        coursPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        // NOMCOURS
        Text coursName = new Text(300, 38, "NOM Cours: " + cours.getNomcours());
        coursName.setFont(new Font("Arial", 21));
        coursName.setEffect(new DropShadow());
        coursName.setUnderline(true);

        // NOMMODULE
        TextArea produitDescription = new TextArea("Nom Module: " + cours.getNommodule());
        produitDescription.setLayoutX(215);
        produitDescription.setLayoutY(60);
        produitDescription.setPrefHeight(91);
        produitDescription.setPrefWidth(512);
        produitDescription.setEditable(false);
        produitDescription.setWrapText(true);
        produitDescription.setFocusTraversable(false);
        produitDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitDescription.setFont(new Font("Arial", 17));
        produitDescription.setEffect(new DropShadow());

        // Niveau
        TextArea produitCout = new TextArea("Niveau: " + cours.getNiveau());
        produitCout.setLayoutX(215);
        produitCout.setLayoutY(90);
        produitCout.setPrefHeight(91);
        produitCout.setPrefWidth(512);
        produitCout.setEditable(false);
        produitCout.setWrapText(true);
        produitCout.setFocusTraversable(false);
        produitCout.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitCout.setFont(new Font("Arial", 17));
        produitCout.setEffect(new DropShadow());

        // coursurlpdf
        TextArea coururl = new TextArea("coururlpdf: " + cours.getcoursURLpdf());
        coururl.setLayoutX(215);
        coururl.setLayoutY(120);
        coururl.setPrefHeight(91);
        coururl.setPrefWidth(512);
        coururl.setEditable(false);
        coururl.setWrapText(true);
        coururl.setFocusTraversable(false);
        coururl.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        coururl.setFont(new Font("Arial", 17));
        coururl.setEffect(new DropShadow());

        coursPane.getChildren().addAll(coursName, produitDescription, produitCout, coururl);

        return coursPane;

    }

    @FXML
    public void chercher() throws SQLException {
        updateProduitDisplay(rechercheTF.getText().toLowerCase());
    }

    private void updateProduitDisplay(String recherche) throws SQLException {
        Courv.getChildren().clear(); // Effacer les salles actuellement affichées
        List<Cours> cours = c.obtenirToutesLesCours(); // Récupérer toutes les salles depuis la base de données
        for (Cours cour : cours) {
            // Vérifier si le nom de la salle contient le texte de recherche
            if (cour.getNomcours().toLowerCase().contains(recherche)) {
                Pane salleEntry = createCoursEntry(cour); // Créer une entrée pour la salle
                Courv.getChildren().add(salleEntry); // Ajouter l'entrée à la liste des salles affichées
            }
        }
    }
    @FXML
    private void trierCours(ActionEvent event) {
        try {
            List<Cours> cours = c.obtenirToutesLesCours();
            cours.sort(Comparator.comparingInt(Cours::getNiveau).reversed());
            afficherCours(cours);
        } catch (Exception e) {
            afficherAlerteErreur("Erreur lors du tri des cours", "Une erreur s'est produite lors du tri des cours.", e.getMessage());
        }
    }

    private void afficherAlerteErreur(String titre, String entete, String contenu) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle(titre);
        alerte.setHeaderText(entete);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }

    private void afficherCours(List<Cours>cour) {
        Courv.getChildren().clear(); // Effacer les produits affichés actuellement
        for ( Cours cours : cour)  {
            Pane produitEntry = createCoursEntry(cours); // Créer une entrée pour le produit
            Courv.getChildren().add(produitEntry); // Ajouter l'entrée à la liste des produits affichés
        }
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Accueil .fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}






