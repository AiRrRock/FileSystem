package is.this_way.better.filesystem.model.low_level_items.files;

import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;

import java.util.TreeSet;

public class FileData {
    private TreeSet<Sector> data;
    private long startingPos;
    public FileData(long startingPos){
        this.startingPos=startingPos;
    }
    public TreeSet<Sector> getData() {
        return data;
    }
    public TreeSet<Long> getSectorStartingPoses() {
        TreeSet<Long> startingPoses = new TreeSet<>();
        for (Sector sector: data){
            startingPoses.add(sector.getStartingPos());
        }
        return startingPoses;
    }
    public void setData(TreeSet<Sector> data) {
        this.data = data;
    }

}
