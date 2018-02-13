package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.io.validators.IntegrityChecker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

import java.io.IOException;

public class InitialWindowController implements ClosableWindow, ErrorInvokingWindow{
    private Stage primaryStage;
    private Parent root;

    @FXML
    protected void chooseFileSystem(ActionEvent event) {
        FileChooser systemFileChooser = new FileChooser();
        String fileSystemFile = systemFileChooser.showOpenDialog(primaryStage).toString();
        if (IntegrityChecker.checkIntegrity(fileSystemFile)) {
            openDeserializationWindow(fileSystemFile);
            closeWindow(event);
        } else {
            ErrorInvokingWindow.super.showErrorWindow("The file is of a wrong format or corrupted");
        }
    }

    @FXML
    protected void createFileSystem(ActionEvent event){
        openCreationWindow();
    }


    private void openDeserializationWindow(String fileSystemFile){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FileSystemDeserializationWindow.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileSystemDeserializationWindowController window = loader.getController();
        window.init(fileSystemFile);
        Stage stage = new Stage();
        stage.setTitle("Deserialization Progress");
        Scene fileSystemDeserializationScene = new Scene(root, 400, 150);
        fileSystemDeserializationScene.getStylesheets().add(getClass().getResource("/CSS/ErrorWindow.css").toExternalForm());
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(fileSystemDeserializationScene);
        stage.show();
    }
    private void openCreationWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FileSystemCreationWindow.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileSystemCreationWindowController window = loader.getController();
        Scene scene = new Scene(root, 460, 120);
        scene.getStylesheets().add(getClass().getResource("/CSS/BasicTheme.css").toExternalForm());

        Stage errorWindow = new Stage();
        errorWindow.initStyle(StageStyle.UNDECORATED);
        errorWindow.setTitle("File System Creation");
        errorWindow.initModality(Modality.APPLICATION_MODAL);
        errorWindow.setScene(scene);
        errorWindow.show();
    }

    @Override
    public void closeWindow(ActionEvent actionEvent) {
        ClosableWindow.super.closeWindow(actionEvent);
    }
}
