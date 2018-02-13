package is.this_way.better.filesystem.io.readers_writers;

import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;

import java.io.IOException;
import java.util.ArrayList;

public class FileSystemWriter implements SectorWriter {
    @Override
    public void write(ArrayList<Sector> sectors) {
        ThreadSafeIO pio = ThreadSafeIO.getInstance();
        for (Sector sector: sectors){
            try {
                pio.writeSector(sector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
