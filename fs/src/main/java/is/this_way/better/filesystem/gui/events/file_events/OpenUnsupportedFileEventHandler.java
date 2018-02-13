package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.controllers.ErrorInvokingWindow;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.event.Event;
import javafx.event.EventHandler;

public class OpenUnsupportedFileEventHandler extends FileEventHandler implements EventHandler,ErrorInvokingWindow {
    private File file;

    public OpenUnsupportedFileEventHandler(File file){
        this.file=file;
    }

    @Override
    public void handle(Event event) {
        ErrorInvokingWindow.super.showErrorWindow("The file " + file.getName() + " is of unsupported format");
    }
}

