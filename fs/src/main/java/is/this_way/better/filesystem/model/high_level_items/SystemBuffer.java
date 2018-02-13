package is.this_way.better.filesystem.model.high_level_items;

import is.this_way.better.filesystem.model.low_level_items.CopyableStructuralItem;

public class SystemBuffer {
    private CopyableStructuralItem itemToCopy;

    public SystemBuffer(){
        itemToCopy=null;
    }

    public boolean isEmpty(){
        return itemToCopy==null ? true : false;
    }
    public void setItemToCopy(CopyableStructuralItem itemToCopy) {
        this.itemToCopy = itemToCopy;
    }
    public CopyableStructuralItem getItemToCopy() {
        return itemToCopy;
    }
    public void cleanBuffer(){
        itemToCopy=null;
    }

}

