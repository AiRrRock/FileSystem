package is.this_way.better.filesystem.io.readers_writers;

import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.util.ArrayList;

public interface SectorWriter {
    public void write(ArrayList<Sector> sectors);
}
