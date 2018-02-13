package is.this_way.better.filesystem.model.low_level_items.folders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import is.this_way.better.filesystem.model.low_level_items.AddableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.CopyableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.files.File;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


public class RootFolder extends AbstractFolder implements CopyableStructuralItem, Serializable, Observer {
    private final String name;
    @JsonManagedReference
    private HashSet<Folder> subFolders;
    @JsonManagedReference
    private HashSet<File> innerFiles;

    public RootFolder(){
        this.name="root";
        this.subFolders=new HashSet<>();
        this.innerFiles=new HashSet<>();
    }
    public String getName() {
        return name;
    }
    public HashSet<Folder> getSubFolders() {
        return subFolders;
    }
    public Set<File> getInnerFiles() {
        return innerFiles;
    }

    public void setInnerFiles(HashSet<File> innerFiles) {
        this.innerFiles=innerFiles;
    }
    public void setSubFolders(HashSet<Folder> subFolders) {
        this.subFolders=subFolders;
    }
    public void add(AddableStructuralItem itemToAdd){
        if (itemToAdd instanceof File) {
            innerFiles.add((File)itemToAdd);
        }else {
            subFolders.add((Folder)itemToAdd);
        }
        itemToAdd.setParent(this);
        setChanged();
        notifyObservers();

    }
    @JsonIgnore
    public String getPath(){
        return name;
    }

    @Override
    public void remove(AddableStructuralItem itemToRemove) {
        if (itemToRemove instanceof File) {
            innerFiles.remove((File)itemToRemove);
        }else {
            subFolders.remove((Folder)itemToRemove);
        }
        setChanged();
        notifyObservers();
    }

    @Override
    @JsonIgnore
    public AbstractFolder getParent() {
        return null;
    }

    @Override
    public void setParent(AbstractFolder parent) {
    }

    @Override
    public String toString() {
        String output="";
        output += name + "\n Subfolders:";
        for (AbstractFolder f : subFolders){
            output += "\n  " +f.toString();
        }
        return output;
    }

    @Override
    public void update(Observable observable, Object o) {

        setChanged();
        notifyObservers();
    }
}
