package is.this_way.better.filesystem.gui.events.folder_events;

import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.gui.tasks.FolderDeletionTask;
import is.this_way.better.filesystem.model.high_level_items.ProcessList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class FolderDeletionEventHandler implements EventHandler {
    private FolderTreeItem folderToDelete;
    public FolderDeletionEventHandler(FolderTreeItem folderToDelete){
        this.folderToDelete=folderToDelete;
    }

    @Override
    public void handle(Event event) {
        FolderTreeItem parent = (FolderTreeItem) folderToDelete.getParent();
        FolderDeletionTask fileDeletionTask = new FolderDeletionTask(folderToDelete);
        ProcessList processList = ProcessList.getSelfRepresentation();
        processList.addProcess("Deleting folder");
        fileDeletionTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                parent.getCorrespondingFolder().remove(folderToDelete.getCorrespondingFolder());
                parent.getChildren().remove(folderToDelete);
                processList.removeProcess("Deleting folder");
            }
        });
        new Thread(fileDeletionTask).start();
    }

}
