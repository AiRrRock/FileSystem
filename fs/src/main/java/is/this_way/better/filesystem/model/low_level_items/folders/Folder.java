package is.this_way.better.filesystem.model.low_level_items.folders;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import com.fasterxml.jackson.annotation.*;
import is.this_way.better.filesystem.model.low_level_items.AddableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.CopyableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.NamedStructuralItem;

public class Folder extends AbstractFolder implements Serializable,CopyableStructuralItem, AddableStructuralItem, NamedStructuralItem ,Observer{
    @JsonBackReference
    private AbstractFolder parent;
    private String name;
    @JsonManagedReference
    private HashSet<Folder> subFolders;
    @JsonManagedReference
    private HashSet<File> innerFiles;


    public Folder(){
        subFolders=new HashSet<>();
        innerFiles=new HashSet<>();
    }
    public Folder(String name){
        this.name=name;
        subFolders=new HashSet<>();
        innerFiles=new HashSet<>();
    }
    public Folder (String name, Folder parent){
        this.name=name;
        this.parent=parent;
        subFolders=new HashSet<>();
        innerFiles=new HashSet<>();
    }
    //Basic copy constructor
    public Folder(Folder folderToCopy){
        this.name       = folderToCopy.getName();
        this.subFolders = new HashSet<>();
        this.innerFiles = new HashSet<>();
    }

    public AbstractFolder getParent() {
        return parent;
    }
    public String getName() {
        return name;
    }
    public HashSet<Folder> getSubFolders() {
        return subFolders;
    }
    public HashSet<File> getInnerFiles() {
        return innerFiles;
    }
    @JsonIgnore
    public String getPath(){
        return parent.getPath()+name;
    }

    public void setParent(AbstractFolder parent) {
        this.parent=parent;
        this.addObserver(parent);
    }
    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }
    public void setSubFolders(HashSet<Folder> subFolders) {
        this.subFolders = subFolders;
    }
    public void setInnerFiles(HashSet<File> innerFiles) {
        this.innerFiles = innerFiles;
    }

    public void add(AddableStructuralItem itemToAdd){
        if (itemToAdd instanceof File) {
            innerFiles.add((File)itemToAdd);
        }else {
            subFolders.add((Folder)itemToAdd);
            ((Folder) itemToAdd).addObserver(this);
        }

        itemToAdd.setParent(this);
        setChanged();
        notifyObservers();
    }

    public void remove(AddableStructuralItem itemToRemove){
        if (itemToRemove instanceof File) {
            innerFiles.remove((File) itemToRemove);
        }else {
            subFolders.remove((Folder) itemToRemove);
        }
        setChanged();
        notifyObservers();
    }
    @Override
    public String toString() {
        String output="";
        output+=name +"\n   Subfolders:";
        for (AbstractFolder f: subFolders){
            output+= "\n   " +f.toString();
        }
        return output;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        byte[] nameArray = name.getBytes();
        for(int i=0;i<nameArray.length;i++){
            hashCode += nameArray[i]*(i+1);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        return ((Folder)o).getName().equals(name) ;
    }


    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers();
    }
}
