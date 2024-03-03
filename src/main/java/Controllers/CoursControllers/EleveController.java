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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Cours.Cours;
import tn.esprit.entities.Cours.Evaluation;
import tn.esprit.entities.User.Enseignant;
import tn.esprit.entities.User.User;
import tn.esprit.services.coursServices.CoursService;
import tn.esprit.services.coursServices.EvaluationService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class EleveController {
    @FXML
    private TextField rechercheTF;
    public int evaluationid;
    CoursService c = new CoursService();
    EvaluationService e= new EvaluationService();
    @FXML
    private VBox Courv;
    @FXML
    private Button buttonReturn;
    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn(); //user

private User user=userLoggedIn;
    final EvaluationUserController euc = new EvaluationUserController();

    private int id;

    void setId(int id){
        this.id=id;
    }

    List<Integer> listIds = new ArrayList<>();



    @FXML
    public void initialize() {
        System.out.println(user);
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
            listIds.add(cour.getCours_Id());
            setId(cour.getCours_Id());
            Pane produitEntry = createCoursEntry(cour);
            Courv.getChildren().add(produitEntry);
        }
    }


    public Pane createCoursEntry(Cours cours) {
        Pane coursPane = new Pane();
        coursPane.setPrefSize(912, 217);
        coursPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: #781C10;-fx-background-radius: 11; ");

        // NOMCOURS
        Text coursName = new Text(300, 38,  cours.getNomcours());
        coursName.setFont(new Font("Arial", 28));
        coursName.setEffect(new DropShadow());
        coursName.setUnderline(true);
        coursName.setFill(Color.WHITE);
        Button evaluation = new Button("Evaluation");
        evaluation.setLayoutX(750);
        evaluation.setLayoutY(100);
        evaluation.setPrefWidth(100);
        evaluation.setPrefHeight(50);

        evaluation.setOnAction(actionEvent -> naviguezVersEvaluation(actionEvent,cours.getCours_Id()));

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
        coururl.setOnMouseClicked(event -> downloadPDF(event,cours.getcoursURLpdf()));
        coursPane.getChildren().addAll(coursName, produitDescription, produitCout, coururl,evaluation);

        return coursPane;

    }

    private void downloadPDF(MouseEvent event, String s) {
        // Assuming pdfFile is the File object representing your PDF file
        File pdfFile = new File("C:\\xampp\\htdocs\\img\\"+s);

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");

        // Set the extension filter to only allow PDF files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the save file dialog
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File saveFile = fileChooser.showSaveDialog(stage);

        // Check if the user selected a file
        if (saveFile != null) {
            try {
                // Copy the PDF file to the selected location
                Files.copy(pdfFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("PDF File downloaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an alert)
            }
        }
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
    List<Evaluation> evals;
    @FXML
     void naviguezVersEvaluation(ActionEvent event,int courid) {
         try {
             // Correctly create an FXMLLoader instance pointing to your FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/EvaluationUser.fxml"));

             // Load the FXML and get the root node in one step
             Parent root = loader.load();
             EvaluationUserController evaluationUserController=new EvaluationUserController();
             evaluationUserController.setUserId(user);

             // Now that the FXML is loaded, get the controller
             CoursService cs=new CoursService();

                 evals= cs.getEvaluationByIdCours(courid);
                 System.out.println("in eleve controller evals list"+evals);

                 for (int j = 0; j < evals.size(); j++) {
                     Evaluation ev = evals.get(j);

                     euc.setEvaluationId(ev.getId()); // Set the evaluation ID from the first evaluation

                     System.out.println(ev.getTitre());
                 }
             buttonReturn.getScene().setRoot(root);
         } catch (IOException e) {
             System.err.println(e.getMessage());
         } catch (SQLException ex) {
             throw new RuntimeException(ex);
         }
    }
    public List<Evaluation> gettevaluation()
    {
        return evals;
    }


}






