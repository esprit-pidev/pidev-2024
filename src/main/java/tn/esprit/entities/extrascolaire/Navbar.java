package tn.esprit.entities.extrascolaire;// Sidebar.java
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Navbar extends VBox {
    public Navbar() {
        Button button1 = new Button("Option 1");
        Button button2 = new Button("Option 2");
        // Ajoutez d'autres éléments à votre sidebar selon vos besoins

        getChildren().addAll(button1, button2);
    }
}
