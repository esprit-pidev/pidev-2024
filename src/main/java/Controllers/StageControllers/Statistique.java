package Controllers.StageControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.entities.stage.Candidature;
import tn.esprit.entities.stage.Offre;
import tn.esprit.services.stageServices.CandidatureService;
import tn.esprit.services.stageServices.OffreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistique {

    @FXML
    private PieChart pi_chart;

    private final CandidatureService candidatureService = new CandidatureService();
    private final OffreService offreService = new OffreService();

    @FXML
    void initialize() {
        statPi();
    }

    public void statPi() {
        // Récupérer les données sur le nombre de candidatures par rapport au nombre d'offres
        Map<String, Long> candidatureCounts = getCandidatureCountsByOffre();

        // Calculer le total des candidatures
        long totalCandidatures = candidatureCounts.values().stream().mapToLong(Long::longValue).sum();

        // Créer les données pour le PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        candidatureCounts.forEach((offreId, count) -> {
            double percentage = (count.doubleValue() / totalCandidatures) * 100;
            PieChart.Data data = new PieChart.Data("Offre " + offreId + " (" + String.format("%.2f", percentage) + "%)", count);
            pieChartData.add(data);
        });

        // Set PieChart data
        pi_chart.setData(pieChartData);
    }


    private Map<String, Long> getCandidatureCountsByOffre() {
        // Récupérer toutes les offres
        List<Offre> offres = offreService.getAllOffres();

        // Initialiser une Map pour stocker les nombres de candidatures par offre
        Map<String, Long> candidatureCounts = new HashMap<>();

        // Pour chaque offre, récupérer le nombre de candidatures et l'ajouter à la Map
        for (Offre offre : offres) {
            List<Candidature> candidatures = candidatureService.getCandidaturesByOffre(offre);
            long count = candidatures.size();
            candidatureCounts.put(String.valueOf(offre.getTitre()), count); // Convertir int en String
        }

        return candidatureCounts;
    }

}
