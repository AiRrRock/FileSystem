package is.this_way.better.filesystem.io.readers_writers;


import is.this_way.better.filesystem.io.exceptions.FSSerializationException;
import is.this_way.better.filesystem.io.serialization.SystemInfoSerializer;
import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.io.lowlevel.ThreadSafeIO;

import java.io.IOException;

public class SystemInfoWriter implements SystemWriter {
    @Override
    public void write(SystemInfo systemInfo) {
        ThreadSafeIO pIO= ThreadSafeIO.getInstance();
        try {
            pIO.writeSystemInfo(
            SystemInfoSerializer.getSerialisedSystemInfo(systemInfo));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FSSerializationException e) {
            e.printStackTrace();
        }
    }
}
