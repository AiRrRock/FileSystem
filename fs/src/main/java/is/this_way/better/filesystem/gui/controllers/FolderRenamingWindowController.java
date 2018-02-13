package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.NamedTreeItem;
import is.this_way.better.filesystem.io.validators.FolderNameValidator;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FolderRenamingWindowController extends RenamingWindowController implements ClosableWindow {
    @FXML
    private Label creationLabel;
    @FXML
    private TextField objectName;

    @FXML
    public void changeName(ActionEvent actionEvent) {
       super.rename(actionEvent, new FolderNameValidator());
    }

    public void setFolder(AbstractFolder folder) {
       super.setFolder(folder);
    }
    public void setItemToRename(NamedTreeItem itemToRename) {
        super.setItemToRename(itemToRename);
    }

}
