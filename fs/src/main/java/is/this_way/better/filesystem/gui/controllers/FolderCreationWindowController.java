package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.validators.FolderNameValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class FolderCreationWindowController extends CreationWindowController implements ClosableWindow {
    @FXML
    private Button actionButton;
    @FXML
    private Label creationLabel;

    public void setFolder(FolderTreeItem folder) {
        super.setFolder(folder);
    }
    @FXML
    public void createFolder(ActionEvent actionEvent) {
        super.create(actionEvent, new FolderNameValidator());
     }
    @Override
    public void closeWindow(ActionEvent actionEvent) {
        super.closeWindow(actionEvent);
    }
}
