package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.controllers.ImageWindowController;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.io.converters.DataConverter;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class OpenImageFileEventHandler extends FileEventHandler implements EventHandler {
    private File file;

    public OpenImageFileEventHandler(File file){
        this.file=file;
    }

    @Override
    public void handle(Event event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ImageWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageWindowController window = loader.getController();
        file.setData(FileReader.readFileData(file.getStartingPos()));
        ByteArrayInputStream nittyTrick = new ByteArrayInputStream(
                DataConverter.sectorsToByteArray(
                        (file.getData())));
        Image image = new Image(nittyTrick);
        window.init(image);
        Scene scene = new Scene(root, image.getWidth(), image.getHeight());
        scene.getStylesheets().add(getClass().getResource("/CSS/TextEditor.css").toExternalForm());
        Stage fileCreationPopup = new Stage();
        fileCreationPopup.initStyle(StageStyle.UTILITY);
        fileCreationPopup.setTitle("Image Viewer");
        fileCreationPopup.setScene(scene);
        fileCreationPopup.show();

    }
}
