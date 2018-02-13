package is.this_way.better.filesystem.model.high_level_items;

import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

public class FileSystem extends Observable implements Observer {
    private SystemInfo systemInfo;
    private IndexTable indexTable;
    private RootFolder rootFolder;
    private SystemBuffer systemBuffer;
    private static FileSystem selfReference;
    private final boolean dynamic;

    private FileSystem(FileSystemBuilder builder) {
        this.systemInfo    = builder.systemInfo;
        this.indexTable    = builder.indexTable;
        this.rootFolder    = builder.rootFolder;
        this.systemBuffer  = new SystemBuffer();
        this.dynamic       = builder.dynamic;
        this.selfReference = this;
    }

    public IndexTable getIndexTable() {
        return indexTable;
    }
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }
    public RootFolder getRootFolder() {
        return rootFolder;
    }
    public static FileSystem getSelfReference() {
        return selfReference;
    }
    public boolean isDynamic(){
        return dynamic;
    }
    public void addSectors(int numberOfSectors){
        systemInfo.addSectors(numberOfSectors);
        indexTable.appendSectors(numberOfSectors,systemInfo.getSectorSize());
    }
    public void freeSectors(TreeSet<Sector> sectorsToFree){
        for (Sector sector: sectorsToFree){
            indexTable.setSectorEmptiness(sector.getStartingPos(), true);
        }
    }
    @Override
    public void update(Observable observable, Object o) {

    }
    public SystemBuffer getSystemBuffer() {
        return systemBuffer;
    }
    public static class FileSystemBuilder {
        private SystemInfo systemInfo;
        private IndexTable indexTable;
        private RootFolder rootFolder;
        private SystemBuffer systemBuffer;
        private boolean dynamic;

        public FileSystemBuilder(boolean dynamic) {
            this.dynamic=dynamic;
        }
        public FileSystemBuilder setSystemInfo(SystemInfo systemInfo) {
            this.systemInfo = systemInfo;
            return this;
        }
        public FileSystemBuilder setIndexTable(IndexTable indexTable) {
            this.indexTable = indexTable;
            return this;
        }
        public FileSystemBuilder setRootFolder(RootFolder rootFolder) {
            this.rootFolder = rootFolder;
            return this;
        }
        public FileSystem build() {
            return new FileSystem(this);
        }
    }
}
