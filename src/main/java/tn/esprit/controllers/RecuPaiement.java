package tn.esprit.controllers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class RecuPaiement {

    public static void genererRecuPDF(String nom, String prenom, String montantPaye, String activiteNom, String cheminFichier) {
        try {
            // Créez un document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            // Créez un nouveau contenu pour la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajoutez le logo à la page
            File file = new File("src/main/resources/logo.jpg");
            PDImageXObject logo = PDImageXObject.createFromFileByContent(file, document);
            contentStream.drawImage(logo, 10, page.getMediaBox().getHeight() - 60, logo.getWidth() / 4, logo.getHeight() / 4);

            // Ajoutez des lignes de texte avec les informations nécessaires
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(65, page.getMediaBox().getHeight() - 50);
            contentStream.showText("Reçu de Paiement");
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(-40, -50);
            contentStream.showText("Nom et prénom de l'etudiant(e): " + nom + " " + prenom);
            contentStream.newLineAtOffset(1, -15);
            contentStream.showText("Inscription à l'activité: " + activiteNom);
            contentStream.newLineAtOffset(3, -15);
            contentStream.showText("Montant payé: " + montantPaye + " Dt");
            contentStream.endText();
            // Ajoutez l'image à la page
            File f = new File("src/main/resources/thank.png");
            PDImageXObject image = PDImageXObject.createFromFileByContent(f, document);
            contentStream.drawImage(image, 90, page.getMediaBox().getHeight() - 300, image.getHeight() / 6f, image.getHeight() / 6f);



            // Dessiner une ligne de séparation
            float separationLineY = 385; // Ajustez la position verticale de la ligne de séparation
            contentStream.moveTo(50, page.getMediaBox().getHeight() - separationLineY);
            contentStream.lineTo(page.getMediaBox().getWidth() - 50, page.getMediaBox().getHeight() - separationLineY);
            contentStream.stroke();

// Ajoutez un message de remerciement
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 8);
            contentStream.newLineAtOffset(40, 20); // Ajustez la position verticale de la dernière phrase
            contentStream.showText("Merci pour votre paiement. Bienvenue chez EspritEduSmart!");
            contentStream.endText();


            // Fermez le contenu de la page
            contentStream.close();

            // Enregistrez le document PDF
            document.save(cheminFichier);

            // Fermez le document
            document.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions liées à la génération du PDF
        }
    }
}
