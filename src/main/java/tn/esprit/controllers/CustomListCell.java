package tn.esprit.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class CustomListCell extends ListCell<String> {

    private final VBox container = new VBox();
    private final Label titleLabel = new Label();
    private final Label contentLabel = new Label();

    public CustomListCell() {
        container.getChildren().addAll(titleLabel, contentLabel);
        container.setSpacing(5);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            String[] parts = item.split(":"); // Adapter en fonction de votre structure de publication
            if (parts.length == 2) {
                titleLabel.setText(parts[0]);
                contentLabel.setText(parts[1]);
                setGraphic(container);
            } else {
                setGraphic(null);
            }
        }
    }
}