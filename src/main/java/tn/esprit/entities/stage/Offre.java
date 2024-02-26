package tn.esprit.entities.stage;
import java.util.Date;
import java.util.Objects;

public class Offre {
    private int id;
    private int entreprise_id;
    private String titre;
    private String description;
    private String competences;
    private int nbr;
    private Date date ;
    public Offre(int id, int entreprise_id, String titre, String description, String competences, int nbr, Date date) {
        this.id = id;
        this.entreprise_id = entreprise_id;
        this.titre = titre;
        this.description = description;
        this.competences = competences;
        this.nbr = nbr;
        this.date = date;
    }
    public Offre(int entreprise_id, String titre, String description, String competences, int nbr, Date date) {
        this.entreprise_id = entreprise_id;
        this.titre = titre;
        this.description = description;
        this.competences = competences;
        this.nbr = nbr;
        this.date = date;
    }
    public Offre(){

    }

    public int getId() {
        return id;
    }

    public int getEntreprise_id() {
        return entreprise_id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getCompetences() {
        return competences;
    }

    public int getNbr() {
        return nbr;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEntreprise_id(int entreprise_id) {
        this.entreprise_id = entreprise_id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "offre{" +
                "id=" + id +
                ", entreprise_id=" + entreprise_id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", competences='" + competences + '\'' +
                ", nbr=" + nbr +
                ", date=" + date +
                '}';
    }
}
