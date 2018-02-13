package is.this_way.better.filesystem.gui.elements;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.binding.Bindings;


public  class FolderImageView extends ImageView {
    private Image closedFolder;
    private Image openFolder;

   public FolderImageView(FolderTreeItem theItem){
       closedFolder = new Image(getClass().getResourceAsStream("/Icons/folderIconClosed.png"));
       openFolder = new Image(getClass().getResourceAsStream("/Icons/folderIconOpened.png"));
       this.imageProperty().bind(Bindings.when(theItem.expandedProperty()).then(openFolder).otherwise(closedFolder));
       this.setPreserveRatio(true);
       this.setFitHeight(32);
   }
}
