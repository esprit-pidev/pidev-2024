package Controllers.StageControllers;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.Random;

public class Captcha {
    public static String verifierCaptcha() {
        // Générer une équation mathématique aléatoire
        Random random = new Random();
        int operand1 = random.nextInt(10) + 1;
        int operand2 = random.nextInt(10) + 1;
        int result = operand1 + operand2;

        // Afficher l'équation à l'utilisateur et lui demander la réponse
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Captcha");
        dialog.setHeaderText("Résolvez l'équation suivante :");
        dialog.setContentText(operand1 + " + " + operand2 + " = ");

        Optional<String> input = dialog.showAndWait();

        // Vérifier si la réponse de l'utilisateur est correcte
        if (input.isPresent()) {
            try {
                int userResult = Integer.parseInt(input.get());
                if (userResult == result) {
                    return "success";
                } else {
                    return "failure";
                }
            } catch (NumberFormatException e) {
                return "failure";
            }
        }
        return "failure";
    }
}
