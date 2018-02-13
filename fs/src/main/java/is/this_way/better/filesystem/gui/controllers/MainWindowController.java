package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.model.high_level_items.ProcessList;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.gui.elements.CustomTreeCell;
import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import java.util.Observable;
import java.util.Observer;

public class MainWindowController implements Observer, ErrorInvokingWindow{
    private ProcessList unfinishedProcesses;
    private FileSystem fileSystem;
    private int doomButtonCounter=0;
    @FXML
    private ProgressIndicator unfinishedProcessIndicator;
    @FXML
    private Button turnOffButton;
    @FXML
    private Button doomButton;
    @FXML
    private TreeView hierarchy;

    public void init(FileSystem fileSystem){
        hierarchy.setRoot(prepareRecursive(fileSystem.getRootFolder()));
        hierarchy.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                CustomTreeCell cell = new CustomTreeCell();
                return cell;
            }
        });
        unfinishedProcesses = new ProcessList(this);
        unfinishedProcessIndicator.setVisible(false);
    }

    private FolderTreeItem prepareRecursive(AbstractFolder folder){
        FolderTreeItem folderItem = new FolderTreeItem(folder);
        for (File innerFile : folder.getInnerFiles()) {
            FileTreeItem fileItem = new FileTreeItem(innerFile,folderItem);
            folderItem.getChildren().add(fileItem);
        }
        for (AbstractFolder subFolder: folder.getSubFolders()){
            FolderTreeItem innerFolder = prepareRecursive(subFolder);
            folderItem.getChildren().add(innerFolder);
        }
        return folderItem;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (unfinishedProcesses.isEmpty()) {
            unfinishedProcessIndicator.setVisible(false);
        } else {
            unfinishedProcessIndicator.setVisible(true);
        }
    }

    @FXML
    protected void doomButtonPressed(ActionEvent actionEvent){
        switch (doomButtonCounter){
            case 0:
                doomButton.setText("Don't do it");
                break;
            case 1:
                doomButton.setText("No more !!!");
                break;
            case 2:
                doomButton.setText("STOP. There is no cow level");
                ErrorInvokingWindow.super.showErrorWindow("Press better once more and smthing bad will happen ");
                break;
            case 3:
                ErrorInvokingWindow.super.showErrorWindow("Maybe next time you listen. Goodbye");
                Platform.exit();
                break;
        }
        doomButtonCounter++;
    }

    @FXML
    protected void closeFileSystem(ActionEvent actionEvent) {
        if (unfinishedProcesses.isEmpty()) {
            Platform.exit();
        } else {

        }
    }

 }
