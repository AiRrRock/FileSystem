package is.this_way.better.filesystem.io.resolvers;

import com.google.common.collect.Iterables;
import is.this_way.better.filesystem.io.converters.DataConverter;
import is.this_way.better.filesystem.io.readers_writers.FileReader;
import is.this_way.better.filesystem.io.readers_writers.FileSystemFormatter;
import is.this_way.better.filesystem.model.high_level_items.FileSystem;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.util.TreeSet;

public class FileUpdateResolver {
    public static TreeSet<Sector> resolve(String data, long startingPos){
        TreeSet<Sector> sectorRepresentation = resolve(data,(FileReader.readFileData(startingPos)).getData());
        return sectorRepresentation;
    }
    public static TreeSet<Sector> partialResolve(String data, TreeSet<Sector> usedSectors){
        long lastSectorAlias = usedSectors.last().getNextSector();
        TreeSet<Sector> sectorRepresentation = resolve(data,usedSectors);
        sectorRepresentation.last().setNextSector(lastSectorAlias);
        return sectorRepresentation;
    }

    private static TreeSet<Sector> resolve(String data, TreeSet<Sector> usedSectors){
        FileSystem fileSystem = FileSystem.getSelfReference();
        int numberOfUsedSectors = usedSectors.size();
        int emptySectors = fileSystem.getIndexTable().getNumberOfEmptySectors();
        int neededSectors = DataConverter.calculateSectors(data.getBytes(),fileSystem.getSystemInfo().getSectorSize());
        TreeSet<Sector> sectorRepresentation = new TreeSet<>();
        if (numberOfUsedSectors < neededSectors) {
            int additionalSectors = neededSectors - numberOfUsedSectors;
            if (emptySectors < additionalSectors) {
                int sectorsToAppend = additionalSectors - emptySectors;
                FileSystemFormatter.appendSectors(sectorsToAppend);
                fileSystem.addSectors(sectorsToAppend);
            }
            TreeSet<Long> sectorPositions = DataConverter.getSectorPositions(usedSectors);
            sectorPositions.addAll(fileSystem.getIndexTable().getEmptySectors(additionalSectors));
            sectorRepresentation = DataConverter.stringToSectorsSet(data,fileSystem.getSystemInfo().getSectorSize(),sectorPositions);
        } else if (numberOfUsedSectors > neededSectors) {
            Sector sector = Iterables.get(usedSectors, neededSectors);
            TreeSet<Sector> sectorsToFree = (TreeSet<Sector>) usedSectors.tailSet(sector);
            sectorRepresentation = DataConverter.recreateSectors(data, (TreeSet<Sector>) usedSectors.headSet(sectorsToFree.first()));
            FileSystem.getSelfReference().freeSectors(sectorsToFree);
            FileSystemFormatter.freeSectors(sectorsToFree);
        } else {
            sectorRepresentation = DataConverter.recreateSectors(data,usedSectors);
        }
        return sectorRepresentation;
    }
}
