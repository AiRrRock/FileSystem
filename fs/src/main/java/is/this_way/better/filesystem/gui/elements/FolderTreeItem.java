package is.this_way.better.filesystem.gui.elements;

import is.this_way.better.filesystem.gui.events.file_events.FileCreationEventHandler;
import is.this_way.better.filesystem.gui.events.folder_events.FolderCreationEventHandler;
import is.this_way.better.filesystem.gui.events.file_events.FileAdditionEventHandler;
import is.this_way.better.filesystem.gui.events.folder_events.FolderDeletionEventHandler;
import is.this_way.better.filesystem.gui.events.PasteItemEventHandler;
import is.this_way.better.filesystem.gui.events.RenameFolderEventHandler;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import javafx.scene.Node;
import javafx.scene.control.*;

public class FolderTreeItem extends AbstractTreeItem implements NamedTreeItem {
    private AbstractFolder correspondingFolder;
    private Node itemView;

    public FolderTreeItem(AbstractFolder folder) {
        this.correspondingFolder=folder;
        this.setValue(folder.getName());

        createItemView();
        this.setGraphic(itemView);
    }
    private void createItemView(){
        itemView = new FolderImageView(this);
    }
    public AbstractFolder getCorrespondingFolder() {
        return correspondingFolder;
    }
    public ContextMenu getMenu(){

        ContextMenu menu = new ContextMenu();
        MenuItem createFolder  = new MenuItem("Create folder");
        MenuItem createFile    = new MenuItem("Create file");
        MenuItem copyFolder    = new MenuItem("Copy folder");
        MenuItem addFromFS     = new MenuItem("Add from file system");
        MenuItem deleteFolder  = new MenuItem("Delete folder");
        MenuItem renameFolder  = new MenuItem("Rename folder");
        MenuItem paste         = new MenuItem("Paste");

        createFolder.setOnAction(new FolderCreationEventHandler(this));
        createFile.setOnAction(new FileCreationEventHandler(this));
        renameFolder.setOnAction(new RenameFolderEventHandler(this));
        addFromFS.setOnAction(new FileAdditionEventHandler(this));
        paste.setOnAction(new PasteItemEventHandler(this));
        deleteFolder.setOnAction(new FolderDeletionEventHandler(this));

        menu.getItems().add(createFolder);
        menu.getItems().add(createFile);
        menu.getItems().add(addFromFS);
        if (correspondingFolder instanceof Folder){menu.getItems().add(deleteFolder);}
       // menu.getItems().add(renameFolder);
       //  menu.getItems().add(copyFolder);
       // menu.getItems().add(paste);

        return menu;
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
