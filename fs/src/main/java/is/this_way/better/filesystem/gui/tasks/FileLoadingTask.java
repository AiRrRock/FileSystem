package is.this_way.better.filesystem.gui.tasks;

import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.concurrent.Task;

import java.util.TreeSet;

public class FileLoadingTask extends Task {
    private File fileToLoad;
    public FileLoadingTask(File fileToLoad){
        this.fileToLoad=fileToLoad;
    }
    @Override
    protected TreeSet<Long> call() throws Exception {
        FileReader.readSectorPosesSet(fileToLoad.getStartingPos());
        return null;
    }
}
