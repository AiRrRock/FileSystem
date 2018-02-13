package is.this_way.better.filesystem.io.resolvers;

import is.this_way.better.filesystem.model.low_level_items.files.FileTypes;

public class ExtensionResolver {
    public static FileTypes resolve(String extension) {
        extension = extension.toUpperCase();
        FileTypes result;
        switch (extension) {
            case "JPEG" :
            case "JPG"  :
            case "PNG"  :
            case "GIF"  :
            case "MPO"  :
            case "TIFF" :
            case "PICT" :
                result = FileTypes.PICTURE;
                break;
            case "TXT" :
            case "BAT" :
            case "SH"  :
            case "INI" :
                result = FileTypes.TEXT;
                break;
            case "AVI" :
                result = FileTypes.VIDEO;
                break;
            case "MP3" :
                result = FileTypes.AUDIO;
                break;
            default:
                result= FileTypes.UNKNOWN;
                break;
        }
        return result;
    }
}
