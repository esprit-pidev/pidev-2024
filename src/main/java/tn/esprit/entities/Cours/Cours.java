package tn.esprit.entities.Cours;

public class Cours {
    private int cours_Id;
    private  String nomcours;
    private String nommodule;
    private int teacher_id;
    private  int niveau;
    private  String coursURLpdf;

    public Cours() {
    }

    public Cours( String nomcours, String nommodule, int teacher_id, int niveau, String coursURLpdf) {

        this.nomcours = nomcours;
        this.nommodule = nommodule;
        this.teacher_id = teacher_id;
        this.niveau = niveau;
        this.coursURLpdf=coursURLpdf;
    }

    public Cours(int niveau, String nomcours, String nommodule) {
        this.nomcours = nomcours;
        this.nommodule = nommodule;
        this.niveau = niveau;

    }

    public Cours(int cours_Id, String nomcours, String nommodule, int teacher_id, int niveau, String coursURLpdf) {
        this.cours_Id = cours_Id;
        this.nomcours = nomcours;
        this.nommodule = nommodule;
        this.teacher_id = teacher_id;
        this.niveau = niveau;
        this.coursURLpdf = coursURLpdf;
    }

    public Cours(int cours_Id, String nomCours, String nomModule, int teacher_id, int niveau) {
        this.cours_Id=cours_Id;
        this.nomcours = nomcours;
        this.nommodule = nommodule;
        this.teacher_id = teacher_id;
        this.niveau = niveau;
    }


    public int getCours_Id() {
        return cours_Id;
    }

    public void setCours_Id(int cours_Id) {
        this.cours_Id = cours_Id;
    }

    public  String getNomcours() {
        return nomcours;
    }

    public void setNomcours(String nomcours) {
        this.nomcours = nomcours;
    }

    public  String getNommodule() {
        return nommodule;
    }

    public void setNommodule(String nommodule) {
        this.nommodule = nommodule;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public  int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
    public  String getcoursURLpdf() {
        return coursURLpdf;
    }

    public void setCoursURLpdf(String coursURLpdf) {
        this.coursURLpdf = coursURLpdf;
    }

    @Override
    public String toString() {
        return
                 nomcours + '\'' +
                ", nommodule='" + nommodule + '\'' +
                ", niveau=" + niveau;
    }
}

