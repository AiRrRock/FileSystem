package is.this_way.better.filesystem.gui;

import is.this_way.better.filesystem.gui.controllers.AudioPlayerWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowFactory {
    public Stage getWindow(String windowType) {
        Parent root = null;
        FXMLLoader loader;
        switch (windowType) {
            case "AudioPlayer" :
                loader = new FXMLLoader(getClass().getResource("/fxml/AudioPlayer.fxml"));
                //root= loader.load();
                AudioPlayerWindowController window = AudioPlayerWindowController.isInitialised() ? AudioPlayerWindowController.getInstance() : loader.getController();
            case "ErrorWindow" :

        }
         // The window creation itself
        Scene scene = new Scene(root, 300, 150);
        scene.getStylesheets().add(getClass().getResource("/CSS/TextEditor.css").toExternalForm());

        Stage fileCreationPopup = new Stage();
        fileCreationPopup.initStyle(StageStyle.UTILITY);
        fileCreationPopup.setTitle("Bugged audio player");
        fileCreationPopup.initModality(Modality.APPLICATION_MODAL);
        fileCreationPopup.setScene(scene);

        return new Stage();
    }

}
