package is.this_way.better.filesystem.model.low_level_items;

import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;

public interface AddableStructuralItem {
    public AbstractFolder getParent();
    public void setParent(AbstractFolder parent);
}
