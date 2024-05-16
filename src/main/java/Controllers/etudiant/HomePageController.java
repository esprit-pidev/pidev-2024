package Controllers.etudiant;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.project.Taches;
import tn.esprit.entities.project.fichier;
import tn.esprit.services.projectService.FichierService;
import tn.esprit.services.projectService.ProjectMembersService;
import tn.esprit.services.projectService.ProjectService;
import tn.esprit.services.projectService.TacheService;
import tn.esprit.services.userServices.AuthResponseDTO;
import tn.esprit.services.userServices.UserService;
import tn.esprit.services.userServices.UserSession;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class HomePageController implements Initializable {
    @FXML
    private VBox containerProjet;
    @FXML
    private VBox tacheD;
    @FXML
    private VBox tacheD1;
    @FXML
    private VBox tacheD2;
    @FXML
    private Button importer;
    private int membreIdClique;
    private int projetIdClique;
    private int MembreId;
    private int ProjetId;
    private boolean clicSurProjet = false;
    private int idUser;


    @FXML
    private ScrollPane afaireScrollPane;
    @FXML
    private ScrollPane encoursScrollPane;
    @FXML
    private ScrollPane termineScrollPane;
    @FXML
    private Pane containerFichiers;


    AuthResponseDTO userLoggedIn = UserSession.getUser_LoggedIn();
    private final UserService us = new UserService();
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;


    private final ProjectService projectService = new ProjectService();
    private final TacheService tacheService = new TacheService();
    private final ProjectMembersService projectMembersService = new ProjectMembersService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idUser = userLoggedIn.getId();
        System.out.println(idUser);
        List<String> userProjects = projectService.getNameProjectsByUserId(idUser);
        afficherProjet(userProjects);
        modifier.setDisable(true);
        supprimer.setDisable(true);
        importer.setOnAction(this::importerClicked);


    }

    private void afficherProjet(List<String> userProjects) {
        containerProjet.getChildren().clear();
        for (String projectName : userProjects) {
            VBox projetBox = new VBox();
            projetBox.setAlignment(Pos.CENTER);
            projetBox.setPrefHeight(43.0);
            projetBox.setPrefWidth(206.0);
            projetBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10;");
            projetBox.setSpacing(20);
            projetBox.setMargin(projetBox, new Insets(0, 0, 10, -5));
            Label nomProjetLabel = new Label(projectName);
            nomProjetLabel.setStyle("-fx-alignment: center; -fx-font-family: 'Baskerville Old Face'; -fx-font-size: 18;");
            projetBox.getChildren().add(nomProjetLabel);
            int idProject = projectService.getIdProjectByName(projectName);
            int idMembre = projectService.getIdMemberByIdUser(idUser, idProject);
            MembreId = idMembre;
            ProjetId=idProject;
            projetBox.setOnMouseClicked(null);
            projetBox.setOnMouseClicked(event -> {
                if (!event.getTarget().equals(nomProjetLabel)) {
                    List<Taches> taches = tacheService.getTacheByIdAndUserIdAndProjectId(idMembre, idProject , idUser);
                    List<Integer> allMember = tacheService.getAllTachesMember(idMembre , idProject);
                    membreIdClique = idMembre;
                    projetIdClique = idProject;
                    clicSurProjet = true;
                    FichierService fichierService = new FichierService();
                    List<fichier> fichiers = fichierService.listerFichiers(idProject);
                    afficherTaches(taches, allMember, idProject, idMembre);
                    chargerFichiersAvecImages(fichiers,idProject);
                } else {
                    clicSurProjet = true;
                }
            });

            containerProjet.getChildren().add(projetBox);



        }
    }


    private void loadAjouterTacheScene(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/AjouterTache.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
            AjouterTacheController ajouterTacheController = loader.getController();
            ajouterTacheController.setDet(membreIdClique, projetIdClique);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherTaches(List<Taches> taches, List<Integer> allMember, int projectId, int idMembre) {
        Collections.sort(taches, Comparator.comparing(Taches::getDedline));
        tacheD.getChildren().clear();
        tacheD1.getChildren().clear();
        tacheD2.getChildren().clear();
        for (Taches tache : taches) {
            VBox tacheBox = new VBox();
            tacheBox.setId("tacheBox-" + tache.getId());
            tacheBox.setCursor(Cursor.HAND);
            tacheBox.setAlignment(Pos.CENTER);
            tacheBox.setSpacing(10);
            tacheBox.prefWidthProperty().bind(tacheD.widthProperty());
            tacheBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #9F1C00; -fx-border-width: 2px; -fx-fill-height: true; -fx-margin-bottom: 10px;");
            String nomM = projectMembersService.getNombyIdUser(idUser);
            Label detailsLabel = new Label(nomM + " : " + tache.getDescription() + " \n date_ajout " + tache.getDate_ajout() + "\n deadline " + tache.getDedline());
            detailsLabel.setWrapText(true);
            detailsLabel.setStyle("-fx-font-family: 'Baskerville Old Face';");
            tacheBox.setId("task-" + tache.getId());
            if (isDedlineToday(tache.getDedline())) {
                // Créer et afficher une notification
                Image image = new Image("project/images/notification-bell.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(30); // Définir la largeur souhaitée
                imageView.setFitHeight(30); // Définir la hauteur souhaitée

                Notifications notifications = Notifications.create();
                notifications.graphic(imageView); // Utiliser l'ImageView avec l'image ajustée en taille
                notifications.title("Notification : Tâche due aujourd'hui");
                notifications.text("La tâche " + tache.getDescription() + " est due aujourd'hui.");
                notifications.hideAfter(Duration.seconds(4));
                notifications.show();
            }

            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(event -> {
                // Vérifier si au moins une case à cocher est cochée pour activer les boutons
                boolean atLeastOneChecked = tacheD.getChildren().stream()
                        .map(node -> (VBox) node)
                        .anyMatch(tb -> {
                            CheckBox cb = (CheckBox) tb.getChildren().get(0);
                            return cb.isSelected();
                        });
                boolean atLeastOneChecked1 = tacheD1.getChildren().stream()
                        .map(node -> (VBox) node)
                        .anyMatch(tb -> {
                            CheckBox cb = (CheckBox) tb.getChildren().get(0); // Assumer que la case à cocher est le premier enfant de la tacheBox
                            return cb.isSelected();
                        });
                boolean atLeastOneChecked2 = tacheD2.getChildren().stream()
                        .map(node -> (VBox) node)
                        .anyMatch(tb -> {
                            CheckBox cb = (CheckBox) tb.getChildren().get(0); // Assumer que la case à cocher est le premier enfant de la tacheBox
                            return cb.isSelected();
                        });

                // Activer ou désactiver les boutons en fonction de l'état de la case à cocher
                supprimer.setDisable(!atLeastOneChecked && !atLeastOneChecked1 && !atLeastOneChecked2);
                modifier.setDisable(!atLeastOneChecked && !atLeastOneChecked1 && !atLeastOneChecked2);
            });

            // Ajout des boutons de modification
            modifier.setOnAction(event -> {
                System.out.println("Modifier la tâche : " + tache.getId());
                String desc = tache.getDescription();
                int id = tache.getId();
                Date ded = tache.getDedline();
            });

            // Ajout des flèches et gestion des déplacements
            Label rightArrow = new Label("→");
            Label leftArrow = new Label("←");
            rightArrow.setStyle("-fx-font-size: 18; -fx-padding: 0 5;");
            leftArrow.setStyle("-fx-font-size: 18; -fx-padding: 0 5;");

            // Ajouter ici la logique des flèches pour déplacer les tâches entre les états
            leftArrow.setOnMouseClicked(event -> {
                if (tache.getEtat().equals("en cours")) {
                    tacheD.getChildren().add(tacheBox);
                    tacheD1.getChildren().remove(tacheBox);
                    tache.setEtat("à faire");
                    tacheService.updateEtat(tache.getId(), "à faire");
                } else if (tache.getEtat().equals("terminée")) {
                    tacheD1.getChildren().add(tacheBox);
                    tacheD2.getChildren().remove(tacheBox);
                    tache.setEtat("en cours");
                    tacheService.updateEtat(tache.getId(), "en cours");
                }
            });
            rightArrow.setOnMouseClicked(event -> {
                if (tache.getEtat().equals("à faire")) {
                    tacheD.getChildren().remove(tacheBox);
                    tacheD1.getChildren().add(tacheBox);
                    tache.setEtat("en cours");
                    tacheService.updateEtat(tache.getId(), "en cours");
                } else if (tache.getEtat().equals("en cours")) {
                    tacheD1.getChildren().remove(tacheBox);
                    tacheD2.getChildren().add(tacheBox);
                    tache.setEtat("terminée");
                    tacheService.updateEtat(tache.getId(), "terminée");
                }
            });

            // Ajout de la tacheBox à la VBox appropriée en fonction de son état
            switch (tache.getEtat()) {
                case "à faire":
                    tacheBox.getChildren().addAll(checkBox, detailsLabel, rightArrow);
                    tacheD.getChildren().add(tacheBox);
                    break;
                case "en cours":
                    tacheBox.getChildren().addAll(checkBox, detailsLabel, leftArrow, rightArrow);
                    tacheD1.getChildren().add(tacheBox);
                    break;
                case "terminée":
                    tacheBox.getChildren().addAll(checkBox, leftArrow, detailsLabel);
                    tacheD2.getChildren().add(tacheBox);
                    break;
                default:
                    System.out.println("État inconnu pour la tâche: " + tache.getEtat());
                    tacheD.getChildren().add(tacheBox);
                    break;
            }
        }
        for (Integer memberId : allMember) {
            if (memberId != idMembre) {
                List<Taches> memberTaches = tacheService.getTacheByIdAndProjectId(memberId, projectId);
                for (Taches tache : memberTaches) {
                    VBox tacheBox = createTacheBox(tache);
                    switch (tache.getEtat()) {
                        case "à faire":
                            afaireScrollPane.setContent(tacheBox);
                            break;
                        case "en cours":
                            encoursScrollPane.setContent(tacheBox);
                            break;
                        case "terminée":
                            termineScrollPane.setContent(tacheBox);
                            break;
                        default:
                            System.out.println("État inconnu pour la tâche: " + tache.getEtat());
                            break;
                    }
                }
            }
        }
        }
    private VBox createTacheBox(Taches tache) {
        VBox tacheBox = new VBox();
        tacheBox.setId("tacheBox-" + tache.getId());
        tacheBox.setCursor(Cursor.HAND);
        tacheBox.setAlignment(Pos.CENTER);
        tacheBox.setSpacing(10);
        tacheBox.prefWidthProperty().bind(tacheD.widthProperty());
        tacheBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #9F1C00; -fx-border-width: 2px; -fx-fill-height: true; -fx-margin-bottom: 10px;");
        // Créez le contenu de la tacheBox (label, etc.) comme vous le faites actuellement
        return tacheBox;
    }




    public void toadd(MouseEvent event) {
        if (clicSurProjet) {
            loadAjouterTacheScene(event);
        }
        clicSurProjet = false;
    }

    public void supprimer(MouseEvent event) {
        // Parcourir les enfants de tacheD, tacheD1 et tacheD2 pour trouver les tâches sélectionnées
        supprimerTachesSelectionnees(tacheD.getChildren());
        supprimerTachesSelectionnees(tacheD1.getChildren());
        supprimerTachesSelectionnees(tacheD2.getChildren());
    }

    private void supprimerTachesSelectionnees(ObservableList<Node> tacheBoxList) {
        Iterator<Node> iterator = tacheBoxList.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof VBox) {
                VBox tacheBox = (VBox) node;
                CheckBox checkBox = (CheckBox) tacheBox.getChildren().get(0);
                if (checkBox.isSelected()) {
                    String tacheBoxId = tacheBox.getId();
                    String[] parts = tacheBoxId.split("-");
                    int tacheId = Integer.parseInt(parts[1]);
                    tacheService.deleteTache(tacheId);
                    iterator.remove();
                }
            }
        }
        supprimer.setDisable(false);

    }

    public void modifier(MouseEvent event) {
        int selectedCount = 0;
        VBox selectedTacheBox = null;

        for (Node node : tacheD.getChildren()) {
            if (node instanceof VBox) {
                VBox tacheBox = (VBox) node;
                CheckBox checkBox = (CheckBox) tacheBox.getChildren().get(0);
                if (checkBox.isSelected()) {
                    selectedCount++;
                    selectedTacheBox = tacheBox;
                }
            }
        }

        for (Node node : tacheD1.getChildren()) {
            if (node instanceof VBox) {
                VBox tacheBox = (VBox) node;
                CheckBox checkBox = (CheckBox) tacheBox.getChildren().get(0);
                if (checkBox.isSelected()) {
                    selectedCount++;
                    selectedTacheBox = tacheBox;
                }
            }
        }

        for (Node node : tacheD2.getChildren()) {
            if (node instanceof VBox) {
                VBox tacheBox = (VBox) node;
                CheckBox checkBox = (CheckBox) tacheBox.getChildren().get(0);
                if (checkBox.isSelected()) {
                    selectedCount++;
                    selectedTacheBox = tacheBox;
                }

            }
        }

        if (selectedCount == 1) {
            String tacheBoxId = selectedTacheBox.getId();
            String[] parts = tacheBoxId.split("-");
            int tacheId = Integer.parseInt(parts[1]);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/ModifierTache.fxml"));
                Parent root = loader.load();
                Scene scene = ((Node) event.getSource()).getScene();
                scene.setRoot(root);
                ModifierTacheController modifierTacheController = loader.getController();
                modifierTacheController.setTacheId(tacheId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectedCount > 1) {
            // Afficher une alerte si plus d'une tacheBox est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez sélectionner qu'une seule tache pour la modifier.");
            alert.showAndWait();
        }
        modifier.setDisable(false);
    }

    public void noti(MouseEvent event) {
        Image image = new Image("project/images/notification-bell.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.title("hello");
        notifications.title("dedline");
        notifications.hideAfter(Duration.seconds(4));
        notifications.show();

    }
    private boolean isDedlineToday(Date dedline) {
        Calendar today = Calendar.getInstance();
        Calendar dedlineCal = Calendar.getInstance();
        dedlineCal.setTime(dedline);
        return today.get(Calendar.YEAR) == dedlineCal.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == dedlineCal.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == dedlineCal.get(Calendar.DAY_OF_MONTH);
    }

    @FXML
    public void importerClicked(ActionEvent actionEvent) {
        if (!clicSurProjet) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un projet avant d'importer un fichier.");
            alert.showAndWait();
        }else{
        Stage stage = (Stage) importer.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier à importer");
        File selectedFile = fileChooser.showOpenDialog(stage);
        System.out.println(MembreId);
        if (selectedFile != null) {
            String nomFichier = selectedFile.getName();
            fichier nouveauFichier = new fichier();
            nouveauFichier.setNom(nomFichier);
            nouveauFichier.setIdMember(MembreId);
            String pathFichier = selectedFile.getAbsolutePath();
            nouveauFichier.setPath(pathFichier);
            long millis = System.currentTimeMillis();
            nouveauFichier.setDate_ajout(new java.sql.Date(millis));
            FichierService fichierService = new FichierService();
            fichierService.ajouterFichier(nouveauFichier);
        }}
    }

    private void chargerFichiersAvecImages(List<fichier> fichiers, int projetId) {
        System.out.println(projetId);
        double espaceEntreFichiers = 10.0;
        HBox containerFichiersHBox = new HBox();
        containerFichiersHBox.setSpacing(espaceEntreFichiers);
        containerFichiersHBox.setAlignment(Pos.CENTER_LEFT);
        containerFichiers.getChildren().clear();

        for (fichier fichier : fichiers) {
            String imagePath = "project/images/new.png";
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(70);
            imageView.setFitHeight(70);
            Label nameLabel = new Label("Nom: " + fichier.getNom());
            Label dateLabel = new Label("Date " + fichier.getDate_ajout());
            FichierService fichierService = new FichierService();
            String nom = fichierService.getNomByIdMember(fichier.getIdMember());
            Label nomLabel = new Label(nom);
            VBox fileBox = new VBox(imageView, nameLabel, dateLabel, nomLabel);
            fileBox.setPrefHeight(100);
            fileBox.setPrefWidth(150);
            fileBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #9F1C00;");
            fileBox.setAlignment(Pos.CENTER);
            containerFichiersHBox.getChildren().add(fileBox);

            fileBox.setOnMouseClicked(event -> {
                String pathFichier = fichier.getPath();
                telechargerFichier(pathFichier);
            });
        }

        ScrollPane scrollPane = new ScrollPane(containerFichiersHBox);
        scrollPane.setPrefViewportWidth(670);
        scrollPane.setPrefViewportHeight(171);// Largeur préférée du viewport (fenêtre de visualisation)
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Afficher la barre de défilement horizontale selon les besoins
        containerFichiers.getChildren().add(scrollPane);
    }

    private void telechargerFichier(String pathFichier) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choisir l'emplacement de téléchargement");
            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
                String fileName = Paths.get(pathFichier).getFileName().toString(); // Obtenir le nom de fichier à partir du chemin source
                Path destinationPath = Paths.get(selectedDirectory.getAbsolutePath(), fileName);

                Files.copy(Paths.get(pathFichier), destinationPath);

                System.out.println("Le fichier a été téléchargé avec succès : " + destinationPath.toString());
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(destinationPath.toFile());
                } else {
                    System.out.println("Le bureau n'est pas pris en charge sur cette plateforme.");
                }
            } else {
                System.out.println("Téléchargement annulé.");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du téléchargement du fichier : " + e.getMessage());
        }
    }

    public void toFile(MouseEvent event) {
        System.out.println(idUser);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/etudiant/gestionDesFichiers.fxml"));
            Parent root = loader.load();
            gererFile controller = loader.getController();
            controller.setDet(idUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }




