package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.controllers.AudioPlayerWindowController;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OpenAudioFileEventHandler extends OpenFileEventHandler implements EventHandler {
    private File file;

    public OpenAudioFileEventHandler(File file){
        super(file);
        this.file=file;
    }
    @Override
    public void handle(Event event) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AudioPlayer.fxml"));
            root = loader.load();
            AudioPlayerWindowController window = AudioPlayerWindowController.isInitialised() ? AudioPlayerWindowController.getInstance() : loader.getController();
            // The window creation itself
            Scene scene = new Scene(root, 300, 150);
            scene.getStylesheets().add(getClass().getResource("/CSS/TextEditor.css").toExternalForm());
            Stage fileCreationPopup = new Stage();
            fileCreationPopup.initStyle(StageStyle.UTILITY);
            fileCreationPopup.setTitle("Bugged audio player");
            fileCreationPopup.initModality(Modality.APPLICATION_MODAL);
            fileCreationPopup.setScene(scene);
            window.init(file);
            fileCreationPopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
