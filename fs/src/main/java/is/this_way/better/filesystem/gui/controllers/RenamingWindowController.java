package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.NamedTreeItem;
import is.this_way.better.filesystem.io.validators.FileNameValidator;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.io.validators.NameValidator;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RenamingWindowController implements ClosableWindow, ErrorInvokingWindow {
    private AbstractFolder folder;
    private NamedTreeItem itemToRename;
    @FXML
    private Label creationLabel;
    @FXML
    private TextField objectName;

    public void rename(ActionEvent actionEvent, NameValidator validator) {
      //  System.out.println(itemToRename);
        String futureName = objectName.getText().isEmpty() ? itemToRename.getName() : objectName.getText() ;
        if (validator.validateName(futureName, folder)) {
            itemToRename.setName(futureName);
            closeWindow(actionEvent);
        } else {
            ErrorInvokingWindow.super.showErrorWindow("The " + (validator instanceof FileNameValidator ? "file" : "folder")+" under this name exists");
        }
    }
    private void innerRename(Folder folder, String name){
        folder.setName(name);
    }
    public void setFolder(AbstractFolder folder) {
        this.folder = folder;
    }
    public void setItemToRename(NamedTreeItem itemToRename) {
        this.itemToRename = itemToRename;
        System.out.println("|HERE is the item" + itemToRename);}
    public void setCreationLabel(String labelText) {
        creationLabel.setText(labelText);
    }
    @Override
    public void closeWindow(ActionEvent actionEvent) {
        ClosableWindow.super.closeWindow(actionEvent);
    }
}


