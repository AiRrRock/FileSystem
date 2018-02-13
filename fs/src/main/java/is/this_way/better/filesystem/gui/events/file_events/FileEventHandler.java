package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.event.Event;
import javafx.event.EventHandler;

public abstract class FileEventHandler implements EventHandler{
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void handle(Event event) {}
}
