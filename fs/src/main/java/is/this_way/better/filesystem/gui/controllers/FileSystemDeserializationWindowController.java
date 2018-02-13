package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.gui.tasks.FileSystemDeserializationTask;
import is.this_way.better.filesystem.io.serialization.FileSystemDeserializer;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class FileSystemDeserializationWindowController implements Observer, ClosableWindow{
    private FileSystem fileSystem;
    private String currentAction;
    private String fileName;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    TextField action;
    @FXML
    Button openFileSystem;
    @FXML
    Button startDeserializationButton;


    public FileSystemDeserializationWindowController(){
    }

    public void init(String fileName){
        this.fileName=fileName;
        action.setText("Press button to load File System");
        progressIndicator.setVisible(false);
        ;
    }

    @FXML
    public void startDeserialization(ActionEvent actionEvent) {
        FileSystemDeserializer fileSystemDeserializer = new FileSystemDeserializer(fileName);
        fileSystemDeserializer.setObserver(this);
        startDeserializationButton.setVisible(false);
        FileSystemDeserializationTask fileSystemDeserializationTask = new FileSystemDeserializationTask(fileSystemDeserializer);
        fileSystemDeserializationTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                fileSystem = (FileSystem) fileSystemDeserializationTask.getValue();
            }
        });
        new Thread(fileSystemDeserializationTask).start();

    }

    @FXML
    private void openMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        MainWindowController window = loader.getController();
        window.init(fileSystem);

        Stage stage = new Stage();
        stage.setTitle("File System");
        Scene fileSystemScene = new Scene(root, 500, 500);
        fileSystemScene.getStylesheets().add(getClass().getResource("/CSS/BasicTheme.css").toExternalForm());
        fileSystemScene.getStylesheets().add(getClass().getResource("/CSS/MainWindowTheme.css").toExternalForm());
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(fileSystemScene);
        stage.show();
    }

    public void openFileSystem(ActionEvent event) {
        try {
            openMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow(event);

    }

    @Override
    public void update(Observable observable, Object o) {
        String className = observable.getClass().getSimpleName();
        progressIndicator.setVisible(false);
        progressIndicator.setVisible(true);
        switch (className){
            case "FileSystemStructureDeserializer":
                currentAction = "Acquiring file system structure";
                break;
            case "IndexTableDeserializer":
                currentAction = "Calculating index table";
                break;
            case "SystemInfoDeserializer":
                currentAction = "Gathering file system information";
                break;
            case "FileSystemDeserializer":
                currentAction = "You can open file system now";
                openFileSystem.setDisable(false);
                ActionEvent completionEvent = new ActionEvent();
                fileSystem = ((FileSystemDeserializer) observable).getFileSystem();
                progressIndicator.setVisible(false);
                openFileSystem.setVisible(true);
                startDeserializationButton.setVisible(false);
                break;
        }
        action.setText(currentAction);

    }

    @Override
    public void closeWindow(ActionEvent actionEvent) {
        ClosableWindow.super.closeWindow(actionEvent);
    }
}
