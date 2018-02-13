package is.this_way.better.filesystem.gui.tasks;

import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import javafx.concurrent.Task;

import java.util.TreeSet;

public class FileDeletionTask extends Task {

    private FileTreeItem fileToDelete;

    public FileDeletionTask(FileTreeItem fileToDelete) {
        this.fileToDelete=fileToDelete;
    }
    @Override
    protected FileTreeItem call() throws Exception {
        FileWriter fileWriter =new FileWriter();
        TreeSet<Long> sectors =
                (FileReader.readSectorPosesSet(fileToDelete.getCorrespondingFile().getStartingPos()));
        fileWriter.deleteFile(sectors);
        return fileToDelete;
    }
}
