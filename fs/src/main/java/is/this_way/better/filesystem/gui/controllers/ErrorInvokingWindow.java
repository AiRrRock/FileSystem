package is.this_way.better.filesystem.gui.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public interface ErrorInvokingWindow {
    default void showErrorWindow(String errorMessage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ErrorWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ErrorWindowController window = loader.getController();
        window.setErrorMessage(errorMessage);
        Scene scene = new Scene(root, 400, 60);
        scene.getStylesheets().add(getClass().getResource("/CSS/ErrorWindow.css").toExternalForm());
        Stage errorWindow = new Stage();
        errorWindow.initStyle(StageStyle.UTILITY);
        errorWindow.setTitle("Error window");
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setScene(scene);
        errorWindow.showAndWait();
    }
}
