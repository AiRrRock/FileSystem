package is.this_way.better.filesystem.io.lowlevel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class SystemInfoIO {
    public static ArrayList<String> read(RandomAccessFile fileSystem) throws IOException {
        fileSystem.seek(0);
        ArrayList<String> result = new ArrayList<>();
        result.add(fileSystem.readLine());
        result.add(fileSystem.readLine());
        result.add(fileSystem.readLine());
        result.add(fileSystem.readLine());
        return result;
    }
}
