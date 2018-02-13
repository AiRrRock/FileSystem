package is.this_way.better.filesystem.model.high_level_items;


import java.util.Observable;

public class SystemInfo extends Observable {
    private int sectors;
    private int sectorSize;
    private long fileStructurePos;
    private int offset;

    private SystemInfo(SystemInfoBuilder builder){
        this.sectors=builder.sectors;
        this.sectorSize=builder.sectorSize;
        this.fileStructurePos =builder.fileStructurePos;
        this.offset=builder.offset;
    }
    public long getSize(){
        return sectors*sectorSize+offset;
    }
    public int getSectors() {
        return sectors;
    }
    public int getSectorSize() {
        return sectorSize;
    }
    public long getFileStructurePos() {
        return fileStructurePos;
    }
    public int getOffset() {
        return offset;
    }
    public void addSectors(int numberToAdd){
        sectors+=numberToAdd;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sectors+"\n");
        sb.append(sectorSize+"\n");
        sb.append(offset+"\n");
        return sb.toString();
    }

    public static class SystemInfoBuilder {
        private int sectors;
        private int sectorSize;
        private long fileStructurePos;
        private int offset;
        public SystemInfoBuilder(int sectors, int sectorSize){
            this.sectors=sectors;
            this.sectorSize= sectorSize;
        }
        public SystemInfoBuilder setOffset(int offset) {
            this.offset = offset;
            return this;
        }
        public SystemInfoBuilder setFileStructurePos(long fileStructurePos) {
            this.fileStructurePos = fileStructurePos;
            return this;
        }
        public SystemInfo build(){return new SystemInfo(this);}
    }
}
