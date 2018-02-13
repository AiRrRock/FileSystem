package is.this_way.better.filesystem.io.serialization;

import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.high_level_items.IndexTable;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;


import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

public class IndexTableDeserializer extends Observable {
    private SystemInfo systemInfo;
    private static IndexTable indexTable;

    public IndexTableDeserializer(SystemInfo systemInfo, Observer observer) {
        this.addObserver(observer);
        this.systemInfo = systemInfo;
    }

    public  IndexTable deserialize(){

        indexTable = initializeIndexTable();
        addSectors(indexTable,systemInfo.getSectors(),systemInfo.getSectorSize(),systemInfo.getOffset());
        slowIndexTableDeserialization();
        return indexTable;
    }
    public IndexTable deserialize(RootFolder rootFolder){
        setChanged();
        notifyObservers();
        indexTable = initializeIndexTable();
        addSectors(indexTable,systemInfo.getSectors(),systemInfo.getSectorSize(),systemInfo.getOffset());
        structureSectorsDeserialization(rootFolder,systemInfo.getFileStructurePos());
        return indexTable;
    }
    private  IndexTable initializeIndexTable(){
        return new IndexTable();
    }
    private  void addSectors(IndexTable indexTable, int sectors,int sectorSize,long firstSectorPos){
        long sectorPos = firstSectorPos;
        for (int i=0;i<sectors;i++){
            indexTable.addSector(sectorPos);
            sectorPos+=sectorSize;
        }
        indexTable.setLastSector(sectorPos);
    }

    private  void slowIndexTableDeserialization() {
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        for (Map.Entry<Long, Boolean> entry : indexTable.getSectorData().entrySet()) {
            try {
                entry.setValue(pio.isEmpty(entry.getKey()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void structureSectorsDeserialization(RootFolder rootFolder, long structuralPoses){
        TreeSet<Long> structuralItemsSectors = FileReader.readSectorPosesSet(structuralPoses);
        for (Long sector: structuralItemsSectors){
            indexTable.setSectorEmptiness(sector,false);
        }
        calculateForFolder(rootFolder);
    }

    private  void calculateForFolder(AbstractFolder folder){
        for (File innerFile: folder.getInnerFiles()){
            calculateForFile(innerFile);
        }
        for (Folder subFolder:folder.getSubFolders()){
            calculateForFolder(subFolder);
        }
    }
    private  void calculateForFile(File file) {
        long startingPos = file.getStartingPos();
        TreeSet<Long> fileSectors = FileReader.readSectorPosesSet(startingPos);
        for (Long sector : fileSectors){
            indexTable.setSectorEmptiness(sector,false);
        }
    }

}
