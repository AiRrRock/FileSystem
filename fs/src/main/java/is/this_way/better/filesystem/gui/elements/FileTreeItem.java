package is.this_way.better.filesystem.gui.elements;

import is.this_way.better.filesystem.gui.events.file_events.FileCopyEventHandler;
import is.this_way.better.filesystem.gui.events.file_events.FileDeletionEventHandler;
import is.this_way.better.filesystem.gui.events.file_events.FileToFSEventHandler;
import is.this_way.better.filesystem.gui.events.file_events.OpenFileEventHandler;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FileTreeItem extends AbstractTreeItem implements NamedTreeItem{
    private File correspondingFile;
    private FolderTreeItem parent;
    private Node fileIcon;

    public FileTreeItem(File file, FolderTreeItem parent) {
      this.parent=parent;
        this.correspondingFile = file;
        this.setValue(file.getName());
        createFileIcon();
        this.setGraphic(fileIcon);

    }

    public void createFileIcon() {
        this.fileIcon = new FileImageView(this);
    }
    public File getCorrespondingFile() {
        return correspondingFile;
    }

    @Override
    public ContextMenu getMenu() {

        ContextMenu fileMenu = new ContextMenu();

        MenuItem openFile   = new MenuItem("Open file");
        MenuItem copyFile   = new MenuItem("Copy file");

        MenuItem renameFile = new MenuItem("Rename file");
        MenuItem deleteFile = new MenuItem("Delete file");
        MenuItem moveToFS   = new MenuItem("Move to file system");

        openFile.setOnAction(new OpenFileEventHandler(correspondingFile));
        deleteFile.setOnAction(new FileDeletionEventHandler(parent,this));
        copyFile.setOnAction(new FileCopyEventHandler(correspondingFile));
        moveToFS.setOnAction(new FileToFSEventHandler(this));

        fileMenu.getItems().add(openFile);

        fileMenu.getItems().add(deleteFile);
        fileMenu.getItems().add(moveToFS);
        //fileMenu.getItems().add(renameFile);
        //fileMenu.getItems().add(copyFile);

        return fileMenu;
    }

    @Override
    public String getName() {
        return (String) this.getValue();
    }

    @Override
    public void setName(String name) {
        this.setValue(name);
    }
}
