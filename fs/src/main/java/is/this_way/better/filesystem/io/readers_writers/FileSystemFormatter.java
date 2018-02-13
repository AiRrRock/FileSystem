package is.this_way.better.filesystem.io.readers_writers;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.io.lowlevel.ConstantsIO;


import java.io.IOException;
import java.util.TreeSet;

public class FileSystemFormatter {
    public static void FormatFS(int sectors, int sectorSize, long sectorStartingPos){
        Sector empty = crateEmptySector(sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE);
        ThreadSafeIO pIO = ThreadSafeIO.getInstance();
        long sectorToWrite = sectorStartingPos;
        for (int i=0; i<sectors;i++){
            empty.setStartingPos(sectorToWrite);
            try {
                pIO.writeSector(empty);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sectorToWrite+=sectorSize;
        }
    }
    public static void appendSectors(int sectors){
        SystemInfo systemInfo = FileSystem.getSelfReference().getSystemInfo();
        int sectorSize = systemInfo.getSectorSize();
        long startingPos = FileSystem.getSelfReference().getIndexTable().getLastSector()+sectorSize;
        Sector empty = crateEmptySector(sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE);
        for(int i=0; i<sectors; i++){
            empty.setStartingPos(startingPos+i*sectorSize);
            appendSector(empty);
        }
    }

    private static void appendSector(Sector sector){
        ThreadSafeIO pIO = ThreadSafeIO.getInstance();
        try {
            pIO.writeSector(sector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void freeSector(long sectorPos) {
        int sectorSize = FileSystem.getSelfReference().getSystemInfo().getSectorSize();
        Sector empty = crateEmptySector(sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE);
        empty.setStartingPos(sectorPos);
        ThreadSafeIO pIO = ThreadSafeIO.getInstance();
        try {
            pIO.writeSector(empty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void freeSectors(TreeSet<Sector> sectorToFree){
        for (Sector sector:sectorToFree){
            freeSector(sector.getStartingPos());
        }
    }

    private static Sector crateEmptySector(int sectorSize){
        byte[] data = new byte[sectorSize];
        data[0]= ConstantsIO.EndOfText;
        for (int i=1;i<data.length-1;i++){
            data[i]= ConstantsIO.EMPTY;
        }
        data[data.length-1]= ConstantsIO.EOL;
        long nextSector=0;
        return new Sector.SectorBuilder(0,data).setNextSector(0).setLast(true).build();
    }


}
