package is.this_way.better.filesystem.io.readers_writers;
import is.this_way.better.filesystem.io.lowlevel.SystemInfoIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class SystemInfoReader {
    public static  ArrayList<String> read(String pathToFile) {
        RandomAccessFile fileSystem = null;
        ArrayList<String> systemInfo = null;
        try {
            fileSystem = new RandomAccessFile(pathToFile,"r");
            systemInfo = SystemInfoIO.read(fileSystem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return systemInfo;
    }

}
