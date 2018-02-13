package is.this_way.better.filesystem.gui.events;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.high_level_items.SystemBuffer;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.HashSet;

public class PasteItemEventHandler implements EventHandler {
    private FolderTreeItem folder;

    public PasteItemEventHandler(FolderTreeItem folder){
        this.folder=folder;
    }
    @Override
    public void handle(Event event) {
        SystemBuffer systemBuffer = FileSystem.getSelfReference().getSystemBuffer();
        if (systemBuffer.isEmpty()){

        } else {
            if (systemBuffer.getItemToCopy()instanceof File){
                folder.getChildren().add(pasteFile((File) systemBuffer.getItemToCopy()));
            }else {
                //pasteFolder((Folder) systemBuffer.getItemToCopy());
            }
        }

    }

    private File pasteFile(File fileToCopy){
        return null;
    }

    private Folder pasteFolder(Folder folderToCopy, FolderTreeItem parent){
        HashSet<Folder> subFolders = folderToCopy.getSubFolders();
        HashSet<File> innerFiles=folderToCopy.getInnerFiles();
        Folder newFolder = new Folder(folderToCopy);
        FolderTreeItem visualRepresentation = new FolderTreeItem(folderToCopy);
        parent.getChildren().add(visualRepresentation);
        for (Folder folder: subFolders){

        }
        for (File file:innerFiles){
         //  folderToCopy.getInnerFiles().add();
           visualRepresentation.getChildren().add(pasteFile(file));
        }
        return newFolder;
    }
    private Folder innerPasteFolder(Folder folderToCopy){
        return null;
    }
    private long innerCopyOfNewFile(File fileToCopy){
        FileSystem fileSystem = FileSystem.getSelfReference();
        long fileSectors= fileToCopy.getSizeInSectors();
        long emptySector = fileSystem.getIndexTable().getNumberOfEmptySectors();
        int neededSectors = (int) (emptySector-fileSectors);
        if(neededSectors>0){
            fileSystem.addSectors(neededSectors);
        }
        fileSystem.getIndexTable().getEmptySectors(fileSectors);

return 10;



    }
}
