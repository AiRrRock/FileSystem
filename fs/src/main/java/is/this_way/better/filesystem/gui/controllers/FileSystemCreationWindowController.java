package is.this_way.better.filesystem.gui.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.readers_writers.FileSystemFormatter;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.io.readers_writers.SystemInfoWriter;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.high_level_items.IndexTable;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.TreeSet;
import static is.this_way.better.filesystem.io.lowlevel.ConstantsIO.*;


public class FileSystemCreationWindowController implements ClosableWindow{
    private Stage primaryStage;
    private String innerFilePath;
    @FXML
    private TextField filePath;
    @FXML
    private TextField sectorSize;
    @FXML
    private TextField numberOfSectors;
    @FXML
    private Button createFS;

    @FXML
    public void createFileSystem(ActionEvent event){
        IndexTable ind = new IndexTable();

        int sectorSize = BYTES_IN_KB*Integer.parseInt(this.sectorSize.getText());
        int sectors = Integer.parseInt(this.numberOfSectors.getText());
        long fileSystemStructPos=40;
        int offset=40;

        ThreadSafeIO.init(innerFilePath,sectorSize);
        ThreadSafeIO threadSafeIO = ThreadSafeIO.getInstance();
        SystemInfo fs= new SystemInfo.SystemInfoBuilder(sectors,sectorSize)
                .setFileStructurePos(fileSystemStructPos).setOffset(offset)
                .build();
        new SystemInfoWriter().write(fs);
        FileSystemFormatter.FormatFS(sectors,sectorSize, offset );

        RootFolder rootFolder = new RootFolder();
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(rootFolder);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        TreeSet<Long> sectorsTree = new TreeSet<>();
        sectorsTree.add((long)40);

       // IndexTable indexTable = IndexTableDeserializer.deserialize(fs);


        FileSystem fileSystem = new FileSystem
                .FileSystemBuilder(true)
                //.setIndexTable(indexTable)
                .setSystemInfo(fs)
                .setRootFolder(rootFolder)
                .build();

        FileWriter fr = new FileWriter();
        fr.writeFile(DataConverter.stringToSectorsSet(json,sectorSize, sectorsTree));

        try {
            threadSafeIO.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow(event);

    }

    public void chooseFile(ActionEvent event) {
        FileChooser systemFileChooser = new FileChooser();
        innerFilePath = systemFileChooser.showSaveDialog(primaryStage).toString();
        filePath.setText(innerFilePath);
        createFS.setDisable(false);
    }

    public void cancelCreation(ActionEvent event) {
        closeWindow(event);
    }
}
