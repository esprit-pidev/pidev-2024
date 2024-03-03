package tn.esprit.entities.User;



public class User {

    private int id;
    private String nom,email,password;
    boolean isEnabled;
    private RoleName role;


    public User() {
    }

    public User(int id,RoleName role) {
     this.id=id;
     this.role=role;
    }

    public User(int id, String nom, String email, String password, RoleName role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isEnabled=true;
    }

    public User(String nom, String email, String password, RoleName role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isEnabled=true;
    }

    public User(String nom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.isEnabled=true;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
