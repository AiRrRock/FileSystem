package is.this_way.better.filesystem.gui.events.folder_events;

import is.this_way.better.filesystem.gui.controllers.FolderCreationWindowController;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FolderCreationEventHandler implements EventHandler {
    private FolderTreeItem folder;

    public FolderCreationEventHandler(FolderTreeItem folder) {
        this.folder = folder;
    }

    @Override
    public void handle(Event event) {
        Parent root = null;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FolderCreationWindow.fxml"));
            root = loader.load();

            FolderCreationWindowController window = loader.getController();
            window.setFolder(folder);
            window.setCreationLabel("Folder name");

            // The window creation itself
            Scene scene = new Scene(root, 340, 110);
            scene.getStylesheets().add(getClass().getResource("/CSS/CreationWindowTheme.css").toExternalForm());

            Stage fileCreationPopup = new Stage();
            fileCreationPopup.initModality(Modality.APPLICATION_MODAL);
            fileCreationPopup.initStyle(StageStyle.UTILITY);
            fileCreationPopup.setTitle("folders Creation");
            fileCreationPopup.setScene(scene);
            fileCreationPopup.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
