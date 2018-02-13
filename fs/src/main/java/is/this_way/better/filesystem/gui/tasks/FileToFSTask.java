package is.this_way.better.filesystem.gui.tasks;

import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import javafx.concurrent.Task;


import java.io.RandomAccessFile;
import java.util.TreeSet;

public class FileToFSTask extends Task {
    private static int MAXIMUM_SMALL_FILE_SIZE = 100000;
    private File fileToCopy;
    private String fileToSaveTo;
    public FileToFSTask(File fileToCopy, String fileToSaveTo){
        this.fileToCopy=fileToCopy;
        this.fileToSaveTo=fileToSaveTo;
    }
    @Override
    protected Boolean call() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileToSaveTo,"rw");
        if (fileToCopy.getSizeInSectors()<MAXIMUM_SMALL_FILE_SIZE){
            smallFileCopy(randomAccessFile);
        } else {
            largeFileCopy(randomAccessFile);
        }
        randomAccessFile.close();
        return true;
    }
    private boolean smallFileCopy(RandomAccessFile randomAccessFile) throws Exception{
        long startingPos = fileToCopy.getStartingPos();
        randomAccessFile.seek(0);
        TreeSet<Sector> sectorSet = new TreeSet<>();
        while (startingPos !=0) {
            Sector sector = FileReader.readSector(startingPos);
            sectorSet.add(sector);
            startingPos=sector.getNextSector();
        }
        randomAccessFile.write(DataConverter.sectorsToByteArray(sectorSet));
        return true;
    }
    private boolean largeFileCopy(RandomAccessFile randomAccessFile) throws Exception{
        long startingPos = fileToCopy.getStartingPos();
        randomAccessFile.seek(0);
        while (startingPos !=0){
            Sector sector = FileReader.readSector(startingPos);
            randomAccessFile.write(DataConverter.sectorToByteArray(sector));
            startingPos= sector.getNextSector();
        }
        return true;
    }
}
