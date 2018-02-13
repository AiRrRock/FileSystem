package is.this_way.better.filesystem.io.serialization;

import is.this_way.better.filesystem.io.readers_writers.AutomatedFileStructureWriter;
import is.this_way.better.filesystem.io.readers_writers.AutomatedSystemInfoWriter;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.io.readers_writers.SystemInfoReader;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.high_level_items.IndexTable;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FileSystemDeserializer extends Observable {
    private Observer observer;
    private String filePath;
    private FileSystem fileSystem;

    public FileSystemDeserializer(String filePath){
        this.filePath=filePath;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
        this.addObserver(observer);
    }

    //Here we deserialize FileSystem and Register
    public void deserialize(){
        ArrayList<String> systemInfo = SystemInfoReader.read(filePath);
        SystemInfoDeserializer systemInfoDeserializer = new SystemInfoDeserializer(systemInfo, observer);
        SystemInfo sysInfo    =  systemInfoDeserializer.deserialize();
        ThreadSafeIO.init(filePath,sysInfo.getSectorSize());
        AutomatedSystemInfoWriter systemInfoWriter = new AutomatedSystemInfoWriter();
        sysInfo.addObserver(systemInfoWriter);

        FileSystemStructureDeserializer fileSystemStructureDeserializer = new FileSystemStructureDeserializer(sysInfo.getFileStructurePos(),observer);
        fileSystemStructureDeserializer.addObserver(observer);
        RootFolder rootFolder =  fileSystemStructureDeserializer.deserialize();

        IndexTableDeserializer indexTableDeserializer = new IndexTableDeserializer(sysInfo,observer);
        IndexTable indexTable =  indexTableDeserializer.deserialize(rootFolder);

        FileWriter.setSectorSize(sysInfo.getSectorSize());
        AutomatedFileStructureWriter automatedFileStructureWriter = new AutomatedFileStructureWriter();
        rootFolder.addObserver(automatedFileStructureWriter);

        FileSystem fileSystem = new FileSystem
                .FileSystemBuilder(true)
                .setSystemInfo(sysInfo)
                .setIndexTable(indexTable)
                .setRootFolder(rootFolder)
                .build();

        this.fileSystem = fileSystem;
        setChanged();
        notifyObservers();

    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
