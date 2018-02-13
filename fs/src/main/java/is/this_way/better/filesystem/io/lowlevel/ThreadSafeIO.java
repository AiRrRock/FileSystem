package is.this_way.better.filesystem.io.lowlevel;

import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ThreadSafeIO {
    private static ThreadSafeIO instance;
    private RandomAccessFile fileSystem;
    private int sectorSize;

    private ThreadSafeIO(RandomAccessFile fileSystem, int sectorSize){
        this.sectorSize= sectorSize;
        this.fileSystem= fileSystem;
    }
    public static ThreadSafeIO getInstance(){
        return instance;
    }

    public static boolean init(String fileSystemFilePath, int sectorSize){
        RandomAccessFile fileSystem;
        try{
            fileSystem = new RandomAccessFile(fileSystemFilePath,"rw");
        }
        catch (FileNotFoundException e){
                e.printStackTrace();
                return false;
            }
        synchronized (ThreadSafeIO.class) {
            if (instance == null){
                synchronized (ThreadSafeIO.class){
                    if (instance == null){
                        instance = new ThreadSafeIO(fileSystem,sectorSize);}
                    }
            }
        }
        return true;
    }
    public static synchronized void release() throws IOException {
        if (instance != null) {
            if(instance.fileSystem!=null){
                instance.fileSystem.close();
            }
            instance = null;
        }
    }

    public synchronized Sector readSector(long sectorPos) throws IOException {
        byte[] data = readSectorData(sectorPos);
        long nextSector = readSectorAlias(sectorPos );
        return new Sector.
                    SectorBuilder(sectorPos, data)
                    .setNextSector(nextSector)
                    .setLast(nextSector == 0 ? true : false)
                    .build();
    }

    private byte[] readSectorData(long sectorPos) throws IOException {
        byte [] data =new byte[sectorSize- ConstantsIO.SECTOR_DATA_ALIAS_WITH_EOL];
        fileSystem.seek(sectorPos);
        fileSystem.read(data);
        return data;
    }
    private long readNextSector(long startingPos) throws IOException {
        fileSystem.seek(startingPos);
        long nextSector = Long.parseLong(fileSystem.readLine());
        return nextSector;
    }
    public synchronized long readSectorAlias(long sectorPos) throws IOException {
        long aliasPosition = sectorPos+sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE;
        long nextSector = readNextSector(aliasPosition);
        return nextSector;
    }
    public synchronized boolean hasNext(long sectorPos) throws IOException {
        boolean test=readSectorAlias(sectorPos)==0 ? false : true;
        return readSectorAlias(sectorPos)==0 ? false : true;
    }
    public synchronized void markFull(long startingPos) throws IOException {
        fileSystem.seek(startingPos);
        fileSystem.write((ConstantsIO.EMPTY));
        fileSystem.write(ConstantsIO.EndOfText);
    }
    public synchronized void writeSector(Sector sector) throws IOException{
       writeSectorData(sector.getStartingPos(),sector.getData());
       writeNextSector(sector.getStartingPos()+sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE,prepareNextSector(sector.getNextSector()));
    }
    private void writeSectorData(long sectorPos, byte[] sectorData)throws IOException{
        fileSystem.seek(sectorPos);
        fileSystem.write(sectorData);
        fileSystem.write(ConstantsIO.EndOfText);
    }
    private void writeNextSector (long sectorPos, byte[] nextSector)throws IOException{
        fileSystem.seek(sectorPos);
        fileSystem.write(nextSector);
    }
    public synchronized void deleteSector(long sectorPos) throws IOException{
        fileSystem.seek(sectorPos);
        fileSystem.write(ConstantsIO.EndOfText);
        byte[] nullifiedNextSector = prepareNextSector(0);
        writeNextSector(sectorPos+sectorSize- ConstantsIO.SECTOR_ALIAS_SIZE,nullifiedNextSector);
    }
    public synchronized void writeSystemInfo(byte[] systemInfo) throws IOException {
        fileSystem.seek(0);
        fileSystem.write(systemInfo);
    }
    public synchronized boolean isEmpty(long startingPos) throws IOException {
        fileSystem.seek(startingPos);
        byte firstByte = fileSystem.readByte();
        return firstByte== ConstantsIO.EndOfText ;
    }
    private byte[] prepareNextSector(long nextSector){
        String representation = Long.toString(nextSector);
        StringBuilder sb = new StringBuilder();
        for (int i = representation.length(); i< ConstantsIO.SECTOR_ALIAS_DATA; i++){
            sb.append((char) ConstantsIO.ZERO);
        }
        sb.append(representation).append((char) ConstantsIO.EOL);
        return sb.toString().getBytes();
    }


}
