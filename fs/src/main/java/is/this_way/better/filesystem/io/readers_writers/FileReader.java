package is.this_way.better.filesystem.io.readers_writers;

import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.model.low_level_items.files.FileData;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.io.IOException;
import java.util.TreeSet;

public class FileReader {
    public static FileData readFileData(long startingPos){
        TreeSet<Sector> sectors = readSectors(startingPos);
        FileData data = new FileData(startingPos);
        data.setData(sectors);
        return data;
    }
    private static TreeSet<Sector> readSectors(long firstSectorPos){
        TreeSet<Sector> sectors = new TreeSet<>();
        ThreadSafeIO threadSafeIO = ThreadSafeIO.getInstance();
        long startingPos = firstSectorPos;
        while(startingPos !=0) {
            Sector sector = null;
            try {
                sector = threadSafeIO.readSector(startingPos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sectors.add(sector);
            startingPos = sector.getNextSector();
        }
        return sectors;
    }

    public static TreeSet<Sector> readSectorsFromPositionSet(TreeSet<Long> sectorsToRead){
        TreeSet<Sector> sectors = new TreeSet<>();
        ThreadSafeIO threadSafeIO = ThreadSafeIO.getInstance();
        for (Long sectorPos:sectorsToRead){
            try {
                sectors.add(threadSafeIO.readSector(sectorPos));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sectors;
    }

    public static TreeSet<Sector> partialReadSectors(int numberOfSectorsToRead, long startingPos){
        TreeSet<Sector> sectors = new TreeSet<>();
        ThreadSafeIO threadSafeIO = ThreadSafeIO.getInstance();
        for (int i=0;i<numberOfSectorsToRead;i++){
            try {
                sectors.add(threadSafeIO.readSector(startingPos));
                startingPos = threadSafeIO.readSectorAlias(startingPos);
                if (startingPos ==0) {return sectors;}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sectors;
    }

    public static int getSectorCount(long startingPos){
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        return (int)readSectorPosesSet(startingPos).stream().count();
    }

    public static Sector readSector(long startingPos){
        Sector sector = null;
        ThreadSafeIO threadSafeIO= ThreadSafeIO.getInstance();
        try {
            sector = threadSafeIO.readSector(startingPos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sector;
    }
    public static TreeSet<Long> readSectorPosesSet(long startingPos){
        TreeSet<Long> fileSectors = new TreeSet<>();
        ThreadSafeIO threadSafeIO = ThreadSafeIO.getInstance();
        try {
            do {
                fileSectors.add(startingPos);
                startingPos = threadSafeIO.readSectorAlias(startingPos);
                //Using negative numbers to exit loop
                startingPos = startingPos>0? startingPos : -1000;
            }while (startingPos>0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSectors;
    }
}
