package is.this_way.better.filesystem.io.lowlevel;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileLoader {
    public static String load(String filePath){
        StringBuilder result = new StringBuilder();
        try (RandomAccessFile fileToRead = new RandomAccessFile(filePath, "r")){
            String line;
            while ((line = fileToRead.readLine())!=null){
                result.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
    public static byte[] loadByteArray(String filePath){
        byte[] result =new byte[0];
        try (RandomAccessFile fileToRead = new RandomAccessFile(filePath, "r")){
            result = new byte[(int) fileToRead.length()];
            fileToRead.read(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
