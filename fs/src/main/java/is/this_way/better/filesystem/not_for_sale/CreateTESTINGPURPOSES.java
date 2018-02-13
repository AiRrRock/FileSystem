package is.this_way.better.filesystem.not_for_sale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;
import is.this_way.better.filesystem.io.readers_writers.FileSystemFormatter;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.io.readers_writers.SystemInfoWriter;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.model.high_level_items.IndexTable;

import java.util.TreeSet;


public class CreateTESTINGPURPOSES {
    public static void main(String[] args) throws JsonProcessingException {
        IndexTable ind = new IndexTable();
        int sectorSize = 1024*2;
        int sectors = 200;
        long fileSystemStructPos=40;
        int offset=40;
        ThreadSafeIO.init("C:\\Users\\aborichev\\Desktop\\try31.txt",sectorSize);
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        SystemInfo fs= new SystemInfo.SystemInfoBuilder(sectors,sectorSize)
                       .setFileStructurePos(fileSystemStructPos).setOffset(offset)
                .build();
        new SystemInfoWriter().write(fs);
        FileSystemFormatter.FormatFS(sectors,sectorSize, offset );
        RootFolder rootFolder = new RootFolder();
        Folder fold = new Folder("aaa");
        rootFolder.add(fold);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(rootFolder);

        TreeSet<Long> sectorsTree = new TreeSet<>();
        sectorsTree.add((long)40);
       // IndexTable inder = IndexTableDeserializer.deserialize(fs);
        FileSystem fileSystem = new FileSystem
                .FileSystemBuilder(true)
               // .setIndexTable(inder)
                .setSystemInfo(fs)
                .setRootFolder(rootFolder)
                .build();
        FileWriter fr = new FileWriter();
        fr.writeFile(DataConverter.stringToSectorsSet(json,sectorSize, sectorsTree));
        /*FileSystemFormatter.appendSectors(14);
*/


    }
}
