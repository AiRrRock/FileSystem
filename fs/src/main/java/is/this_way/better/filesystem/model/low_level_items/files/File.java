package is.this_way.better.filesystem.model.low_level_items.files;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import is.this_way.better.filesystem.model.low_level_items.AddableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.CopyableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import is.this_way.better.filesystem.model.low_level_items.lowlevel.Sector;
import is.this_way.better.filesystem.model.low_level_items.NamedStructuralItem;

import java.io.Serializable;
import java.util.Observable;
import java.util.TreeSet;

public class File extends Observable implements Serializable,CopyableStructuralItem, AddableStructuralItem, NamedStructuralItem {

    @JsonBackReference
    private AbstractFolder parent;
    private String name;
    private FileTypes fileType;

    @JsonIgnore
    private FileData data;
    private long startingPos;
    private long sizeInSectors;

    public File(){}

    public File(File oldFile,long newStartingPos){
        this.parent        = oldFile.getParent();
        this.name          = oldFile.getName();
        this.fileType      = oldFile.getFileType();
        this.sizeInSectors = oldFile.getSizeInSectors();
        this.startingPos   = newStartingPos;
    }
    public File(String name,FileTypes fileType){
        this.fileType = fileType;
        this.name=name;
    }

    public AbstractFolder getParent() {
        return parent;
    }
    public String getName() {
        return name;
    }
    public FileTypes getFileType() {
        return fileType;
    }

    public TreeSet<Sector> getData() {
        if (data==null){
        data = new FileData(startingPos);}
        return data.getData();
    }
    public long getStartingPos() {
        return startingPos;
    }
    public long getSizeInSectors() {
        return sizeInSectors;
    }
    @JsonIgnore
    public String getPath(){
        return parent.getPath()+name;
    }
    @JsonIgnore
    public TreeSet<Long> getSectorPoses(){
        return data.getSectorStartingPoses();
    }

    public void setParent(AbstractFolder parent) {
        this.parent = parent;
        this.addObserver(parent);
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFileType(FileTypes fileType) {
        this.fileType = fileType;
    }
    public void setData(FileData data) {
       this.data = data;
    }
    public void setStartingPos(long startingPos) {
        this.startingPos = startingPos;
    }
    public void setSizeInSectors(long sizeInSectors) {
        this.sizeInSectors = sizeInSectors;
        setChanged();
        notifyObservers();
    }

    public void freeFileData(){
        data = null;
        // I know that it is tedious but I still had to try to force it to clean
        System.gc();
    }


    @Override
    public int hashCode() {
        int hashCode=0;
        byte[] nameArray = name.getBytes();
        for(int i=0;i<nameArray.length;i++){
            hashCode+= nameArray[i]*(i+1);
        }
        return hashCode;
    }
    @Override
    public boolean equals(Object o) {
        return ((File)o).getName().equals(name) ;
    }
}
