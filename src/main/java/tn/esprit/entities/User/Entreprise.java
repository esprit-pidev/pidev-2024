package tn.esprit.entities.User;

public class Entreprise extends User{

    private String website,pays,localisation;

    public Entreprise(int id, String nom, String email, String password, RoleName role, String website, String pays, String localisation) {
        super(id, nom, email, password, role);
        this.website = website;
        this.pays = pays;
        this.localisation = localisation;
    }

    public Entreprise(int id, String nom, String email, String password, RoleName role, String website, String pays, String localisation,boolean is_enabled) {
        super(id, nom, email, password, role);
        this.website = website;
        this.pays = pays;
        this.localisation = localisation;
        this.setIsEnabled(is_enabled);
    }

    public Entreprise(String nom, String email, String password, RoleName role, String website, String pays, String localisation) {
        super(nom, email, password, role);
        this.website = website;
        this.pays = pays;
        this.localisation = localisation;
    }

    public Entreprise(String nom, String email, String password, String website, String pays, String localisation) {
        super(nom, email, password);
        this.website = website;
        this.pays = pays;
        this.localisation = localisation;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", website='" + website + '\'' +
                ", pays='" + pays + '\'' +
                ", localisation='" + localisation + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
