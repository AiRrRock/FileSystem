package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.high_level_items.SystemBuffer;
import javafx.event.Event;
import javafx.event.EventHandler;

public class FileCopyEventHandler extends FileEventHandler implements EventHandler {
    File fileToCopy;

    public FileCopyEventHandler(File fileToCopy){
        this.fileToCopy=fileToCopy;
    }

    @Override
    public void handle(Event event) {
        SystemBuffer buffer = FileSystem.getSelfReference().getSystemBuffer();
        buffer.setItemToCopy(fileToCopy);
    }
}
