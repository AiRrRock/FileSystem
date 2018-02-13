package is.this_way.better.filesystem.gui.tasks;


import is.this_way.better.filesystem.gui.elements.FileTreeItem;
import is.this_way.better.filesystem.gui.elements.FolderTreeItem;
import is.this_way.better.filesystem.io.resolvers.ExtensionResolver;
import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.lowlevel.FileLoader;
import is.this_way.better.filesystem.io.readers_writers.FileSystemFormatter;
import is.this_way.better.filesystem.io.readers_writers.FileWriter;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.files.FileTypes;
import javafx.concurrent.Task;
import java.util.TreeSet;

public class FileAdditionTask extends Task  {
    private java.io.File fileToAdd;
    private FolderTreeItem folder;

    public FileAdditionTask(java.io.File fileToAdd, FolderTreeItem folder) {
        this.fileToAdd = fileToAdd;
        this.folder = folder;
    }

    @Override
    protected FileTreeItem call() throws Exception {
        FileSystem fileSystem = FileSystem.getSelfReference();
        String futureFileName =fileToAdd.getName();
        String extension = fileToAdd.getName().contains(".")
                ? (fileToAdd.getName().lastIndexOf(".") < (fileToAdd.getName().length() - 1)
                    ? fileToAdd.getName().substring(fileToAdd.getName().lastIndexOf(".") + 1)
                    : "")
                : "";
        FileTypes fileType = ExtensionResolver.resolve(extension);
        // Calculate needed sectors for the file
        byte[] dataInArray = FileLoader.loadByteArray(fileToAdd.getAbsolutePath());
        int neededSectors = DataConverter.calculateSectors(dataInArray, fileSystem.getSystemInfo().getSectorSize());
        // Check how much sectors we have left
        int emptySectors = fileSystem.getIndexTable().getNumberOfEmptySectors();
        // Add some sectors if we need them
        if (neededSectors > emptySectors) {
            int sectorsToAppend = neededSectors - emptySectors;
            FileSystemFormatter.appendSectors(sectorsToAppend);
            fileSystem.addSectors(sectorsToAppend);
        }
        emptySectors = fileSystem.getIndexTable().getNumberOfEmptySectors();
        TreeSet<Long> futureSectors= new TreeSet<>(fileSystem.getIndexTable().getEmptySectors(neededSectors));

        long startingPos = futureSectors.first();
        FileWriter fileWriter = new FileWriter();
        fileWriter.writeFile(DataConverter.byteArrayToSectorSet(dataInArray,fileSystem.getSystemInfo().getSectorSize(),futureSectors));
        futureSectors=null;
        File file = new File(futureFileName, fileType);
        file.setStartingPos(startingPos);
        file.setSizeInSectors(neededSectors);
        file.setParent(folder.getCorrespondingFolder());
        folder.getCorrespondingFolder().add(file);
        return new FileTreeItem(file,folder);
    }

}
