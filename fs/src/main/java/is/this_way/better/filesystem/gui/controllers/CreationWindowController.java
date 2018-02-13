package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.readers_writers.FileSystemFormatter;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.files.FileTypes;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.io.validators.FileNameValidator;
import is.this_way.better.filesystem.io.validators.NameValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


public abstract class CreationWindowController implements ClosableWindow, ErrorInvokingWindow {
    private FolderTreeItem folder;
    @FXML
    private Label creationLabel;
    @FXML
    private TextField objectName;

    @FXML
    public void create(ActionEvent actionEvent, NameValidator validator) {
        String futureName = objectName.getText().isEmpty() ? "Unnamed" : objectName.getText() ;
        AbstractFolder folderToAddTo = folder.getCorrespondingFolder();
        if (validator.validateName(futureName, folderToAddTo)) {
            folder.getChildren()
                  .add((validator instanceof FileNameValidator) ?
                        new FileTreeItem(createFile(futureName, folderToAddTo),folder) :
                        new FolderTreeItem(createFolder(futureName, folderToAddTo)));
            closeWindow(actionEvent);
        } else {
           ErrorInvokingWindow
                   .super
                   .showErrorWindow("The " + (validator instanceof FileNameValidator ? "file" : "folder")+ " under the name " + futureName+ " already exists");
        }
    }

    public void setFolder(FolderTreeItem folder) {
        this.folder = folder;
    }
    public void setCreationLabel(String labelText) {
        creationLabel.setText(labelText);
    }

    private Folder createFolder(String name, AbstractFolder folder) {
        Folder newFolder = new Folder(name);
        folder.add(newFolder);
        return newFolder;
    }
    private File createFile(String name, AbstractFolder folder) {
        File newFile = new File(name, FileTypes.TEXT);
        FileSystem fileSystem = FileSystem.getSelfReference();
        if (fileSystem.getIndexTable().getNumberOfEmptySectors()>0){

        } else {
            fileSystem.addSectors(1);
            FileSystemFormatter.appendSectors(1);
        }
        long startingPos = fileSystem.getIndexTable().getEmptySectors(1).get(0) ;
        newFile.setStartingPos(startingPos);
        try {
            ThreadSafeIO.getInstance().markFull(startingPos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        folder.add(newFile);
        return newFile;
    }

    @Override
    public void closeWindow(ActionEvent actionEvent) {
        ClosableWindow.super.closeWindow(actionEvent);
    }
}
