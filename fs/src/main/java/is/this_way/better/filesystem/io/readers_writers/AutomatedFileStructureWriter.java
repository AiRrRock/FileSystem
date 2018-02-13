package is.this_way.better.filesystem.io.readers_writers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.this_way.better.filesystem.io.resolvers.FileUpdateResolver;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;



public class AutomatedFileStructureWriter implements Observer {
    private FileWriter fileWriter;
    public AutomatedFileStructureWriter() {
        fileWriter = new FileWriter();
    }

    @Override
    public void update(Observable observable, Object o) {
        RootFolder root = (RootFolder) observable;
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        long startingSector = FileSystem
                .getSelfReference()
                .getSystemInfo()
                .getFileStructurePos();
        TreeSet<Sector> sectorsToWrite = FileUpdateResolver.resolve(json,startingSector);
        fileWriter.writeFile(sectorsToWrite);
    }
}
