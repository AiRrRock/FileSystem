package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.tasks.FileToFSTask;
import is.this_way.better.filesystem.model.high_level_items.ProcessList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileToFSEventHandler implements EventHandler {
    private FileTreeItem fileToCopy;

    public FileToFSEventHandler(FileTreeItem fileToCopy){
        this.fileToCopy=fileToCopy;
    }
    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        Window window = fileToCopy.getMenu().getScene().getWindow();
        java.io.File fileToSaveTo = fileChooser.showSaveDialog(window);
        FileToFSTask fileToFSTask = new FileToFSTask(fileToCopy.getCorrespondingFile(), fileToSaveTo.getAbsolutePath());
        ProcessList processList = ProcessList.getSelfRepresentation();
        processList.addProcess("Copying to FS");
        fileToFSTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
             @Override
             public void handle(WorkerStateEvent t) {
                 processList.removeProcess("Copying to FS");
                }
            });
            new Thread(fileToFSTask).start();
    }
}
