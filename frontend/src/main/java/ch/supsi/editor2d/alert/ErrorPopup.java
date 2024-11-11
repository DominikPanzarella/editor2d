package ch.supsi.editor2d.alert;

import javafx.scene.control.Alert;

public class ErrorPopup extends Popup
{

    public void show(final String message) {
        // Create the dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Popup");
        alert.setHeaderText(message);
        // Add IDs to be able to test the dialog
        alert.getDialogPane().setId("errorDialog");
        alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setId("errorDialogOkButton");
        // Show the dialog
        alert.showAndWait();
    }
}
