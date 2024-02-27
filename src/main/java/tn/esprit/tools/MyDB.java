package tn.esprit.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyDB {

    public static final String url = "jdbc:mysql://localhost:3306/pidev";
    public static final String user = "root";
    public static final String pwd = "";
    private Connection cnx;
    public static MyDB ct;

    private MyDB() {
        try {
            this.cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            System.out.println("connexion etablie !!");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

    }

    public Connection getCnx() {
        return this.cnx;
    }

    public static MyDB getInstance() {
        if (ct == null) {
            ct = new MyDB();
        }

        return ct;
    }
}
