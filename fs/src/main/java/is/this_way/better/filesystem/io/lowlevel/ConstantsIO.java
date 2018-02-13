package is.this_way.better.filesystem.io.lowlevel;

public class ConstantsIO {
    //Sector size in bytes
    public static final int SECTOR_ALIAS_SIZE=20;
    public static final int SECTOR_ALIAS_DATA=19;
    //S
    public static final int SECTOR_DATA_SIZE_SUBTRAHEND=SECTOR_ALIAS_SIZE+2;
    public static final int SECTOR_DATA_ALIAS_WITH_EOL =SECTOR_ALIAS_SIZE+1;
    /** Byte properties */
    public static final int BYTES_IN_KB= 1024;

    public static final byte EMPTY = (byte)0;
    public static final byte EOL = (byte) 13;
    public static final byte EndOfText = (byte) 8;
    public static final byte StartOfText = (byte) 2;
    public static final byte ZERO = (byte) 48;
}
