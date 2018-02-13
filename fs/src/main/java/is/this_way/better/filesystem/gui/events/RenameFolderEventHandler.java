package is.this_way.better.filesystem.gui.events;

import is.this_way.better.filesystem.gui.controllers.FolderRenamingWindowController;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RenameFolderEventHandler implements EventHandler{
    private FolderTreeItem folder;

    public RenameFolderEventHandler(FolderTreeItem folder){
        this.folder=folder;
    }
    @Override
    public void handle(Event event) {
        Parent root = null;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FolderRenamingWindow.fxml"));
            root = loader.load();

            FolderRenamingWindowController window = loader.getController();

            window.setFolder(folder.getCorrespondingFolder().getParent());

            window.setCreationLabel("New name");


            // The window creation itself
            Scene scene = new Scene(root, 320, 110);
            scene.getStylesheets().add(getClass().getResource("/CSS/CreationWindowTheme.css").toExternalForm());

            Stage fileCreationPopup = new Stage();
            fileCreationPopup.initStyle(StageStyle.UTILITY);
            fileCreationPopup.setTitle("files Creation");
            fileCreationPopup.setScene(scene);
            fileCreationPopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
