package is.this_way.better.filesystem.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ErrorWindowController implements ClosableWindow{
    @FXML
    private TextField errorMessage;
    public void setErrorMessage(String error) {
        this.errorMessage.setText(error);
    }
    @FXML
    public void okPressed(ActionEvent actionEvent) {
            closeWindow(actionEvent);
    }

}
