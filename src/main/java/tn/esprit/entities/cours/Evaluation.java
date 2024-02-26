package entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Evaluation {


    private int id;
    private int cours_Id;
    private String titre;
    private List<Questions> questions = new ArrayList<Questions>();

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
    public void AddQuestion(Questions question){
        if(!this.questions.contains(question)){
            this.questions.add(question);

        }
    }

    public Evaluation(String text, String titreevaluationText, int cours_Id) {

    }
    public Evaluation(String titre,int cour_Id){
        this.titre = titre;
        this.cours_Id = cour_Id;
    }
    public Evaluation(int id, int cours_Id, String titre) {
        this.id = id;
        this.cours_Id = cours_Id;
        this.titre = titre;
    }

    public Evaluation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCours_Id() {
        return cours_Id;
    }

    public void setCours_Id(int cours_Id) {
        this.cours_Id = cours_Id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return titre;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evaluation that = (Evaluation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
