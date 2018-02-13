package is.this_way.better.filesystem.gui.tasks;

import is.this_way.better.filesystem.io.serialization.FileSystemDeserializer;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import javafx.concurrent.Task;

public class FileSystemDeserializationTask extends Task {
    private FileSystemDeserializer fileSystemDeserializer;
    public FileSystemDeserializationTask(FileSystemDeserializer fileSystemDeserializer){
        this.fileSystemDeserializer=fileSystemDeserializer;
    }
    @Override
    protected FileSystem call() throws Exception {
        fileSystemDeserializer.deserialize();
        FileSystem fileSystem = fileSystemDeserializer.getFileSystem();
        return fileSystem;
    }
}
