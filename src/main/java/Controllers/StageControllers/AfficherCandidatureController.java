package Controllers.StageControllers;

import tn.esprit.entities.stage.Candidature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.services.stageServices.CandidatureService;
import java.util.Date;

public class AfficherCandidatureController {

    private final CandidatureService CS = new CandidatureService();
    private Candidature C =new Candidature();

    private ObservableList<Candidature> candidatureData = FXCollections.observableArrayList();


    @FXML
    private TableView<Candidature> candidatureTV;

    @FXML
    private TableColumn<Candidature, String> competences;

    @FXML
    private TableColumn<Candidature, String> cv;

    @FXML
    private TableColumn<Candidature, Date> date;

    @FXML
    private TableColumn<Candidature, String> status;


    public void initialize() {

        loadData();

    }
    private void loadData() {

        candidatureData.clear();
        candidatureData.addAll(CS.getById(9));
        candidatureTV.setItems(candidatureData);
    }





}
