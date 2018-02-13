package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.validators.FileNameValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FileCreationWindowController extends CreationWindowController {
    @FXML
    public void createFile(ActionEvent actionEvent) {
        super.create(actionEvent, new FileNameValidator());

    }
    public void setFolder(FolderTreeItem folder) {
        super.setFolder(folder);
    }
    @Override
    public void closeWindow(ActionEvent actionEvent) {
        super.closeWindow(actionEvent);
    }
}
