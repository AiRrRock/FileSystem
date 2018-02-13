package is.this_way.better.filesystem.gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;



public interface ClosableWindow {
    default public void closeWindow(ActionEvent actionEvent){
        Stage currentStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
