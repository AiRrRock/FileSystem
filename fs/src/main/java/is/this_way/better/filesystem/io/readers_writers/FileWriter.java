package is.this_way.better.filesystem.io.readers_writers;

import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class FileWriter {
    private static int sectorSize;

    public FileWriter(){
    }
    public static void setSectorSize(int sectorSize) {
        FileWriter.sectorSize = sectorSize;
    }

    public void writeFile(TreeSet<Sector> sectors) {
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        for (Sector sector: sectors){
            try {
                pio.writeSector(sector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Also tidious, (I know that I don't have to worry about memory management) but feels right
        sectors=null;
    }
    //TODO Make a vay to copy files
    public void copyFile(long startingPos, ArrayList<Long> newSectors){
        for (int i=0; i<newSectors.size();i++){
            startingPos=copySector(startingPos,newSectors.get(i), i<=newSectors.size()-2 ? newSectors.get(i+1):0);
        }
    }

    public void deleteFile(TreeSet<Long> sectors){
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        for (long sector:sectors){
            try {
                pio.deleteSector(sector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void deleteSectors(TreeSet<Sector> sectors){
        deleteFile(DataConverter.getSectorPositions(sectors));
    }

    private long copySector(long from, long to, long nextTo){
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        long nextSector = 0;
        try {
            Sector sectorToCopy = pio.readSector(from);
            sectorToCopy.setStartingPos(to);
            nextSector=sectorToCopy.getNextSector();
            sectorToCopy.setNextSector(nextTo);
            pio.writeSector(sectorToCopy);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return nextSector;
    }

}
