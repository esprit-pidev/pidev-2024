package Controllers.userControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.User.*;
import tn.esprit.services.userServices.UserService;

import java.io.IOException;

public class AdminController {


    private final UserService userService = new UserService();

    private ObservableList<User> etudiantData = FXCollections.observableArrayList();

    private ObservableList<User> clubData = FXCollections.observableArrayList();

    private ObservableList<User> enseignantData = FXCollections.observableArrayList();

    private ObservableList<User> entreprisesData = FXCollections.observableArrayList();


    @FXML
    private TextField SearchClubTF;

    @FXML
    private TextField SearchETF;

    @FXML
    private TextField SearchEnTF;

    @FXML
    private TextField SearchTTF;

    @FXML
    private TableView<User> adminsTV;

    @FXML
    private TableView<User> etudiantsTV;

    @FXML
    private TableView<User> clubsTV;

    @FXML
    private TableView<User> enseignantsTV;

    @FXML
    private TableView<User> entreprisesTV;

    public void initialize() {
        setupEntrepriseTable();
        setupClubTable();
        setupEnseignantTable();
        setupEtudiantTable();
        loadData();
    }


    private void setupEtudiantTable() {

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");
            private final Button toRhBtn = new Button("RH");

            {
                actionBox.getChildren().addAll(deleteBtn, editBtn, toRhBtn);

                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.delete(user.getId());
                    etudiantData.remove(user);
                });

                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Etudiant etudiant = (Etudiant) userService.getById(user.getId());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantEditForm.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(loader.load()));
                        EtudiantEditFormController controller = loader.getController();
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
                        etudiantData.clear();
                        etudiantData.addAll(userService.getByRole(RoleName.STUDENT));
                        etudiantsTV.setItems(etudiantData);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });

                toRhBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.toClubRH(user.getId());
                    etudiantData.clear();
                    etudiantData.addAll(userService.getByRole(RoleName.STUDENT));
                    etudiantsTV.setItems(etudiantData);
                    clubData.clear();
                    clubData.addAll(userService.getByRole(RoleName.CLUB_RH));
                    clubsTV.setItems(clubData);
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        etudiantsTV.getColumns().add(actionCol);
    }

    private void setupClubTable() {

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");
            private final Button toStudentBtn = new Button("STUDENT");

            {
                actionBox.getChildren().addAll(deleteBtn, editBtn,toStudentBtn);

                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.delete(user.getId());
                    clubData.remove(user);
                });

                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    ResponsableClub responsableClub = (ResponsableClub) userService.getById(user.getId());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantEditForm.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(loader.load()));
                        EtudiantEditFormController controller = loader.getController();
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
                        clubData.clear();
                        clubData.addAll(userService.getByRole(RoleName.CLUB_RH));
                        clubsTV.setItems(clubData);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });

                toStudentBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.toStudent(user.getId());
                    etudiantData.clear();
                    etudiantData.addAll(userService.getByRole(RoleName.STUDENT));
                    etudiantsTV.setItems(etudiantData);
                    clubData.clear();
                    clubData.addAll(userService.getByRole(RoleName.CLUB_RH));
                    clubsTV.setItems(clubData);
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        clubsTV.getColumns().add(actionCol);
    }

    private void setupEnseignantTable() {

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");

            {
                actionBox.getChildren().addAll(deleteBtn, editBtn);

                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.delete(user.getId());
                    enseignantData.remove(user);
                });

                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Enseignant enseignant = (Enseignant) userService.getById(user.getId());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnseignantEditForm.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(loader.load()));
                        EnseignantEditFormController controller = loader.getController();
                        controller.setEnseignant(enseignant);
                        controller.setNomTF(enseignant.getNom());
                        controller.setPrenomTF(enseignant.getPrenom());
                        controller.setEmailTF(enseignant.getEmail());
                        controller.setCinTF(enseignant.getCin());
                        controller.setDateNaissanceTF(enseignant.getDate_naissance());
                        controller.setGenreCB(enseignant.getGenre());
                        stage.showAndWait();
                        enseignantData.clear();
                        enseignantData.addAll(userService.getByRole(RoleName.TEACHER));
                        enseignantsTV.setItems(enseignantData);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });

        enseignantsTV.getColumns().add(actionCol);
    }

    private void setupEntrepriseTable() {

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");

            {
                actionBox.getChildren().addAll(deleteBtn, editBtn);

                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    userService.delete(user.getId());
                    entreprisesData.remove(user);
                });

                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    Entreprise entreprise = (Entreprise) userService.getById(user.getId());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EntrepriseEditForm.fxml"));
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(new Scene(loader.load()));
                        EntrepriseEditFormController controller = loader.getController();
                        controller.setEntreprise(entreprise);
                        controller.setNomTF(entreprise.getNom());
                        controller.setPaysTF(entreprise.getPays());
                        controller.setEmailTF(entreprise.getEmail());
                        controller.setLocalTF(entreprise.getLocalisation());
                        controller.setWebsiteTF(entreprise.getWebsite());
                        stage.showAndWait();
                        entreprisesData.clear();
                        entreprisesData.addAll(userService.getByRole(RoleName.ENTREPRISE_RH));
                        entreprisesTV.setItems(entreprisesData);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });

/*
                User user = getTableView().getItems().get(getIndex());
                boolean accountStatut = userService.getById(user.getId()).getIsEnabled();
                if (accountStatut) {
                    Button blockBtn = new Button("Bloquer");
                    actionBox.getChildren().add(blockBtn);

                    blockBtn.setOnAction(event -> {
                        userService.blockUser(user.getId());
                        entreprisesData.clear();
                        entreprisesData.addAll(userService.getByRole(RoleName.ENTREPRISE_RH));
                        entreprisesTV.setItems(entreprisesData);
                    });
                } else {
                    Button unblockBtn = new Button("Débloquer");
                    actionBox.getChildren().add(unblockBtn);

                    unblockBtn.setOnAction(event -> {
                        userService.unblockUser(user.getId());
                        entreprisesData.clear();
                        entreprisesData.addAll(userService.getByRole(RoleName.ENTREPRISE_RH));
                        entreprisesTV.setItems(entreprisesData);
                    });
                }
*/


            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionBox);
                }
            }
        });


        entreprisesTV.getColumns().add(actionCol);
    }


    private void loadData() {

        etudiantData.clear();
        etudiantData.addAll(userService.getByRole(RoleName.STUDENT));
        etudiantsTV.setItems(etudiantData);

        clubData.clear();
        clubData.addAll(userService.getByRole(RoleName.CLUB_RH));
        clubsTV.setItems(clubData);

        enseignantData.clear();
        enseignantData.addAll(userService.getByRole(RoleName.TEACHER));
        enseignantsTV.setItems(enseignantData);

        entreprisesData.clear();
        entreprisesData.addAll(userService.getByRole(RoleName.ENTREPRISE_RH));
        entreprisesTV.setItems(entreprisesData);

    }

    @FXML
    void AddUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUserForm.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        loadData();

    }

    @FXML
    void searchClub(ActionEvent event) {

    }

    @FXML
    void searchE(ActionEvent event) {

    }

    @FXML
    void searchEn(ActionEvent event) {

    }

    @FXML
    void searchT(ActionEvent event) {

    }

}
