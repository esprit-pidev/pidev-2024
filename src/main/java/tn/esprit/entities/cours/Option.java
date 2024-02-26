package entities;

import java.util.Objects;

public class Option {
    private int id;
    private int question_id;
    private String valeur;
    private boolean is_correct;

    public Option() {
    }

    public Option(String valeur, boolean is_correct) {
        this.valeur = valeur;
        this.is_correct = is_correct;
    }

    public Option(int id, int question_id, String valeur, boolean is_correct) {
        this.id = id;
        this.question_id = question_id;
        this.valeur = valeur;
        this.is_correct = is_correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public boolean getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
