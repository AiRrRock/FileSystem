package is.this_way.better.filesystem.io.validators;

import is.this_way.better.filesystem.model.low_level_items.folders.Folder;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import com.sun.istack.internal.NotNull;

public class FolderNameValidator implements NameValidator {
    @Override
    public boolean validateName(@NotNull String name, AbstractFolder parentFolder) {
        return  parentFolder.getSubFolders().contains(new Folder(name)) ? false : true;

    }
}
