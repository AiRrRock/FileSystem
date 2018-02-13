package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.controllers.TextEditorController;
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

public class OpenTextFileEventHandler extends OpenFileEventHandler implements EventHandler  {
    private File file;

    public OpenTextFileEventHandler(File file){
        super(file);
        this.file=file;
    }
    @Override
    public void handle(Event event) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BasicTextEditor.fxml"));
            root = loader.load();
            TextEditorController window = loader.getController();
            window.setFile(file);
            Scene scene = new Scene(root, 300, 150);
            scene.getStylesheets().add(getClass().getResource("/CSS/TextEditor.css").toExternalForm());
            Stage textEditor = new Stage();
            textEditor.initStyle(StageStyle.UTILITY);
            textEditor.setTitle("Text editor");
            textEditor.setAlwaysOnTop(true);
            textEditor.initModality(Modality.APPLICATION_MODAL);
            textEditor.setScene(scene);
            textEditor.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

