package tn.esprit.entities.Cours;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Questions {
    private int id;
    private int evaluation_id;
    private String valeur;
    private List<Option> optionList = new ArrayList<>();
    public Questions() {

    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
    public void addOption(Option opt){
        if(!this.optionList.contains(opt)){
            this.optionList.add(opt);

        }
    }
    public Questions(int id, int evaluation_id, String valeur) {
        this.id = id;
        this.evaluation_id = evaluation_id;
        this.valeur = valeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return id == questions.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
