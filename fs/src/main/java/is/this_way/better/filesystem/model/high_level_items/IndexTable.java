package is.this_way.better.filesystem.model.high_level_items;

import java.util.*;
import java.util.stream.Collectors;

public class IndexTable {
    private TreeMap<Long,Boolean> sectorData;
    private int numberOfEmptySectors;
    private long lastSector;
    public IndexTable(){
        sectorData =new TreeMap<>();
    }

    public TreeMap<Long, Boolean> getSectorData() {
        return sectorData;
    }
    public synchronized ArrayList<Long> getEmptySectors(long neededSectors){
        ArrayList<Long> freeSectors = sectorData
                .entrySet()
                .stream()
                .parallel()
                .filter(sector -> sector.getValue())
                .map(sector -> new Long(sector.getKey())).limit(neededSectors)
                .collect(Collectors.toCollection(ArrayList::new));
        for (Long sector: freeSectors){
            sectorData.replace(sector,false);
            numberOfEmptySectors --;
        }
        return freeSectors;
    }

    public int getNumberOfEmptySectors(){
        recalculateEmptySectors();
        return  numberOfEmptySectors;
    }
    private void recalculateEmptySectors(){
        this.numberOfEmptySectors=(int) sectorData.entrySet().stream().filter(sector -> sector.getValue()).count();
    }
    public long getLastSector(){
        return Collections.max(sectorData.keySet());
    }

    public void setSectorEmptiness(long sectorPos, boolean value){
        sectorData.replace(sectorPos,value);
    }
    public synchronized void appendSectors (int numberToAdd, int sectorSize){
        for (int i=0; i<numberToAdd;i++){
            lastSector = lastSector+sectorSize;
            addSector(lastSector);
        }
    }
    public void addSector(long startingPos){
        sectorData.put(startingPos,true);
        numberOfEmptySectors++;
    }

    public void setLastSector(long lastSector) {
        this.lastSector = lastSector;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long,Boolean> entry: sectorData.entrySet()){
            sb.append(entry.getKey()+"   "+entry.getValue()+"\n");
        }
        sb.append("____________________________________________\n");
        return sb.toString();
    }
}

