package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;

public class InnerFileChooserWindowController implements ClosableWindow  {
    private FolderTreeItem rootFolder;
    @FXML
    TreeView hierarchy;
    @FXML
    Button add;
    public void init(FolderTreeItem rootFolder){
        this.rootFolder=rootFolder;
    }
    private void prepareTree(FolderTreeItem rootFolder){

    }
    @FXML
    public void addSong(ActionEvent actionEvent) {
    }
}
