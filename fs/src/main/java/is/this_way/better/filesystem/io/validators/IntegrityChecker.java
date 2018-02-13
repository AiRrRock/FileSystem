package is.this_way.better.filesystem.io.validators;

import is.this_way.better.filesystem.io.readers_writers.SystemInfoReader;
import is.this_way.better.filesystem.io.lowlevel.ConstantsIO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class IntegrityChecker {
    public static boolean checkIntegrity(String fileSystem){
        try (RandomAccessFile theFile = new RandomAccessFile(fileSystem, "r")){
            ArrayList<String> systemInfo = SystemInfoReader.read(fileSystem.toString());
            long realFileLength = theFile.length();
            long calculatedFileLength = ConstantsIO.BYTES_IN_KB*Long.parseLong(systemInfo.get(0))*Long.parseLong(systemInfo.get(1))+Long.parseLong(systemInfo.get(3));
            System.out.println(realFileLength +"  "+ calculatedFileLength );
            System.out.println(realFileLength-calculatedFileLength);
            if (Long.compare(realFileLength,calculatedFileLength)==0){return true;}
        } catch (IOException e) {
            return false;
        } catch (Exception ex){
            return false;
        }
        //TODO fix this nonsense
        return true;
    }
}
