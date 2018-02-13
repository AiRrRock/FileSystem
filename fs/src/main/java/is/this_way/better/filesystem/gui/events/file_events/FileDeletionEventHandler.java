package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.gui.tasks.FileDeletionTask;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;


public class FileDeletionEventHandler implements EventHandler{
    private FolderTreeItem folder;
    private FileTreeItem fileToDelete;
    public FileDeletionEventHandler(FolderTreeItem folder, FileTreeItem fileToDelete){
        this.folder=folder;
        this.fileToDelete=fileToDelete;
    }
    @Override
    public void handle(Event event) {
        FileDeletionTask fileDeletionTask = new FileDeletionTask(fileToDelete);
        fileDeletionTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                folder.getCorrespondingFolder().remove(fileToDelete.getCorrespondingFile());
                folder.getChildren().remove(fileToDelete);
            }
        });
        new Thread(fileDeletionTask).start();
    }
}
