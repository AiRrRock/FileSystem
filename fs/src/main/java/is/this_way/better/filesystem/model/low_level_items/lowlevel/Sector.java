package is.this_way.better.filesystem.model.low_level_items.lowlevel;

import is.this_way.better.filesystem.io.converters.DataConverter;

public class Sector implements Comparable{
    private long startingPos;
    private long nextSector;
    private boolean last;
    private byte[] data;

    private Sector(SectorBuilder builder){
        this.startingPos=builder.startingPos;
        this.data=builder.data;
        this.nextSector=builder.nextSector;
        this.last = builder.last;
    }
    public void setStartingPos(long startingPos) {
        this.startingPos = startingPos;
    }
    public void setNextSector(long nextSector) {
        this.nextSector = nextSector;
        if (Long.compare(nextSector,0) == 0) {
            last = true;
        } else {
            last = false;
        }
    }
    public long getNextSector(){
        return nextSector;
    }
    public byte[] getData() {
        return data;
    }
    public long getStartingPos() {
        return startingPos;
    }
    public boolean isLast() { return last;
    }

    @Override
    public String toString() {
        return DataConverter.convertSector(data);
    }

    @Override
    public int compareTo(Object o) {
        return startingPos<((Sector)o).getStartingPos()? -1 : (startingPos>((Sector)o).getStartingPos()? 1: 0);
    }

    public static class SectorBuilder{
        private long startingPos;
        private long nextSector;
        private boolean last;
        private byte[] data;

        public SectorBuilder(long startingPos,byte[] data){
            this.startingPos=startingPos;
            this.data = data;
            }
        public SectorBuilder setNextSector(long nextSector) {
            this.nextSector = nextSector;
            return this;
        }
        public SectorBuilder setLast(boolean last) {
            this.last = last;
            return this;
        }
        public Sector build(){
            return new Sector(this);
        }
    }
}
