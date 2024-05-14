package Controllers.userControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.User.*;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {


    private final UserService userService = new UserService();


    @FXML
    private TextField SearchClubTF;

    @FXML
    AnchorPane ClubAP;

    @FXML
    private ImageView searchCIV;

    @FXML
    private ImageView searchEIV;

    @FXML
    private ImageView searchEnIV;

    @FXML
    private ImageView searchTIV;

    @FXML
    AnchorPane EntrepriseAP;

    @FXML
    AnchorPane StudentAP;

    @FXML
    AnchorPane TeacherAP;

    @FXML
    private AnchorPane GraphAp;

    @FXML
    private TextField SearchETF;

    @FXML
    private TextField SearchEnTF;

    @FXML
    private TextField SearchTTF;

    @FXML
    private BorderPane bp;


    private List<User> etudiantList = new ArrayList<>();

    private List<User> enseignantList = new ArrayList<>();

    private List<User> clubRhList = new ArrayList<>();

    private List<User> entrepriseList = new ArrayList<>();

    @FXML
    private TabPane TabPan;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private HBox password;

    @FXML
    private ImageView img4;

    @FXML
    private HBox Dashboard;

    @FXML
    private Label nbrEntreprise;

    @FXML
    private Label nbrEtud;

    @FXML
    private Label nbrRH;

    @FXML
    private Label nbrTeacher;


    public static int selectedTabIndex = 0;

    public void initialize() {
        UsersNumber();
        Graph();
        TabPan.getSelectionModel().select(selectedTabIndex);
        this.etudiantList = userService.getByRole(RoleName.STUDENT);
        this.clubRhList = userService.getByRole(RoleName.CLUB);
        this.enseignantList = userService.getByRole(RoleName.TEACHER);
        this.entrepriseList = userService.getByRole(RoleName.ENTREPRISE);
        setupEnseignantTable(enseignantList);
        setupEntrepriseTable(entrepriseList);
        setupClubTable(clubRhList);
        setupEtudiantTable(this.etudiantList);
        searchEIV.setImage(new Image(getClass().getResourceAsStream("/images/icons8-search-18.png")));
        searchCIV.setImage(new Image(getClass().getResourceAsStream("/images/icons8-search-18.png")));
        searchTIV.setImage(new Image(getClass().getResourceAsStream("/images/icons8-search-18.png")));
        searchEnIV.setImage(new Image(getClass().getResourceAsStream("/images/icons8-search-18.png")));
        img1.setImage(new Image(getClass().getResourceAsStream("/images/icons8-user-44-blck.png")));
        img2.setImage(new Image(getClass().getResourceAsStream("/images/icons8-user-44-red-red.png")));
        img3.setImage(new Image(getClass().getResourceAsStream("/images/icons8-user-44-grey.png")));
        img4.setImage(new Image(getClass().getResourceAsStream("/images/icons8-user-44-red.png")));
    }


    private void setupEtudiantTable(List<User> etudiantList) {
        StudentAP.getChildren().clear();
        GridPane studentGrid = new GridPane();
        studentGrid.setPadding(new Insets(10));


        String headerStyle = "-fx-font-weight: bold;-fx-border-color : black;-fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 15px 5px 5px ;";
        String rowStyle = "-fx-border-color : black; -fx-border-width: 0 0 0 1;-fx-padding: 8px; -fx-margin: 1px;";

        Label lblNom = new Label("Nom");
        lblNom.setStyle("-fx-font-weight: bold; -fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblPrenom = new Label("Prenom");
        lblPrenom.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblCin = new Label("Cin");
        lblCin.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 50px 5px 5px ;");
        Label lblGenre = new Label("Genre");
        lblGenre.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 30px 5px 5px ;");
        Label lblDate = new Label("Date Naissance");
        lblDate.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 5px 5px 5px ;");
        Label lblNiveau = new Label("Niveau");
        lblNiveau.setStyle(headerStyle);
        Label lblClasse = new Label("Classe");
        lblClasse.setStyle(headerStyle);
        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 150px 5px 5px ;");
        Label lblAction = new Label("Action");
        lblAction.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 1 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 200px 5px 5px ;");

        studentGrid.addRow(0, lblNom, lblPrenom, lblCin , lblGenre, lblDate,  lblNiveau, lblClasse, lblEmail, lblAction);

        int row = 1;
        int lastIndex = etudiantList.size() - 1 ;
        for (User user : etudiantList) {
            Etudiant etudiant = (Etudiant) user;
            Label Nom = new Label(etudiant.getNom());
            Label Prenom = new Label(etudiant.getPrenom());
            Label Cin = new Label(etudiant.getCin());
            Label Genre = new Label(etudiant.getGenre());
            Label Date = new Label(String.valueOf(etudiant.getDate_naissance()));
            Label Classe = new Label(etudiant.getClasse());
            Label Niveau = new Label(String.valueOf(etudiant.getNiveau()));
            Label Email = new Label(etudiant.getEmail());
            if (!(user == etudiantList.get(lastIndex))) {
                Nom.setStyle(rowStyle);
                Cin.setStyle(rowStyle);
                Genre.setStyle(rowStyle);
                Date.setStyle(rowStyle);
                Prenom.setStyle(rowStyle);
                Classe.setStyle(rowStyle);
                Niveau.setStyle(rowStyle);
                Email.setStyle(rowStyle);
            }
            else {
                Nom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 134px; -fx-margin: 1px;");
                Cin.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 75px; -fx-margin: 1px;");
                Genre.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 70px; -fx-margin: 1px;");
                Date.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 97px; -fx-margin: 1px;");
                Prenom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 151px; -fx-margin: 1px;");
                Classe.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 56.5px; -fx-margin: 1px;");
                Niveau.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 61px; -fx-margin: 1px;");
                Email.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 188px; -fx-margin: 1px;");
            }


            final HBox actionBox = new HBox(10);

            Image deleteImage = new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png"));
            ImageView deleteImageView = new ImageView(deleteImage);

            deleteImageView.setFitHeight(15);
            deleteImageView.setFitWidth(15);

            final Button deleteBtn = new Button();
            deleteBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            deleteBtn.setGraphic(deleteImageView);

            Image editImage = new Image(getClass().getResourceAsStream("/images/icons8-edit-25.png"));
            ImageView editImageView = new ImageView(editImage);

            editImageView.setFitHeight(15);
            editImageView.setFitWidth(15);

            final Button editBtn = new Button();
            editBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            editBtn.setGraphic(editImageView);

            final Button toRhBtn = new Button("RH");
            toRhBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button blockBtn = new Button("Bloquer");
            blockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button unblockBtn = new Button("Débloquer");
            unblockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final HBox accessBox = new HBox(10);


            if (etudiant.getIsEnabled()) {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(blockBtn);
                blockBtn.setOnAction(event -> {
                    userService.blockUser(etudiant.getId());
                    selectedTabIndex = 1;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        StudentAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else  {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(unblockBtn);
                unblockBtn.setOnAction(event -> {
                    userService.unblockUser(etudiant.getId());
                    selectedTabIndex = 1;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        StudentAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            actionBox.getChildren().addAll(deleteBtn, editBtn,accessBox,toRhBtn);

            deleteBtn.setOnAction(event -> {
                userService.delete(etudiant.getId());
                selectedTabIndex = 1;
                initialize();
            });

            editBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantEditForm.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Modifier etudiant");
                    EtudiantEditFormController controller = loader.getController();
                    controller.setAdminController(this);
                    controller.setEtudiant(etudiant);
                    controller.setNomTF(etudiant.getNom());
                    controller.setPrenomTF(etudiant.getPrenom());
                    controller.setEmailTF(etudiant.getEmail());
                    controller.setNiveauTF(etudiant.getNiveau());
                    controller.setCinTF(etudiant.getCin());
                    controller.setClasseTF(etudiant.getClasse());
                    controller.setDateNaissanceTF(etudiant.getDate_naissance());
                    controller.setGenreCB(etudiant.getGenre());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            toRhBtn.setOnAction(event -> {
                userService.toClubRH(user.getId());
                selectedTabIndex = 1;
                initialize();
            });

            if (!(user == etudiantList.get(lastIndex))) {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 0 1;-fx-padding: 3px 5px 3px 16px;");
            }
            else {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 1 1;-fx-padding: 3px 5px 3px 16px ;-fx-min-width : 200px;");
            }

            studentGrid.addRow(row++, Nom,Prenom,Cin,Genre,Date,Niveau,Classe,Email,actionBox);
        }

        ScrollPane scrollPane = new ScrollPane(studentGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(271);
        scrollPane.setPrefWidth(1117);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: white;");

        StudentAP.getChildren().add(scrollPane);

    }

    private void setupClubTable(List<User> clubRhList) {
        ClubAP.getChildren().clear();
        GridPane clubRhGrid = new GridPane();
        clubRhGrid.setPadding(new Insets(10));


        String headerStyle = "-fx-font-weight: bold;-fx-border-color : black;-fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 15px 5px 5px ;";
        String rowStyle = "-fx-border-color : black; -fx-border-width: 0 0 0 1;-fx-padding: 8px; -fx-margin: 1px;";

        Label lblNom = new Label("Nom");
        lblNom.setStyle("-fx-font-weight: bold; -fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblPrenom = new Label("Prenom");
        lblPrenom.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblCin = new Label("Cin");
        lblCin.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 50px 5px 5px ;");
        Label lblGenre = new Label("Genre");
        lblGenre.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 30px 5px 5px ;");
        Label lblDate = new Label("Date Naissance");
        lblDate.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 5px 5px 5px ;");
        Label lblNiveau = new Label("Niveau");
        lblNiveau.setStyle(headerStyle);
        Label lblClasse = new Label("Classe");
        lblClasse.setStyle(headerStyle);
        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 150px 5px 5px ;");
        Label lblAction = new Label("Action");
        lblAction.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 1 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 200px 5px 5px ;");

        clubRhGrid.addRow(0, lblNom, lblPrenom, lblCin , lblGenre, lblDate,  lblNiveau, lblClasse, lblEmail, lblAction);

        int row = 1;
        int lastIndex = clubRhList.size() - 1 ;
        for (User user : clubRhList) {
            ResponsableClub responsableClub = (ResponsableClub) user;
            Label Nom = new Label(responsableClub.getNom());
            Label Prenom = new Label(responsableClub.getPrenom());
            Label Cin = new Label(responsableClub.getCin());
            Label Genre = new Label(responsableClub.getGenre());
            Label Date = new Label(String.valueOf(responsableClub.getDate_naissance()));
            Label Classe = new Label(responsableClub.getClasse());
            Label Niveau = new Label(String.valueOf(responsableClub.getNiveau()));
            Label Email = new Label(responsableClub.getEmail());
            if (!(user == clubRhList.get(lastIndex))) {
                Nom.setStyle(rowStyle);
                Cin.setStyle(rowStyle);
                Genre.setStyle(rowStyle);
                Date.setStyle(rowStyle);
                Prenom.setStyle(rowStyle);
                Classe.setStyle(rowStyle);
                Niveau.setStyle(rowStyle);
                Email.setStyle(rowStyle);
            }
            else {
                Nom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 134px; -fx-margin: 1px;");
                Cin.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 75px; -fx-margin: 1px;");
                Genre.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 70px; -fx-margin: 1px;");
                Date.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 97px; -fx-margin: 1px;");
                Prenom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 151px; -fx-margin: 1px;");
                Classe.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 56.5px; -fx-margin: 1px;");
                Niveau.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 61px; -fx-margin: 1px;");
                Email.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 188px; -fx-margin: 1px;");
            }

            final HBox actionBox = new HBox(10);

            Image deleteImage = new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png"));
            ImageView deleteImageView = new ImageView(deleteImage);

            deleteImageView.setFitHeight(15);
            deleteImageView.setFitWidth(15);

            final Button deleteBtn = new Button();
            deleteBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            deleteBtn.setGraphic(deleteImageView);

            Image editImage = new Image(getClass().getResourceAsStream("/images/icons8-edit-25.png"));
            ImageView editImageView = new ImageView(editImage);

            editImageView.setFitHeight(15);
            editImageView.setFitWidth(15);

            final Button editBtn = new Button();
            editBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            editBtn.setGraphic(editImageView);

            final Button toStudentBtn = new Button("Etud");
            toStudentBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button blockBtn = new Button("Block");
            blockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button unblockBtn = new Button("Unblock");
            unblockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final HBox accessBox = new HBox(10);


            if (responsableClub.getIsEnabled()) {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(blockBtn);
                blockBtn.setOnAction(event -> {
                    userService.blockUser(responsableClub.getId());
                    selectedTabIndex = 2;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        ClubAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else  {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(unblockBtn);
                unblockBtn.setOnAction(event -> {
                    userService.unblockUser(responsableClub.getId());
                    selectedTabIndex = 2;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        ClubAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            actionBox.getChildren().addAll(deleteBtn, editBtn,accessBox,toStudentBtn);

            deleteBtn.setOnAction(event -> {
                userService.delete(responsableClub.getId());
                selectedTabIndex = 2;
                initialize();
            });

            editBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantEditForm.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Modifier responsable club");
                    EtudiantEditFormController controller = loader.getController();
                    controller.setAdminController(this);
                    controller.setResponsableClub(responsableClub);
                    controller.setNomTF(responsableClub.getNom());
                    controller.setPrenomTF(responsableClub.getPrenom());
                    controller.setEmailTF(responsableClub.getEmail());
                    controller.setNiveauTF(responsableClub.getNiveau());
                    controller.setCinTF(responsableClub.getCin());
                    controller.setClasseTF(responsableClub.getClasse());
                    controller.setDateNaissanceTF(responsableClub.getDate_naissance());
                    controller.setGenreCB(responsableClub.getGenre());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            toStudentBtn.setOnAction(event -> {
                userService.toStudent(user.getId());
                selectedTabIndex = 2;
                initialize();
            });

            if (!(user == clubRhList.get(lastIndex))) {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 0 1;-fx-padding: 3px 5px 3px 16px;");
            }
            else {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 1 1;-fx-padding: 3px 5px 3px 16px ;-fx-min-width : 200px;");
            }

            clubRhGrid.addRow(row++, Nom,Prenom,Cin,Genre,Date,Niveau,Classe,Email,actionBox);
        }

        ScrollPane scrollPane = new ScrollPane(clubRhGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(271);
        scrollPane.setPrefWidth(1117);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: white;");

        ClubAP.getChildren().add(scrollPane);
    }

    private void setupEnseignantTable(List<User> enseignantList) {

        TeacherAP.getChildren().clear();
        GridPane teacherGrid = new GridPane();
        teacherGrid.setPadding(new Insets(10));


        String headerStyle = "-fx-font-weight: bold;-fx-border-color : black;-fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 15px 5px 5px ;";
        String rowStyle = "-fx-border-color : black; -fx-border-width: 0 0 0 1;-fx-padding: 8px; -fx-margin: 1px;";

        Label lblNom = new Label("Nom");
        lblNom.setStyle("-fx-font-weight: bold; -fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblPrenom = new Label("Prenom");
        lblPrenom.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblCin = new Label("Cin");
        lblCin.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 50px 5px 5px ;");
        Label lblGenre = new Label("Genre");
        lblGenre.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 30px 5px 5px ;");
        Label lblDate = new Label("Date Naissance");
        lblDate.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 5px 5px 5px ;");
        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 150px 5px 5px ;");
        Label lblAction = new Label("Action");
        lblAction.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 1 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 155px 5px 5px ;");

        teacherGrid.addRow(0, lblNom, lblPrenom, lblCin , lblGenre, lblDate, lblEmail, lblAction);

        int row = 1;
        int lastIndex = enseignantList.size() - 1 ;
        for (User user : enseignantList) {
            Enseignant enseignant = (Enseignant) user;
            Label Nom = new Label(enseignant.getNom());
            Label Prenom = new Label(enseignant.getPrenom());
            Label Cin = new Label(enseignant.getCin());
            Label Genre = new Label(enseignant.getGenre());
            Label Date = new Label(String.valueOf(enseignant.getDate_naissance()));
            Label Email = new Label(enseignant.getEmail());
            if (!(user == enseignantList.get(lastIndex))) {
                Nom.setStyle(rowStyle);
                Cin.setStyle(rowStyle);
                Genre.setStyle(rowStyle);
                Date.setStyle(rowStyle);
                Prenom.setStyle(rowStyle);
                Email.setStyle(rowStyle);
            }
            else {
                Nom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 134px; -fx-margin: 1px;");
                Cin.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 75px; -fx-margin: 1px;");
                Genre.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 70px; -fx-margin: 1px;");
                Date.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 97px; -fx-margin: 1px;");
                Prenom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 151px; -fx-margin: 1px;");
                Email.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 188px; -fx-margin: 1px;");
            }


            final HBox actionBox = new HBox(10);

            Image deleteImage = new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png"));
            ImageView deleteImageView = new ImageView(deleteImage);

            deleteImageView.setFitHeight(15);
            deleteImageView.setFitWidth(15);

            final Button deleteBtn = new Button();
            deleteBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            deleteBtn.setGraphic(deleteImageView);

            Image editImage = new Image(getClass().getResourceAsStream("/images/icons8-edit-25.png"));
            ImageView editImageView = new ImageView(editImage);

            editImageView.setFitHeight(15);
            editImageView.setFitWidth(15);

            final Button editBtn = new Button();
            editBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            editBtn.setGraphic(editImageView);

            final Button blockBtn = new Button("Bloquer");
            blockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button unblockBtn = new Button("Débloquer");
            unblockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final HBox accessBox = new HBox(10);


            if (enseignant.getIsEnabled()) {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(blockBtn);
                blockBtn.setOnAction(event -> {
                    userService.blockUser(enseignant.getId());
                    selectedTabIndex = 0;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        TeacherAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else  {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(unblockBtn);
                unblockBtn.setOnAction(event -> {
                    userService.unblockUser(enseignant.getId());
                    selectedTabIndex = 0;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        TeacherAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            actionBox.getChildren().addAll(deleteBtn, editBtn,accessBox);

            deleteBtn.setOnAction(event -> {
                userService.delete(enseignant.getId());
                selectedTabIndex = 0;
                initialize();
            });

            editBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnseignantEditForm.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Modifier enseignant");
                    EnseignantEditFormController controller = loader.getController();
                    controller.setAdminController(this);
                    controller.setEnseignant(enseignant);
                    controller.setNomTF(enseignant.getNom());
                    controller.setPrenomTF(enseignant.getPrenom());
                    controller.setEmailTF(enseignant.getEmail());
                    controller.setCinTF(enseignant.getCin());
                    controller.setDateNaissanceTF(enseignant.getDate_naissance());
                    controller.setGenreCB(enseignant.getGenre());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            if (!(user == enseignantList.get(lastIndex))) {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 0 1;-fx-padding: 3px 5px 3px 16px;");
            }
            else {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 1 1;-fx-padding: 3px 5px 3px 16px ;-fx-min-width : 175px;");
            }

            teacherGrid.addRow(row++, Nom,Prenom,Cin,Genre,Date,Email,actionBox);
        }

        ScrollPane scrollPane = new ScrollPane(teacherGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(271);
        scrollPane.setPrefWidth(1086);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: white;");

        TeacherAP.getChildren().add(scrollPane);

    }

    private void setupEntrepriseTable(List<User> entrepriseList) {
        EntrepriseAP.getChildren().clear();
        GridPane entrepriseGrid = new GridPane();
        entrepriseGrid.setPadding(new Insets(10));


        String rowStyle = "-fx-border-color : black; -fx-border-width: 0 0 0 1;-fx-padding: 8px; -fx-margin: 1px;";

        Label lblNom = new Label("Nom");
        lblNom.setStyle("-fx-font-weight: bold; -fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblPays = new Label("Pays");
        lblPays.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblLocalisation = new Label("Localisation");
        lblLocalisation.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblWebsite = new Label("Site Web");
        lblWebsite.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 100px 5px 5px ;");
        Label lblEmail = new Label("Email");
        lblEmail.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 0 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 150px 5px 5px ;");
        Label lblAction = new Label("Action");
        lblAction.setStyle("-fx-font-weight: bold;-fx-border-color : black; -fx-border-width: 1 1 1 1; -fx-background-color: #9f1c00; -fx-text-fill: white; -fx-padding: 5px 155px 5px 5px ;");

        entrepriseGrid.addRow(0, lblNom, lblPays, lblLocalisation , lblWebsite, lblEmail, lblAction);

        int row = 1;
        int lastIndex = entrepriseList.size() - 1 ;
        for (User user : entrepriseList) {
            Entreprise entreprise = (Entreprise) user;
            Label Nom = new Label(entreprise.getNom());
            Label Pays = new Label(entreprise.getPays());
            Label Localisation = new Label(entreprise.getLocalisation());
            Label Website = new Label(entreprise.getWebsite());
            Label Email = new Label(entreprise.getEmail());
            if (!(user == entrepriseList.get(lastIndex))) {
                Nom.setStyle(rowStyle);
                Pays.setStyle(rowStyle);
                Localisation.setStyle(rowStyle);
                Website.setStyle(rowStyle);
                Email.setStyle(rowStyle);
            }
            else {
                Nom.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 134px; -fx-margin: 1px;");
                Pays.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 132px; -fx-margin: 1px;");
                Localisation.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 173px; -fx-margin: 1px;");
                Website.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 157.5px; -fx-margin: 1px;");
                Email.setStyle("-fx-border-color : black; -fx-border-width: 0 0 1 1;-fx-padding: 8px;-fx-min-width : 188px; -fx-margin: 1px;");
            }


            final HBox actionBox = new HBox(10);

            Image deleteImage = new Image(getClass().getResourceAsStream("/images/icons8-delete-24.png"));
            ImageView deleteImageView = new ImageView(deleteImage);

            deleteImageView.setFitHeight(15);
            deleteImageView.setFitWidth(15);

            final Button deleteBtn = new Button();
            deleteBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            deleteBtn.setGraphic(deleteImageView);

            Image editImage = new Image(getClass().getResourceAsStream("/images/icons8-edit-25.png"));
            ImageView editImageView = new ImageView(editImage);

            editImageView.setFitHeight(15);
            editImageView.setFitWidth(15);

            final Button editBtn = new Button();
            editBtn.setStyle("-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            editBtn.setGraphic(editImageView);

            final Button blockBtn = new Button("Bloquer");
            blockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final Button unblockBtn = new Button("Débloquer");
            unblockBtn.setStyle("-fx-text-fill: #9f1c00;-fx-font-weight: bold;-fx-background-color : white;-fx-border-color : #9f1c00; -fx-border-radius : 10px");
            final HBox accessBox = new HBox(10);


            if (entreprise.getIsEnabled()) {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(blockBtn);
                blockBtn.setOnAction(event -> {
                    userService.blockUser(entreprise.getId());
                    selectedTabIndex = 3;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        EntrepriseAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else  {
                accessBox.getChildren().clear();
                accessBox.getChildren().add(unblockBtn);
                unblockBtn.setOnAction(event -> {
                    userService.unblockUser(entreprise.getId());
                    selectedTabIndex = 3;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    try {
                        Parent root = loader.load();
                        EntrepriseAP.getScene().setRoot(root);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            actionBox.getChildren().addAll(deleteBtn, editBtn,accessBox);

            deleteBtn.setOnAction(event -> {
                userService.delete(entreprise.getId());
                selectedTabIndex = 3;
                initialize();
            });

            editBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/EntrepriseEditForm.fxml"));
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Modifier entreprise");
                    EntrepriseEditFormController controller = loader.getController();
                    controller.setAdminController(this);
                    controller.setEntreprise(entreprise);
                    controller.setNomTF(entreprise.getNom());
                    controller.setPaysTF(entreprise.getPays());
                    controller.setEmailTF(entreprise.getEmail());
                    controller.setLocalTF(entreprise.getLocalisation());
                    controller.setWebsiteTF(entreprise.getWebsite());
                    stage.showAndWait();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });

            if (!(user == entrepriseList.get(lastIndex))) {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 0 1;-fx-padding: 3px 5px 3px 16px;");
            }
            else {
                actionBox.setStyle("-fx-border-color : black; -fx-border-width: 0 1 1 1;-fx-padding: 3px 5px 3px 16px ;-fx-min-width : 175px;");
            }

            entrepriseGrid.addRow(row++, Nom,Pays,Localisation,Website,Email,actionBox);
        }

        ScrollPane scrollPane = new ScrollPane(entrepriseGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(271);
        scrollPane.setPrefWidth(1086);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: white;");

        EntrepriseAP.getChildren().add(scrollPane);
    }


    @FXML
    void AddUser(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUserForm.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Ajouter utilisateur");
        AddUserFormController controller = loader.getController();
        controller.setAdminController(this);
        stage.showAndWait();
    }

    @FXML
    void searchClub(MouseEvent event) {
        String searchText = SearchClubTF.getText().toLowerCase();
        this.clubRhList = new ArrayList<>();
        this.clubRhList = userService.getByRole(RoleName.CLUB).stream()
                .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        setupClubTable(this.clubRhList);
    }

    @FXML
    void searchE(MouseEvent event) {
        String searchText = SearchETF.getText().toLowerCase();
        this.etudiantList = new ArrayList<>();
        this.etudiantList = userService.getByRole(RoleName.STUDENT).stream()
                .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        setupEtudiantTable(this.etudiantList);
    }

    @FXML
    void searchEn(MouseEvent event) {
        String searchText = SearchEnTF.getText().toLowerCase();
        this.entrepriseList = new ArrayList<>();
        this.entrepriseList = userService.getByRole(RoleName.ENTREPRISE).stream()
                .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        setupEntrepriseTable(this.entrepriseList);
    }

    @FXML
    void searchT(MouseEvent event) {
        String searchText = SearchTTF.getText().toLowerCase();
        this.enseignantList = new ArrayList<>();
        this.enseignantList = userService.getByRole(RoleName.TEACHER).stream()
                .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
        setupEnseignantTable(this.enseignantList);
    }

    public void reloadLoginScene(int i) {
        selectedTabIndex = i;
        initialize();
    }

    public void Graph() {
        GraphAp.getChildren().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Integer responsableClubCount = userService.getUserCountByRole().get(RoleName.CLUB);
        if (responsableClubCount != null && responsableClubCount != 0) {
            pieChartData.add(new PieChart.Data("Responsable Club", responsableClubCount));
        }

        Integer entrepriseCount = userService.getUserCountByRole().get(RoleName.ENTREPRISE);
        if (entrepriseCount != null && entrepriseCount != 0) {
            pieChartData.add(new PieChart.Data("Entreprise", entrepriseCount));
        }

        Integer etudiantCount = userService.getUserCountByRole().get(RoleName.STUDENT);
        if (etudiantCount != null && etudiantCount != 0) {
            pieChartData.add(new PieChart.Data("Etudiant", etudiantCount));
        }

        Integer enseignantCount = userService.getUserCountByRole().get(RoleName.TEACHER);
        if (enseignantCount != null && enseignantCount != 0) {
            pieChartData.add(new PieChart.Data("Enseignant", enseignantCount));
        }


        final PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Distribution des utilisateurs par role");

        pieChart.setPrefSize(597, 275);

        applyCustomColors(pieChartData);
        pieChart.setLegendVisible(false);

        GraphAp.getChildren().add(pieChart);
    }
    private void applyCustomColors(ObservableList<PieChart.Data> pieChartData) {
        String[] colors = {"#B9B9B9", "#9F1C00", "#781C10", "#262428"};
        int colorIndex = 0;

        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + colors[colorIndex++ % colors.length] + ";");
        }
    }

    private void UsersNumber() {
        nbrEntreprise.setText(String.valueOf(userService.getUserCountByRole().get(RoleName.ENTREPRISE) != null ?
                userService.getUserCountByRole().get(RoleName.ENTREPRISE) : 0));

        nbrEtud.setText(String.valueOf(userService.getUserCountByRole().get(RoleName.STUDENT) != null ?
                userService.getUserCountByRole().get(RoleName.STUDENT) : 0));

        nbrTeacher.setText(String.valueOf(userService.getUserCountByRole().get(RoleName.TEACHER) != null ?
                userService.getUserCountByRole().get(RoleName.TEACHER) : 0));

        nbrRH.setText(String.valueOf(userService.getUserCountByRole().get(RoleName.CLUB) != null ?
                userService.getUserCountByRole().get(RoleName.CLUB) : 0));

    }



    @FXML
    void editPassword(MouseEvent event) throws IOException {
        password.setStyle("-fx-background-color : #781C10;");
        Dashboard.setStyle("-fx-background-color :  #9F1C00;");
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/editPassword.fxml"));
        bp.setCenter(root);
    }

    @FXML
    void goToAdminPage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
        Parent root = loader.load();
        bp.getScene().setRoot(root);
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        UserSession.Logout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        ClubAP.getScene().setRoot(root);
    }

}
