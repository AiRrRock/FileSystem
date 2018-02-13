package is.this_way.better.filesystem.io.serialization;

import is.this_way.better.filesystem.model.high_level_items.SystemInfo;
import is.this_way.better.filesystem.io.exceptions.FSSerializationException;
import is.this_way.better.filesystem.io.lowlevel.ConstantsFS;

import static is.this_way.better.filesystem.io.lowlevel.ConstantsIO.*;

public class SystemInfoSerializer {

    public static byte[] getSerialisedSystemInfo(SystemInfo systemInfo) throws FSSerializationException {
        return prepareForSerialization(systemInfo);
    }

    private static byte[] prepareForSerialization(SystemInfo systemInfo) throws FSSerializationException {
        StringBuilder sb = new StringBuilder();
        sb.append(toSpecificString(Integer.toString(systemInfo.getSectors()), ConstantsFS.SECTORS_SIZE))
                .append(toSpecificString(Integer.toString(systemInfo.getSectorSize()/BYTES_IN_KB), ConstantsFS.SECTORS))
                .append(toSpecificString(Long.toString(systemInfo.getFileStructurePos()), SECTOR_ALIAS_DATA))
                .append(toSpecificString(Integer.toString(systemInfo.getOffset()), ConstantsFS.OFFSET_BYTE_SIZE));
        return sb.toString().getBytes();
    }

    private static String toSpecificString(String data, int numberOfBytes) throws FSSerializationException {
        int length = data.length();
        StringBuilder sb = new StringBuilder();
        if (length > numberOfBytes){
            throw new FSSerializationException("Incorrect size of variables");
        }
        if (length < numberOfBytes) {
            for (int i = length; i < numberOfBytes; i++) {
                sb.append(0);
            }
        } else {

        }
        sb.append(data);
        sb.append("\n");
        return sb.toString();
    }
}

