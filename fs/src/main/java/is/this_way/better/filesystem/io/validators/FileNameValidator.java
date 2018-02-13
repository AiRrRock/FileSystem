package is.this_way.better.filesystem.io.validators;

import is.this_way.better.filesystem.model.low_level_items.files.File;
import is.this_way.better.filesystem.model.low_level_items.files.FileTypes;
import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import com.sun.istack.internal.NotNull;

public class FileNameValidator implements NameValidator{
    @Override
    public boolean validateName(@NotNull String name, AbstractFolder parentFolder) {
        return parentFolder.getInnerFiles().contains(new File(name, FileTypes.TEXT)) ? false : true;
    }
}
