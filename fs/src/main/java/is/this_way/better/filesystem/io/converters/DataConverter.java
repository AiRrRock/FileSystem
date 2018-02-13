package is.this_way.better.filesystem.io.converters;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static is.this_way.better.filesystem.io.lowlevel.ConstantsIO.*;

public class DataConverter implements Converter {

    private static String convert(byte[] sectorData) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<sectorData.length;i++){
            if (sectorData[i]==EndOfText){
                return sb.toString();}
            sb.append((char)sectorData[i]);
        }
        return sb.toString();
    }

    public static String convertSector(byte[] sectorData){
        return convert(sectorData);
    }

    public static String sectorsToString(TreeSet<Sector> sectors){
        byte[] sectorData = sectorsToByteArray(sectors);
        return convert(sectorData);
    }
    public static byte[] sectorsToByteArray(TreeSet<Sector> sectors){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (Sector sector:sectors){
            try {
                byte[] subarray = Arrays.copyOfRange(sector.getData(),0,sector.getData().length-1);
                outputStream.write(subarray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outputStream.toByteArray();
    }
    public static byte[] sectorToByteArray(Sector sector){
        return convert(sector.getData()).getBytes();
    }


    public static TreeSet<Sector> stringToSectorsSet(String data, int sectorSize, TreeSet<Long> sectors){
        return byteArrayToSectorSet(data.getBytes(),sectorSize,sectors);
    }

    public static TreeSet<Sector> byteArrayToSectorSet(byte[] data, int sectorSize, TreeSet<Long> sectors){
        TreeSet<Sector> result = new TreeSet<>();
        long firstPos;
        long sectorAlias;
        int step = sectorSize-SECTOR_DATA_SIZE_SUBTRAHEND;
        byte[] slice;
        for (int i = 0; i<data.length;i += step){
            firstPos = sectors.first();
            sectors.remove(firstPos);
            sectorAlias = sectors.isEmpty()? 0 : sectors.first();
            slice = Arrays.copyOfRange(data,i,(i+step)< data.length ? i+step:data.length);
            result.add(byteArrayToSector(slice,firstPos,sectorAlias));
        }
        result.last().setNextSector(0);
        return result;
    }


    public static TreeSet<Sector> recreateSectors(String data, TreeSet<Sector> sectors){
        TreeSet<Long> sectorPoses = getSectorPositions(sectors);
        int sectorSize = FileSystem.getSelfReference().getSystemInfo().getSectorSize();
        return stringToSectorsSet(data,sectorSize,sectorPoses);
    }

    public static TreeSet<Long> getSectorPositions(TreeSet<Sector> sectors){
        TreeSet<Long> sectorPoses = sectors
                .stream()
                .parallel()
                .map(sector -> new Long(sector.getStartingPos())).collect(Collectors.toCollection(TreeSet::new));
        return sectorPoses;
    }
    private static Sector byteArrayToSector(byte[] data, long startingPos, long sectorAlias){
        byte[] sectorData = Arrays.copyOf(data,data.length+1);
        sectorData[sectorData.length-1]=EndOfText;
        return new Sector.SectorBuilder(startingPos,data).setNextSector(sectorAlias).build();
    }
    public static int calculateSectors(byte[] data, int sectorSize){
        double dataLength = data.length;
        int neededSectors = (int) Math.ceil(dataLength/(sectorSize-SECTOR_DATA_SIZE_SUBTRAHEND));
        return neededSectors;
    }

}
