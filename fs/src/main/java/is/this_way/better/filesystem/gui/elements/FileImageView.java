package is.this_way.better.filesystem.gui.elements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileImageView extends ImageView {
    private Image fileIcon;

    public FileImageView(FileTreeItem theItem) {
        switch (theItem.getCorrespondingFile().getFileType()) {
            case TEXT:
                fileIcon = new Image(getClass().getResourceAsStream("/Icons/TXT128x128.png"));
                break;
            case AUDIO:
                fileIcon = new Image(getClass().getResourceAsStream("/Icons/Music.png"));
                break;
            case VIDEO:
                fileIcon = new Image(getClass().getResourceAsStream("/Icons/Video.png"));
                break;
            case PICTURE:
                fileIcon = new Image(getClass().getResourceAsStream("/Icons/Photo.png"));
                break;
            case UNKNOWN:
                fileIcon = new Image(getClass().getResourceAsStream("/Icons/Unknown.png"));
                break;
        }
        this.setImage(fileIcon);
        this.setPreserveRatio(true);
        this.setFitHeight(32);
    }
}
