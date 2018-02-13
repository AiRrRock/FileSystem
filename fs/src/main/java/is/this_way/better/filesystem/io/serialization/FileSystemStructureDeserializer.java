package is.this_way.better.filesystem.io.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.model.low_level_items.files.FileData;
import is.this_way.better.filesystem.model.low_level_items.folders.RootFolder;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class FileSystemStructureDeserializer extends Observable {
    private boolean finished;
    private long startingSector;

        public FileSystemStructureDeserializer(long startingSector, Observer observer){
            finished=false;
            this.startingSector=startingSector;
            this.addObserver(observer);
        }
    public  RootFolder deserialize() {
        setChanged();
        notifyObservers();
        ObjectMapper mapper = new ObjectMapper();
        FileData structuralData = FileReader.readFileData(startingSector);
        String jsonString = DataConverter.sectorsToString(structuralData.getData());
        RootFolder rootFolder = null;
        try {
            rootFolder = mapper.readValue(jsonString,RootFolder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootFolder;
    }


}
