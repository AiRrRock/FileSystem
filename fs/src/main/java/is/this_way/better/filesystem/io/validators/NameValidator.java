package is.this_way.better.filesystem.io.validators;

import is.this_way.better.filesystem.model.low_level_items.folders.AbstractFolder;
import com.sun.istack.internal.NotNull;

public interface NameValidator {
   boolean validateName(@NotNull String name, AbstractFolder parentFolder);
}
