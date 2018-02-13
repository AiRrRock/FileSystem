package is.this_way.better.filesystem.gui.controllers;

import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.resolvers.FileUpdateResolver;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import java.util.TreeSet;

public class TextEditorController implements ClosableWindow {
    private static int SECTOR_LIMIT= 500;
    private File file;
    private FileWriter fileWriter;
    private long nextSectorToRead;
    private long startingPos;
    private TreeSet<Sector> readPart;
    private long fileSizeInSectors;
    private long currenSectorNumber;
    @FXML
    TextArea textPane;
    @FXML
    MenuItem saveFile;
    @FXML
    MenuItem readNextPart;

    public void setFile(File file) {
        readPart = new TreeSet<Sector>();
        if (file.getSizeInSectors() > SECTOR_LIMIT) {
            saveFile.setDisable(true);
            readNextPart.setDisable(false);
        } else {
            saveFile.setDisable(false);
            readNextPart.setDisable(true);
        }
        this.file = file;
        fileSizeInSectors= file.getSizeInSectors();
        startingPos = this.file.getStartingPos();
        read(startingPos);
        fileWriter = new FileWriter();
        textPane.setText(DataConverter.sectorsToString(readPart));
        currenSectorNumber = readPart.size();
    }

    private void read(long startingPos){
        if (fileSizeInSectors >SECTOR_LIMIT){
            currenSectorNumber += readPart.size();
            readPart = FileReader.partialReadSectors(SECTOR_LIMIT,startingPos);
            nextSectorToRead = readPart.isEmpty() ? startingPos : readPart.last().getNextSector();
        } else {
            readPart = FileReader.readFileData(startingPos).getData();
            nextSectorToRead = 0;
            saveFile.setDisable(false);
            readNextPart.setDisable(true);
        }
        if (currenSectorNumber>=fileSizeInSectors){
            saveFile.setDisable(false);
            readNextPart.setDisable(true);
        }
    }

    @FXML
    public void saveChanges(ActionEvent actionEvent) {
        TreeSet<Sector> newSectors = FileUpdateResolver.partialResolve(textPane.getText(),readPart);
        file.setSizeInSectors(file.getSizeInSectors() - file.getSizeInSectors()+ newSectors.size());
        fileWriter.writeFile(newSectors);

    }
    @FXML
    public void getNextPart(ActionEvent actionEvent){
        TreeSet<Sector> newSectors= FileUpdateResolver.partialResolve(textPane.getText(),readPart);
        fileWriter.writeFile(newSectors);
        file.setSizeInSectors(file.getSizeInSectors() - readPart.size() + newSectors.size());
        if (readPart.last().getNextSector()!=0){
            read(readPart.last().getNextSector());
        } else {
            saveFile.setDisable(false);
            readNextPart.setDisable(true);
        }
        textPane.setText(DataConverter.sectorsToString(readPart));
    }

    @FXML
    @Override
    public void closeWindow(ActionEvent actionEvent) {
         ClosableWindow.super.closeWindow(actionEvent);
         file.freeFileData();
    }

}
