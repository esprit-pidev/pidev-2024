package tn.esprit.entities.User;

public class Admin extends User{

    private String prenom;

    public Admin(int id, String nom, String email, String password, RoleName role, String prenom) {
        super(id, nom, email, password, role);
        this.prenom = prenom;
    }

    public Admin(String nom, String email, String password, RoleName role, String prenom) {
        super(nom, email, password, role);
        this.prenom = prenom;
    }

    public Admin(String nom, String email, String password, String prenom) {
        super(nom, email, password);
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", role=" + this.getRole() +
                '}';
    }
}
