package is.this_way.better.filesystem.gui.events.file_events;

import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.model.high_level_items.ProcessList;
import is.this_way.better.filesystem.gui.tasks.FileAdditionTask;
import is.this_way.better.filesystem.io.validators.FileNameValidator;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileAdditionEventHandler implements EventHandler {
    private FolderTreeItem folder;

    public FileAdditionEventHandler(FolderTreeItem folder) {
        this.folder = folder;
    }

    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        Window window = folder.getMenu().getScene().getWindow();
        java.io.File fileToAdd = fileChooser.showOpenDialog(window);
        FileNameValidator validator = new FileNameValidator();

        if  (validator.validateName(fileToAdd.getName(), folder.getCorrespondingFolder())) {
            FileAdditionTask fileAdditionTask = new FileAdditionTask(fileToAdd, folder);
            final FileTreeItem[] fileTreeItem = new FileTreeItem[1];
            ProcessList processList = ProcessList.getSelfRepresentation();
            processList.addProcess("Addition of file");
            fileAdditionTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    fileTreeItem[0] = (FileTreeItem) fileAdditionTask.getValue();
                    folder.getChildren().add(fileTreeItem[0]);
                    processList.removeProcess("Addition of file");
                }
            });
            new Thread(fileAdditionTask).start();
        } else {

        }
    }
}
