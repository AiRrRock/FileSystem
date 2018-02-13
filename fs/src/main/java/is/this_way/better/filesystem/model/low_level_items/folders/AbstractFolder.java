package is.this_way.better.filesystem.model.low_level_items.folders;
import is.this_way.better.filesystem.model.low_level_items.AddableStructuralItem;
import is.this_way.better.filesystem.model.low_level_items.files.File;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public abstract class AbstractFolder extends Observable implements Observer,AddableStructuralItem {
    public abstract Set<File> getInnerFiles();
    public abstract HashSet<Folder> getSubFolders();
    public abstract String getName();
    public abstract void setInnerFiles(HashSet<File> innerFiles);
    public abstract void setSubFolders(HashSet<Folder> subFolders);
    public abstract void add(AddableStructuralItem itemToAdd);
    public abstract String getPath();
    public abstract void remove(AddableStructuralItem itemToRemove);
    public abstract AbstractFolder getParent();
}
