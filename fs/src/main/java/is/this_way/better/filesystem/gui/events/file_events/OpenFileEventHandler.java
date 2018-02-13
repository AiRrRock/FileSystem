package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.model.low_level_items.files.File;
import javafx.event.Event;
import javafx.event.EventHandler;

public class OpenFileEventHandler extends FileEventHandler implements EventHandler {
    private File file;
    private FileEventHandler eventHandler;


    public OpenFileEventHandler(File file){
        this.file = file;
    }
    @Override
    public void handle(Event event) {
        switch (file.getFileType()){
            case PICTURE:
                eventHandler = new OpenImageFileEventHandler(file);
                break;
            case VIDEO: break;
            case AUDIO:
                eventHandler = new OpenAudioFileEventHandler(file);
                break;
            case TEXT:
                eventHandler = new OpenTextFileEventHandler(file);
                break;
            case UNKNOWN:
                eventHandler = new OpenUnsupportedFileEventHandler(file);
        }
        eventHandler.handle(event);
    }

}
