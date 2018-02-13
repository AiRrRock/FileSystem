package is.this_way.better.filesystem.gui.tasks;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import javafx.concurrent.Task;

import java.util.TreeSet;

public class FolderDeletionTask extends Task {
    private FolderTreeItem folderToDelete;
    private FileWriter fileWriter;

    public FolderDeletionTask(FolderTreeItem folderToDelete){
        this.folderToDelete=folderToDelete;
    }
    @Override
    protected FolderTreeItem call() throws Exception {
        deleteFolder(folderToDelete.getCorrespondingFolder());
        return folderToDelete;
    }
    private void deleteFolder(AbstractFolder folder){
        for (File fileToDelete: folder.getInnerFiles()){
                deleteFile(fileToDelete);
                folder.remove(fileToDelete);
        }
        for (Folder subFolderToDelete : folder.getSubFolders()){
            deleteFolder(subFolderToDelete);
            folder.remove(subFolderToDelete);
        }

    }
    private void deleteFile(File fileToDelete){
        TreeSet<Long> sectors =
                (FileReader.readSectorPosesSet(fileToDelete.getStartingPos()));
        fileWriter.deleteFile(sectors);
    }
}
